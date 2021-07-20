package me.listed.listedhack.client.hacks.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventDamageBlock;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.listedhack.client.util.WurstplusHoleUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusCevBreaker extends WurstplusHack {
   WurstplusSetting target = this.create("Target", "Target", "Nearest", this.combobox(new String[]{"Nearest", "Looking"}));
   WurstplusSetting breakCrystal = this.create("Break Crystal", "BreakMode", "Packet", this.combobox(new String[]{"Vanilla", "Packet", "None"}));
   WurstplusSetting placeCrystal = this.create("Place Crystal", "PlaceCrystal", true);
   WurstplusSetting enemyRange = this.create("Range", "CivBreakerEnemyRange", 4.9D, 1.0D, 6.0D);
   WurstplusSetting supDelay = this.create("Sup Delay", "SupDelay", 1, 0, 4);
   WurstplusSetting endDelay = this.create("End Delay", "EndDelay", 1, 0, 4);
   WurstplusSetting hitDelay = this.create("Hit Delay", "HitDelay", 2, 0, 20);
   WurstplusSetting confirmBreak = this.create("Confirm Break", "ConfirmBreak", true);
   WurstplusSetting confirmPlace = this.create("Confirm Place", "ConfirmPlace", true);
   WurstplusSetting crystalDelay = this.create("Crystal Delay", "CrystalDelay", 2, 0, 20);
   WurstplusSetting antiWeakness = this.create("Anti Weakness", "AntiWeakness", false);
   WurstplusSetting antiStep = this.create("Anti Step", "AntiStep", false);
   WurstplusSetting trapPlayer = this.create("Trap Player", "TrapPlayer", false);
   WurstplusSetting fastPlace = this.create("Fast Place", "FastPlace", false);
   WurstplusSetting blocksPerTick = this.create("Blocks Per Tick", "BPS", 4, 2, 6);
   WurstplusSetting pickSwitchTick = this.create("PickSwitchTick", "PickSwitchTick", 100, 0, 500);
   WurstplusSetting switchSword = this.create("Switch Sword", "SwitchSword", false);
   WurstplusSetting rotate = this.create("Rotate", "Rotate", true);
   WurstplusSetting midHitDelay = this.create("Mid HitDelay", "MidHitDelay", 1, 0, 5);
   WurstplusSetting chatMsg = this.create("Chat Messages", "Messages", true);
   private boolean noMaterials = false;
   private boolean hasMoved = false;
   private boolean isSneaking = false;
   private boolean isHole = true;
   private boolean enoughSpace = true;
   private boolean broken;
   private boolean stoppedCa;
   private boolean deadPl;
   private boolean rotationPlayerMoved;
   private boolean prevBreak;
   private int oldSlot = -1;
   private int stage;
   private int delayTimeTicks;
   private int hitTryTick;
   private int tickPick;
   private final int[][] model = new int[][]{{1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1}};
   private int[] slot_mat;
   private int[] delayTable;
   private int[] enemyCoordsInt;
   private double[] enemyCoordsDouble;
   private WurstplusCevBreaker.structureTemp toPlace;
   Double[][] sur_block = new Double[4][3];
   private EntityPlayer aimTarget;
   @EventHandler
   private final Listener<WurstplusEventDamageBlock> listener2 = new Listener((event) -> {
      if (this.enemyCoordsInt != null && event.getPos().func_177958_n() + (event.getPos().func_177958_n() < 0 ? 1 : 0) == this.enemyCoordsInt[0] && event.getPos().func_177952_p() + (event.getPos().func_177952_p() < 0 ? 1 : 0) == this.enemyCoordsInt[2]) {
         this.destroyCrystalAlgo();
      }

   }, new Predicate[0]);

   public WurstplusCevBreaker() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Civ Breaker";
      this.tag = "CivBreaker";
      this.description = "crystals an opponent from above their heads";
   }

   protected void enable() {
      this.initValues();
      if (!this.getAimTarget()) {
         this.playerChecks();
      }
   }

   private boolean getAimTarget() {
      if (this.target.in("Nearest")) {
         this.aimTarget = WurstplusEntityUtil.findClosestTarget((double)this.enemyRange.get_value(1), this.aimTarget);
      } else {
         this.aimTarget = WurstplusPlayerUtil.findLookingPlayer((double)this.enemyRange.get_value(1));
      }

      if (this.aimTarget == null || !this.target.in("Looking")) {
         if (!this.target.in("Looking") && this.aimTarget == null) {
            this.set_disable();
         }

         if (this.aimTarget == null) {
            return true;
         }
      }

      return false;
   }

   private void playerChecks() {
      if (this.getMaterialsSlot()) {
         if (this.is_in_hole()) {
            this.enemyCoordsDouble = new double[]{this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v};
            this.enemyCoordsInt = new int[]{(int)this.enemyCoordsDouble[0], (int)this.enemyCoordsDouble[1], (int)this.enemyCoordsDouble[2]};
            this.enoughSpace = this.createStructure();
         } else {
            this.isHole = false;
         }
      } else {
         this.noMaterials = true;
      }

   }

   private void initValues() {
      this.aimTarget = null;
      this.delayTable = new int[]{this.supDelay.get_value(1), this.crystalDelay.get_value(1), this.hitDelay.get_value(1), this.endDelay.get_value(1)};
      this.toPlace = new WurstplusCevBreaker.structureTemp(0.0D, 0, new ArrayList());
      this.isHole = true;
      this.hasMoved = this.rotationPlayerMoved = this.deadPl = this.broken = false;
      this.slot_mat = new int[]{-1, -1, -1, -1};
      this.stage = this.delayTimeTicks = 0;
      if (mc.field_71439_g == null) {
         this.set_disable();
      } else {
         if (this.chatMsg.get_value(true)) {
            WurstplusMessageUtil.send_client_error_message("CevBreaker off");
         }

         this.oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         this.stoppedCa = false;
      }
   }

   protected void disable() {
      if (mc.field_71439_g != null) {
         if (this.chatMsg.get_value(true)) {
            String output = "";
            String materialsNeeded = "";
            if (this.aimTarget == null) {
               output = "No target found";
            } else if (this.noMaterials) {
               output = "No Materials Detected";
               materialsNeeded = this.getMissingMaterials();
            } else if (!this.isHole) {
               output = "Enemy is not in hole";
            } else if (!this.enoughSpace) {
               output = "Not enough space";
            } else if (this.hasMoved) {
               output = "Out of range";
            } else if (this.deadPl) {
               output = "Enemy is dead ";
            }

            WurstplusMessageUtil.send_client_error_message(output + "");
            if (!materialsNeeded.equals("")) {
               WurstplusMessageUtil.send_client_error_message("Materials missing:" + materialsNeeded);
            }
         }

         if (this.isSneaking) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
            this.isSneaking = false;
         }

         if (this.oldSlot != mc.field_71439_g.field_71071_by.field_70461_c && this.oldSlot != -1) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.oldSlot;
            this.oldSlot = -1;
         }

         this.noMaterials = false;
      }
   }

   private String getMissingMaterials() {
      StringBuilder output = new StringBuilder();
      if (this.slot_mat[0] == -1) {
         output.append(" Obsidian");
      }

      if (this.slot_mat[1] == -1) {
         output.append(" Crystal");
      }

      if ((this.antiWeakness.get_value(true) || this.switchSword.get_value(true)) && this.slot_mat[3] == -1) {
         output.append(" Sword");
      }

      if (this.slot_mat[2] == -1) {
         output.append(" Pickaxe");
      }

      return output.toString();
   }

   public void update() {
      if (mc.field_71439_g != null && !mc.field_71439_g.field_70128_L) {
         if (this.delayTimeTicks < this.delayTable[this.stage]) {
            ++this.delayTimeTicks;
         } else {
            this.delayTimeTicks = 0;
            if (this.enemyCoordsDouble != null && this.aimTarget != null) {
               if (this.aimTarget.field_70128_L) {
                  this.deadPl = true;
               }

               if ((int)this.aimTarget.field_70165_t != (int)this.enemyCoordsDouble[0] || (int)this.aimTarget.field_70161_v != (int)this.enemyCoordsDouble[2]) {
                  this.hasMoved = true;
               }

               if (!this.checkVariable()) {
                  if (this.placeSupport()) {
                     switch(this.stage) {
                     case 1:
                        this.placeBlockThings(this.stage);
                        if (this.fastPlace.get_value(true)) {
                           this.placeCrystal();
                        }

                        this.prevBreak = false;
                        this.tickPick = 0;
                        break;
                     case 2:
                        if (this.confirmPlace.get_value(true) && !(WurstplusBlockInteractHelper.getBlock(this.compactBlockPos(0)) instanceof BlockObsidian)) {
                           --this.stage;
                           return;
                        }

                        this.placeCrystal();
                        break;
                     case 3:
                        if (this.confirmPlace.get_value(true) && this.getCrystal() == null) {
                           --this.stage;
                           return;
                        }

                        int switchValue = 3;
                        if (!this.switchSword.get_value(true) || this.tickPick == this.pickSwitchTick.get_value(1) || this.tickPick++ == 0) {
                           switchValue = 2;
                        }

                        if (mc.field_71439_g.field_71071_by.field_70461_c != this.slot_mat[switchValue]) {
                           mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[switchValue];
                        }

                        BlockPos obbyBreak = new BlockPos(this.enemyCoordsDouble[0], (double)(this.enemyCoordsInt[1] + 2), this.enemyCoordsDouble[2]);
                        if (WurstplusBlockInteractHelper.getBlock(obbyBreak) instanceof BlockObsidian) {
                           EnumFacing sideBreak = WurstplusBlockInteractHelper.getPlaceableSide(obbyBreak);
                           if (sideBreak != null) {
                              if (this.breakCrystal.in("Packet")) {
                                 if (!this.prevBreak) {
                                    mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                                    mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, obbyBreak, sideBreak));
                                    mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, obbyBreak, sideBreak));
                                    this.prevBreak = true;
                                 }
                              } else if (this.breakCrystal.in("Normal")) {
                                 mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                                 mc.field_71442_b.func_180512_c(obbyBreak, sideBreak);
                              }
                           }
                        } else {
                           this.destroyCrystalAlgo();
                        }
                     }
                  }

               }
            } else {
               if (this.aimTarget == null) {
                  this.aimTarget = WurstplusPlayerUtil.findLookingPlayer((double)this.enemyRange.get_value(1));
                  if (this.aimTarget != null) {
                     this.playerChecks();
                  }
               } else {
                  this.checkVariable();
               }

            }
         }
      } else {
         this.set_disable();
      }
   }

   private void placeCrystal() {
      this.placeBlockThings(this.stage);
   }

   private Entity getCrystal() {
      Iterator var1 = mc.field_71441_e.field_72996_f.iterator();

      Entity t;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         t = (Entity)var1.next();
      } while(!(t instanceof EntityEnderCrystal) || (int)t.field_70165_t != this.enemyCoordsInt[0] || (int)t.field_70161_v != this.enemyCoordsInt[2] || t.field_70163_u - (double)this.enemyCoordsInt[1] != 3.0D);

      return t;
   }

   public void destroyCrystalAlgo() {
      Entity crystal = this.getCrystal();
      if (this.confirmBreak.get_value(true) && this.broken && crystal == null) {
         this.stage = 0;
         this.broken = false;
      }

      if (crystal != null) {
         this.breakCrystalPiston(crystal);
         if (this.confirmBreak.get_value(true)) {
            this.broken = true;
         } else {
            this.stage = 0;
         }
      } else {
         this.stage = 0;
      }

   }

   private void breakCrystalPiston(Entity crystal) {
      if (this.hitTryTick++ >= this.midHitDelay.get_value(1)) {
         this.hitTryTick = 0;
         if (this.antiWeakness.get_value(true)) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[3];
         }

         if (this.breakCrystal.in("Vanilla")) {
            WurstplusEntityUtil.attackEntity(crystal);
         } else if (this.breakCrystal.in("Packet")) {
            try {
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(crystal));
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            } catch (NullPointerException var3) {
            }
         }

      }
   }

   private boolean placeSupport() {
      int checksDone = 0;
      int blockDone = 0;
      if (this.toPlace.supportBlock > 0) {
         do {
            BlockPos targetPos = this.getTargetPos(checksDone);
            if (this.placeBlock(targetPos, 0)) {
               ++blockDone;
               if (blockDone == this.blocksPerTick.get_value(1)) {
                  return false;
               }
            }

            ++checksDone;
         } while(checksDone != this.toPlace.supportBlock);
      }

      this.stage = this.stage == 0 ? 1 : this.stage;
      return true;
   }

   private boolean placeBlock(BlockPos pos, int step) {
      Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
      EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
      if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
         return false;
      } else if (side == null) {
         return false;
      } else {
         BlockPos neighbour = pos.func_177972_a(side);
         EnumFacing opposite = side.func_176734_d();
         if (!WurstplusBlockInteractHelper.canBeClicked(neighbour)) {
            return false;
         } else {
            Vec3d hitVec = (new Vec3d(neighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
            Block neighbourBlock = mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
            if (this.slot_mat[step] != 11 && mc.field_71439_g.field_71071_by.func_70301_a(this.slot_mat[step]) == ItemStack.field_190927_a) {
               this.noMaterials = true;
               return false;
            } else {
               if (mc.field_71439_g.field_71071_by.field_70461_c != this.slot_mat[step]) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[step] == 11 ? mc.field_71439_g.field_71071_by.field_70461_c : this.slot_mat[step];
               }

               if (!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                  this.isSneaking = true;
               }

               if (this.rotate.get_value(true)) {
                  WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec, true);
               }

               EnumHand handSwing = EnumHand.MAIN_HAND;
               if (this.slot_mat[step] == 11) {
                  handSwing = EnumHand.OFF_HAND;
               }

               mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbour, opposite, hitVec, handSwing);
               mc.field_71439_g.func_184609_a(handSwing);
               return true;
            }
         }
      }
   }

   public void placeBlockThings(int step) {
      if (step != 1 || this.placeCrystal.get_value(true)) {
         --step;
         BlockPos targetPos = this.compactBlockPos(step);
         this.placeBlock(targetPos, step);
      }

      ++this.stage;
   }

   public BlockPos compactBlockPos(int step) {
      BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + step));
      return new BlockPos(this.enemyCoordsDouble[0] + (double)offsetPos.func_177958_n(), this.enemyCoordsDouble[1] + (double)offsetPos.func_177956_o(), this.enemyCoordsDouble[2] + (double)offsetPos.func_177952_p());
   }

   private BlockPos getTargetPos(int idx) {
      BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(idx));
      return new BlockPos(this.enemyCoordsDouble[0] + (double)offsetPos.func_177958_n(), this.enemyCoordsDouble[1] + (double)offsetPos.func_177956_o(), this.enemyCoordsDouble[2] + (double)offsetPos.func_177952_p());
   }

   private boolean checkVariable() {
      if (!this.noMaterials && this.isHole && this.enoughSpace && !this.hasMoved && !this.deadPl && !this.rotationPlayerMoved) {
         return false;
      } else {
         this.set_disable();
         return true;
      }
   }

   private boolean createStructure() {
      if (!((ResourceLocation)Objects.requireNonNull(WurstplusBlockInteractHelper.getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 2.0D, this.enemyCoordsDouble[2]).getRegistryName())).toString().toLowerCase().contains("bedrock") && WurstplusBlockInteractHelper.getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 3.0D, this.enemyCoordsDouble[2]) instanceof BlockAir && WurstplusBlockInteractHelper.getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 4.0D, this.enemyCoordsDouble[2]) instanceof BlockAir) {
         double min_found = Double.MAX_VALUE;
         int cor = 0;
         int i = 0;
         Double[][] var7 = this.sur_block;
         int high = var7.length;

         for(int var9 = 0; var9 < high; ++var9) {
            Double[] cord_b = var7[var9];
            double distance_now;
            if ((distance_now = mc.field_71439_g.func_174818_b(new BlockPos(cord_b[0], cord_b[1], cord_b[2]))) < min_found) {
               min_found = distance_now;
               cor = i;
            }

            ++i;
         }

         int bias = this.enemyCoordsInt[0] != (int)mc.field_71439_g.field_70165_t && this.enemyCoordsInt[2] != (int)mc.field_71439_g.field_70161_v ? 1 : -1;
         this.toPlace.to_place.add(new Vec3d((double)(this.model[cor][0] * bias), 1.0D, (double)(this.model[cor][2] * bias)));
         this.toPlace.to_place.add(new Vec3d((double)(this.model[cor][0] * bias), 2.0D, (double)(this.model[cor][2] * bias)));
         this.toPlace.supportBlock = 2;
         if (this.trapPlayer.get_value(true) || this.antiStep.get_value(true)) {
            for(high = 1; high < 3; ++high) {
               if (high != 2 || this.antiStep.get_value(true)) {
                  int[][] var15 = this.model;
                  int var16 = var15.length;

                  for(int var11 = 0; var11 < var16; ++var11) {
                     int[] modelBas = var15[var11];
                     Vec3d toAdd = new Vec3d((double)modelBas[0], (double)high, (double)modelBas[2]);
                     if (!this.toPlace.to_place.contains(toAdd)) {
                        this.toPlace.to_place.add(toAdd);
                        ++this.toPlace.supportBlock;
                     }
                  }
               }
            }
         }

         this.toPlace.to_place.add(new Vec3d(0.0D, 2.0D, 0.0D));
         this.toPlace.to_place.add(new Vec3d(0.0D, 3.0D, 0.0D));
         return true;
      } else {
         return false;
      }
   }

   private boolean getMaterialsSlot() {
      if (mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemEndCrystal) {
         this.slot_mat[1] = 11;
      }

      int i;
      for(i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a) {
            if (this.slot_mat[1] == -1 && stack.func_77973_b() instanceof ItemEndCrystal) {
               this.slot_mat[1] = i;
            } else if ((this.antiWeakness.get_value(true) || this.switchSword.get_value(true)) && stack.func_77973_b() instanceof ItemSword) {
               this.slot_mat[3] = i;
            } else if (stack.func_77973_b() instanceof ItemPickaxe) {
               this.slot_mat[2] = i;
            }

            if (stack.func_77973_b() instanceof ItemBlock) {
               Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
               if (block instanceof BlockObsidian) {
                  this.slot_mat[0] = i;
               }
            }
         }
      }

      i = 0;
      int[] var6 = this.slot_mat;
      int var7 = var6.length;

      for(int var4 = 0; var4 < var7; ++var4) {
         int val = var6[var4];
         if (val != -1) {
            ++i;
         }
      }

      return i >= 3 + (!this.antiWeakness.get_value(true) && !this.switchSword.get_value(true) ? 0 : 1);
   }

   private boolean is_in_hole() {
      this.sur_block = new Double[][]{{this.aimTarget.field_70165_t + 1.0D, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v}, {this.aimTarget.field_70165_t - 1.0D, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v}, {this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v + 1.0D}, {this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v - 1.0D}};
      return WurstplusHoleUtil.isHole(WurstplusEntityUtil.getPosition(this.aimTarget), true, true).getType() != WurstplusHoleUtil.HoleType.NONE;
   }

   private static class structureTemp {
      public double distance;
      public int supportBlock;
      public ArrayList<Vec3d> to_place;
      public int direction;

      public structureTemp(double distance, int supportBlock, ArrayList<Vec3d> to_place) {
         this.distance = distance;
         this.supportBlock = supportBlock;
         this.to_place = to_place;
         this.direction = -1;
      }
   }
}
