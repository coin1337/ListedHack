package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.base.Joiner;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class RedirectInjector extends InvokeInjector {
   private static final String KEY_NOMINATORS = "nominators";
   private static final String KEY_WILD = "wildcard";
   private static final String KEY_FUZZ = "fuzz";
   private static final String KEY_OPCODE = "opcode";
   protected RedirectInjector.Meta meta;
   private Map<BeforeNew, RedirectInjector.ConstructorRedirectData> ctorRedirectors;

   public RedirectInjector(InjectionInfo info) {
      this(info, "@Redirect");
   }

   protected RedirectInjector(InjectionInfo info, String annotationType) {
      super(info, annotationType);
      this.ctorRedirectors = new HashMap();
      int priority = info.getContext().getPriority();
      boolean isFinal = Annotations.getVisible(this.methodNode, Final.class) != null;
      this.meta = new RedirectInjector.Meta(priority, isFinal, this.info.toString(), this.methodNode.desc);
   }

   protected void checkTarget(Target target) {
   }

   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode insn, Set<InjectionPoint> nominators) {
      InjectionNodes.InjectionNode node = target.getInjectionNode(insn);
      RedirectInjector.ConstructorRedirectData ctorData = null;
      int fuzz = 8;
      int opcode = 0;
      if (node != null) {
         RedirectInjector.Meta other = (RedirectInjector.Meta)node.getDecoration("redirector");
         if (other != null && other.getOwner() != this) {
            if (other.priority >= this.meta.priority) {
               Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{this.annotationType, this.info, this.meta.priority, other.name, other.priority});
               return;
            }

            if (other.isFinal) {
               throw new InvalidInjectionException(this.info, this.annotationType + " conflict: " + this + " failed because target was already remapped by " + other.name);
            }
         }
      }

      Iterator var12 = nominators.iterator();

      while(true) {
         while(var12.hasNext()) {
            InjectionPoint ip = (InjectionPoint)var12.next();
            if (ip instanceof BeforeNew && !((BeforeNew)ip).hasDescriptor()) {
               ctorData = this.getCtorRedirect((BeforeNew)ip);
            } else if (ip instanceof BeforeFieldAccess) {
               BeforeFieldAccess bfa = (BeforeFieldAccess)ip;
               fuzz = bfa.getFuzzFactor();
               opcode = bfa.getArrayOpcode();
            }
         }

         InjectionNodes.InjectionNode targetNode = target.addInjectionNode(insn);
         targetNode.decorate("redirector", this.meta);
         targetNode.decorate("nominators", nominators);
         if (insn instanceof TypeInsnNode && insn.getOpcode() == 187) {
            targetNode.decorate("wildcard", ctorData != null);
            targetNode.decorate("ctor", ctorData);
         } else {
            targetNode.decorate("fuzz", fuzz);
            targetNode.decorate("opcode", opcode);
         }

         myNodes.add(targetNode);
         return;
      }
   }

   private RedirectInjector.ConstructorRedirectData getCtorRedirect(BeforeNew ip) {
      RedirectInjector.ConstructorRedirectData ctorRedirect = (RedirectInjector.ConstructorRedirectData)this.ctorRedirectors.get(ip);
      if (ctorRedirect == null) {
         ctorRedirect = new RedirectInjector.ConstructorRedirectData();
         this.ctorRedirectors.put(ip, ctorRedirect);
      }

      return ctorRedirect;
   }

   protected void inject(Target target, InjectionNodes.InjectionNode node) {
      if (this.preInject(node)) {
         if (node.isReplaced()) {
            throw new UnsupportedOperationException("Redirector target failure for " + this.info);
         } else if (node.getCurrentTarget() instanceof MethodInsnNode) {
            this.checkTargetForNode(target, node);
            this.injectAtInvoke(target, node);
         } else if (node.getCurrentTarget() instanceof FieldInsnNode) {
            this.checkTargetForNode(target, node);
            this.injectAtFieldAccess(target, node);
         } else if (node.getCurrentTarget() instanceof TypeInsnNode && node.getCurrentTarget().getOpcode() == 187) {
            if (!this.isStatic && target.isStatic) {
               throw new InvalidInjectionException(this.info, "non-static callback method " + this + " has a static target which is not supported");
            } else {
               this.injectAtConstructor(target, node);
            }
         } else {
            throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting an invalid insn in " + target + " in " + this);
         }
      }
   }

   protected boolean preInject(InjectionNodes.InjectionNode node) {
      RedirectInjector.Meta other = (RedirectInjector.Meta)node.getDecoration("redirector");
      if (other.getOwner() != this) {
         Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{this.annotationType, this.info, this.meta.priority, other.name, other.priority});
         return false;
      } else {
         return true;
      }
   }

   protected void postInject(Target target, InjectionNodes.InjectionNode node) {
      super.postInject(target, node);
      if (node.getOriginalTarget() instanceof TypeInsnNode && node.getOriginalTarget().getOpcode() == 187) {
         RedirectInjector.ConstructorRedirectData meta = (RedirectInjector.ConstructorRedirectData)node.getDecoration("ctor");
         boolean wildcard = (Boolean)node.getDecoration("wildcard");
         if (wildcard && meta.injected == 0) {
            throw new InvalidInjectionException(this.info, this.annotationType + " ctor invocation was not found in " + target);
         }
      }

   }

   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
      MethodInsnNode methodNode = (MethodInsnNode)node.getCurrentTarget();
      boolean targetIsStatic = methodNode.getOpcode() == 184;
      Type ownerType = Type.getType("L" + methodNode.owner + ";");
      Type returnType = Type.getReturnType(methodNode.desc);
      Type[] args = Type.getArgumentTypes(methodNode.desc);
      Type[] stackVars = targetIsStatic ? args : (Type[])ObjectArrays.concat(ownerType, args);
      boolean injectTargetParams = false;
      String desc = Bytecode.getDescriptor(stackVars, returnType);
      if (!desc.equals(this.methodNode.desc)) {
         String alternateDesc = Bytecode.getDescriptor((Type[])ObjectArrays.concat(stackVars, target.arguments, Type.class), returnType);
         if (!alternateDesc.equals(this.methodNode.desc)) {
            throw new InvalidInjectionException(this.info, this.annotationType + " handler method " + this + " has an invalid signature, expected " + desc + " found " + this.methodNode.desc);
         }

         injectTargetParams = true;
      }

      InsnList insns = new InsnList();
      int extraLocals = Bytecode.getArgsSize(stackVars) + 1;
      int extraStack = 1;
      int[] argMap = this.storeArgs(target, stackVars, insns, 0);
      if (injectTargetParams) {
         int argSize = Bytecode.getArgsSize(target.arguments);
         extraLocals += argSize;
         extraStack += argSize;
         argMap = Ints.concat(new int[][]{argMap, target.getArgIndices()});
      }

      AbstractInsnNode insn = this.invokeHandlerWithArgs(this.methodArgs, insns, argMap);
      target.replaceNode(methodNode, insn, insns);
      target.addToLocals(extraLocals);
      target.addToStack(extraStack);
   }

   private void injectAtFieldAccess(Target target, InjectionNodes.InjectionNode node) {
      FieldInsnNode fieldNode = (FieldInsnNode)node.getCurrentTarget();
      int opCode = fieldNode.getOpcode();
      Type ownerType = Type.getType("L" + fieldNode.owner + ";");
      Type fieldType = Type.getType(fieldNode.desc);
      int targetDimensions = fieldType.getSort() == 9 ? fieldType.getDimensions() : 0;
      int handlerDimensions = this.returnType.getSort() == 9 ? this.returnType.getDimensions() : 0;
      if (handlerDimensions > targetDimensions) {
         throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this);
      } else {
         if (handlerDimensions == 0 && targetDimensions > 0) {
            int fuzz = (Integer)node.getDecoration("fuzz");
            int opcode = (Integer)node.getDecoration("opcode");
            this.injectAtArrayField(target, fieldNode, opCode, ownerType, fieldType, fuzz, opcode);
         } else {
            this.injectAtScalarField(target, fieldNode, opCode, ownerType, fieldType);
         }

      }
   }

   private void injectAtArrayField(Target target, FieldInsnNode fieldNode, int opCode, Type ownerType, Type fieldType, int fuzz, int opcode) {
      Type elementType = fieldType.getElementType();
      if (opCode != 178 && opCode != 180) {
         throw new InvalidInjectionException(this.info, "Unspported opcode " + Bytecode.getOpcodeName(opCode) + " for array access " + this.info);
      } else {
         AbstractInsnNode varNode;
         if (this.returnType.getSort() != 0) {
            if (opcode != 190) {
               opcode = elementType.getOpcode(46);
            }

            varNode = BeforeFieldAccess.findArrayNode(target.insns, fieldNode, opcode, fuzz);
            this.injectAtGetArray(target, fieldNode, varNode, ownerType, fieldType);
         } else {
            varNode = BeforeFieldAccess.findArrayNode(target.insns, fieldNode, elementType.getOpcode(79), fuzz);
            this.injectAtSetArray(target, fieldNode, varNode, ownerType, fieldType);
         }

      }
   }

   private void injectAtGetArray(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, Type ownerType, Type fieldType) {
      String handlerDesc = getGetArrayHandlerDescriptor(varNode, this.returnType, fieldType);
      boolean withArgs = this.checkDescriptor(handlerDesc, target, "array getter");
      this.injectArrayRedirect(target, fieldNode, varNode, withArgs, "array getter");
   }

   private void injectAtSetArray(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, Type ownerType, Type fieldType) {
      String handlerDesc = Bytecode.generateDescriptor((Object)null, (Object[])getArrayArgs(fieldType, 1, fieldType.getElementType()));
      boolean withArgs = this.checkDescriptor(handlerDesc, target, "array setter");
      this.injectArrayRedirect(target, fieldNode, varNode, withArgs, "array setter");
   }

   public void injectArrayRedirect(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, boolean withArgs, String type) {
      if (varNode == null) {
         String advice = "";
         throw new InvalidInjectionException(this.info, "Array element " + this.annotationType + " on " + this + " could not locate a matching " + type + " instruction in " + target + ". " + advice);
      } else {
         if (!this.isStatic) {
            target.insns.insertBefore(fieldNode, (AbstractInsnNode)(new VarInsnNode(25, 0)));
            target.addToStack(1);
         }

         InsnList invokeInsns = new InsnList();
         if (withArgs) {
            this.pushArgs(target.arguments, invokeInsns, target.getArgIndices(), 0, target.arguments.length);
            target.addToStack(Bytecode.getArgsSize(target.arguments));
         }

         target.replaceNode(varNode, this.invokeHandler(invokeInsns), invokeInsns);
      }
   }

   public void injectAtScalarField(Target target, FieldInsnNode fieldNode, int opCode, Type ownerType, Type fieldType) {
      AbstractInsnNode invoke = null;
      InsnList insns = new InsnList();
      if (opCode != 178 && opCode != 180) {
         if (opCode != 179 && opCode != 181) {
            throw new InvalidInjectionException(this.info, "Unspported opcode " + Bytecode.getOpcodeName(opCode) + " for " + this.info);
         }

         invoke = this.injectAtPutField(insns, target, fieldNode, opCode == 179, ownerType, fieldType);
      } else {
         invoke = this.injectAtGetField(insns, target, fieldNode, opCode == 178, ownerType, fieldType);
      }

      target.replaceNode(fieldNode, invoke, insns);
   }

   private AbstractInsnNode injectAtGetField(InsnList insns, Target target, FieldInsnNode node, boolean staticField, Type owner, Type fieldType) {
      String handlerDesc = staticField ? Bytecode.generateDescriptor(fieldType) : Bytecode.generateDescriptor(fieldType, owner);
      boolean withArgs = this.checkDescriptor(handlerDesc, target, "getter");
      if (!this.isStatic) {
         insns.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         if (!staticField) {
            insns.add((AbstractInsnNode)(new InsnNode(95)));
         }
      }

      if (withArgs) {
         this.pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
         target.addToStack(Bytecode.getArgsSize(target.arguments));
      }

      target.addToStack(this.isStatic ? 0 : 1);
      return this.invokeHandler(insns);
   }

   private AbstractInsnNode injectAtPutField(InsnList insns, Target target, FieldInsnNode node, boolean staticField, Type owner, Type fieldType) {
      String handlerDesc = staticField ? Bytecode.generateDescriptor((Object)null, fieldType) : Bytecode.generateDescriptor((Object)null, owner, fieldType);
      boolean withArgs = this.checkDescriptor(handlerDesc, target, "setter");
      if (!this.isStatic) {
         if (staticField) {
            insns.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
            insns.add((AbstractInsnNode)(new InsnNode(95)));
         } else {
            int marshallVar = target.allocateLocals(fieldType.getSize());
            insns.add((AbstractInsnNode)(new VarInsnNode(fieldType.getOpcode(54), marshallVar)));
            insns.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
            insns.add((AbstractInsnNode)(new InsnNode(95)));
            insns.add((AbstractInsnNode)(new VarInsnNode(fieldType.getOpcode(21), marshallVar)));
         }
      }

      if (withArgs) {
         this.pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
         target.addToStack(Bytecode.getArgsSize(target.arguments));
      }

      target.addToStack(!this.isStatic && !staticField ? 1 : 0);
      return this.invokeHandler(insns);
   }

   protected boolean checkDescriptor(String desc, Target target, String type) {
      if (this.methodNode.desc.equals(desc)) {
         return false;
      } else {
         int pos = desc.indexOf(41);
         String alternateDesc = String.format("%s%s%s", desc.substring(0, pos), Joiner.on("").join(target.arguments), desc.substring(pos));
         if (this.methodNode.desc.equals(alternateDesc)) {
            return true;
         } else {
            throw new InvalidInjectionException(this.info, this.annotationType + " method " + type + " " + this + " has an invalid signature. Expected " + desc + " but found " + this.methodNode.desc);
         }
      }
   }

   protected void injectAtConstructor(Target target, InjectionNodes.InjectionNode node) {
      RedirectInjector.ConstructorRedirectData meta = (RedirectInjector.ConstructorRedirectData)node.getDecoration("ctor");
      boolean wildcard = (Boolean)node.getDecoration("wildcard");
      TypeInsnNode newNode = (TypeInsnNode)node.getCurrentTarget();
      AbstractInsnNode dupNode = target.get(target.indexOf((AbstractInsnNode)newNode) + 1);
      MethodInsnNode initNode = target.findInitNodeFor(newNode);
      if (initNode == null) {
         if (!wildcard) {
            throw new InvalidInjectionException(this.info, this.annotationType + " ctor invocation was not found in " + target);
         }
      } else {
         boolean isAssigned = dupNode.getOpcode() == 89;
         String desc = initNode.desc.replace(")V", ")L" + newNode.desc + ";");
         boolean withArgs = false;

         try {
            withArgs = this.checkDescriptor(desc, target, "constructor");
         } catch (InvalidInjectionException var13) {
            if (!wildcard) {
               throw var13;
            }

            return;
         }

         if (isAssigned) {
            target.removeNode(dupNode);
         }

         if (this.isStatic) {
            target.removeNode(newNode);
         } else {
            target.replaceNode(newNode, (AbstractInsnNode)(new VarInsnNode(25, 0)));
         }

         InsnList insns = new InsnList();
         if (withArgs) {
            this.pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
            target.addToStack(Bytecode.getArgsSize(target.arguments));
         }

         this.invokeHandler(insns);
         if (isAssigned) {
            LabelNode nullCheckSucceeded = new LabelNode();
            insns.add((AbstractInsnNode)(new InsnNode(89)));
            insns.add((AbstractInsnNode)(new JumpInsnNode(199, nullCheckSucceeded)));
            this.throwException(insns, "java/lang/NullPointerException", this.annotationType + " constructor handler " + this + " returned null for " + newNode.desc.replace('/', '.'));
            insns.add((AbstractInsnNode)nullCheckSucceeded);
            target.addToStack(1);
         } else {
            insns.add((AbstractInsnNode)(new InsnNode(87)));
         }

         target.replaceNode(initNode, (InsnList)insns);
         ++meta.injected;
      }
   }

   private static String getGetArrayHandlerDescriptor(AbstractInsnNode varNode, Type returnType, Type fieldType) {
      return varNode != null && varNode.getOpcode() == 190 ? Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(fieldType, 0)) : Bytecode.generateDescriptor(returnType, (Object[])getArrayArgs(fieldType, 1));
   }

   private static Type[] getArrayArgs(Type fieldType, int extraDimensions, Type... extra) {
      int dimensions = fieldType.getDimensions() + extraDimensions;
      Type[] args = new Type[dimensions + extra.length];

      for(int i = 0; i < args.length; ++i) {
         args[i] = i == 0 ? fieldType : (i < dimensions ? Type.INT_TYPE : extra[dimensions - i]);
      }

      return args;
   }

   class ConstructorRedirectData {
      public static final String KEY = "ctor";
      public int injected = 0;
   }

   class Meta {
      public static final String KEY = "redirector";
      final int priority;
      final boolean isFinal;
      final String name;
      final String desc;

      public Meta(int priority, boolean isFinal, String name, String desc) {
         this.priority = priority;
         this.isFinal = isFinal;
         this.name = name;
         this.desc = desc;
      }

      RedirectInjector getOwner() {
         return RedirectInjector.this;
      }
   }
}
