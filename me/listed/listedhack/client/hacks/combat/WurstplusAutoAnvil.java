package me.listed.listedhack.client.hacks.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusAutoAnvil extends WurstplusHack {
   WurstplusSetting break_mode = this.create("Break Mode", "Break Mode", "Pickaxe", this.combobox(new String[]{"Pickaxe", "Feet", "None"}));
   WurstplusSetting target = this.create("Target", "Target", "Nearest", this.combobox(new String[]{"Nearest", "Looking"}));
   WurstplusSetting tick_delay = this.create("Tick Delay", "Tick Delay", 5, 1, 10);
   WurstplusSetting blocks_per_tick = this.create("Blocks Per Tick", "BPS", 5, 1, 10);
   WurstplusSetting range = this.create("Range", "Range", 4, 0, 6);
   WurstplusSetting decrease = this.create("Decrease", "Decrease", 4, 0, 8);
   WurstplusSetting h_distance = this.create("H Distance", "HDistance", 7, 1, 10);
   WurstplusSetting min_h = this.create("Min H", "MinH", 3, 1, 10);
   WurstplusSetting fail_stop = this.create("Fail Stop", "FailStop", 2, 1, 10);
   WurstplusSetting anti_crystal = this.create("AntiCrystal", "AntiCrystal", false);
   WurstplusSetting rotate = this.create("Rotate", "Rotate", true);
   WurstplusSetting fast_anvil = this.create("FastAnvil", "FastAnvil", true);
   WurstplusSetting chatMsg = this.create("Chat Messages", "messages", true);
   private boolean isSneaking = false;
   private boolean firstRun = false;
   private boolean noMaterials = false;
   private boolean hasMoved = false;
   private boolean isHole = true;
   private boolean enoughSpace = true;
   private boolean blockUp = false;
   private int oldSlot = -1;
   private int[] slot_mat = new int[]{-1, -1, -1, -1};
   private double[] enemyCoords;
   Double[][] sur_block;
   private int noKick;
   int[][] model = new int[][]{{1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1}};
   private int blocksPlaced = 0;
   private int delayTimeTicks = 0;
   private int offsetSteps = 0;
   private boolean pick_d = false;
   private EntityPlayer aimTarget;
   private static ArrayList<Vec3d> to_place = new ArrayList();

   public WurstplusAutoAnvil() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Anvil";
      this.tag = "AutoAnvil";
      this.description = "automatically anvils an opponent";
   }

   protected void enable() {
      if (this.break_mode.in("Pickaxe")) {
         this.pick_d = true;
      }

      this.blocksPlaced = 0;
      this.isHole = true;
      this.hasMoved = this.blockUp = false;
      this.firstRun = true;
      this.slot_mat = new int[]{-1, -1, -1, -1};
      to_place = new ArrayList();
      if (mc.field_71439_g == null) {
         this.set_disable();
      } else {
         this.oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
      }
   }

   protected void disable() {
      if (mc.field_71439_g != null) {
         if (this.chatMsg.get_value(true)) {
            WurstplusMessageUtil.send_client_error_message("AutoAnvil off");
            if (this.noMaterials) {
               WurstplusMessageUtil.send_client_error_message("No Materials Detected");
            } else if (!this.isHole) {
               WurstplusMessageUtil.send_client_error_message("Enemy is not in a hole");
            } else if (!this.enoughSpace) {
               WurstplusMessageUtil.send_client_error_message("Not enough space");
            } else if (this.hasMoved) {
               WurstplusMessageUtil.send_client_error_message("Enemy moved away from the hole");
            } else if (this.blockUp) {
               WurstplusMessageUtil.send_client_error_message("Enemy head blocked");
            } else {
               WurstplusMessageUtil.send_client_error_message("");
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
         this.firstRun = true;
      }
   }

   public void update() {
      if (mc.field_71439_g == null) {
         this.set_disable();
      } else {
         if (this.firstRun) {
            if (this.target.in("Nearest")) {
               this.aimTarget = findClosestTarget((double)this.range.get_value(1), this.aimTarget);
            } else if (this.target.in("Looking")) {
               this.aimTarget = findLookingPlayer((double)this.range.get_value(1));
            }

            if (this.aimTarget == null) {
               return;
            }

            this.firstRun = false;
            if (this.getMaterialsSlot()) {
               if (this.is_in_hole()) {
                  this.enemyCoords = new double[]{this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v};
                  this.enoughSpace = this.createStructure();
               } else {
                  this.isHole = false;
               }
            } else {
               this.noMaterials = true;
            }
         } else {
            if (this.delayTimeTicks < this.tick_delay.get_value(1)) {
               ++this.delayTimeTicks;
               return;
            }

            this.delayTimeTicks = 0;
            if ((int)this.enemyCoords[0] != (int)this.aimTarget.field_70165_t || (int)this.enemyCoords[2] != (int)this.aimTarget.field_70161_v) {
               this.hasMoved = true;
            }

            if (!(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + 2.0D, this.enemyCoords[2]) instanceof BlockAir) || !(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + 3.0D, this.enemyCoords[2]) instanceof BlockAir)) {
               this.blockUp = true;
            }
         }

         this.blocksPlaced = 0;
         if (!this.noMaterials && this.isHole && this.enoughSpace && !this.hasMoved && !this.blockUp) {
            this.noKick = 0;

            while(this.blocksPlaced <= this.blocks_per_tick.get_value(1)) {
               int maxSteps = to_place.size();
               if (this.offsetSteps >= maxSteps) {
                  this.offsetSteps = 0;
                  break;
               }

               BlockPos offsetPos = new BlockPos((Vec3d)to_place.get(this.offsetSteps));
               BlockPos targetPos = (new BlockPos(this.aimTarget.func_174791_d())).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p());
               boolean tryPlacing = true;
               if (this.offsetSteps > 0 && this.offsetSteps < to_place.size() - 1) {
                  Iterator var5 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(targetPos)).iterator();

                  while(var5.hasNext()) {
                     Entity entity = (Entity)var5.next();
                     if (entity instanceof EntityPlayer) {
                        tryPlacing = false;
                        break;
                     }
                  }
               }

               if (tryPlacing && this.placeBlock(targetPos, this.offsetSteps)) {
                  ++this.blocksPlaced;
               }

               ++this.offsetSteps;
               if (this.isSneaking) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                  this.isSneaking = false;
               }

               if (this.noKick == this.fail_stop.get_value(1)) {
                  break;
               }
            }

         } else {
            this.set_disable();
         }
      }
   }

   public static boolean basicChecksEntity(Entity pl) {
      return pl == mc.field_71439_g || WurstplusFriendUtil.isFriend(pl.func_70005_c_()) || pl.field_70128_L;
   }

   public static EntityPlayer findLookingPlayer(double rangeMax) {
      ArrayList<EntityPlayer> listPlayer = new ArrayList();
      Iterator var3 = mc.field_71441_e.field_73010_i.iterator();

      while(var3.hasNext()) {
         EntityPlayer playerSin = (EntityPlayer)var3.next();
         if (!basicChecksEntity(playerSin) && (double)mc.field_71439_g.func_70032_d(playerSin) <= rangeMax) {
            listPlayer.add(playerSin);
         }
      }

      EntityPlayer target = null;
      Vec3d positionEyes = mc.field_71439_g.func_174824_e(mc.func_184121_ak());
      Vec3d rotationEyes = mc.field_71439_g.func_70676_i(mc.func_184121_ak());
      int precision = 2;

      for(int i = 0; i < (int)rangeMax; ++i) {
         for(int j = precision; j > 0; --j) {
            Iterator var9 = listPlayer.iterator();

            while(var9.hasNext()) {
               EntityPlayer targetTemp = (EntityPlayer)var9.next();
               AxisAlignedBB playerBox = targetTemp.func_174813_aQ();
               double xArray = positionEyes.field_72450_a + rotationEyes.field_72450_a * (double)i + rotationEyes.field_72450_a / (double)j;
               double yArray = positionEyes.field_72448_b + rotationEyes.field_72448_b * (double)i + rotationEyes.field_72448_b / (double)j;
               double zArray = positionEyes.field_72449_c + rotationEyes.field_72449_c * (double)i + rotationEyes.field_72449_c / (double)j;
               if (playerBox.field_72337_e >= yArray && playerBox.field_72338_b <= yArray && playerBox.field_72336_d >= xArray && playerBox.field_72340_a <= xArray && playerBox.field_72334_f >= zArray && playerBox.field_72339_c <= zArray) {
                  target = targetTemp;
               }
            }
         }
      }

      return target;
   }

   public static EntityPlayer findClosestTarget(double rangeMax, EntityPlayer aimTarget) {
      List<EntityPlayer> playerList = mc.field_71441_e.field_73010_i;
      EntityPlayer closestTarget_test = null;
      Iterator var5 = playerList.iterator();

      while(true) {
         while(true) {
            EntityPlayer entityPlayer;
            do {
               if (!var5.hasNext()) {
                  return closestTarget_test;
               }

               entityPlayer = (EntityPlayer)var5.next();
            } while(basicChecksEntity(entityPlayer));

            if (aimTarget == null && (double)mc.field_71439_g.func_70032_d(entityPlayer) <= rangeMax) {
               closestTarget_test = entityPlayer;
            } else if (aimTarget != null && (double)mc.field_71439_g.func_70032_d(entityPlayer) <= rangeMax && mc.field_71439_g.func_70032_d(entityPlayer) < mc.field_71439_g.func_70032_d(aimTarget)) {
               closestTarget_test = entityPlayer;
            }
         }
      }
   }

   private boolean placeBlock(BlockPos pos, int step) {
      Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
      EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
      if (step == to_place.size() - 1 && block instanceof BlockAnvil && side != null) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
         ++this.noKick;
      }

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
            int utilSlot = step == 0 && this.break_mode.in("Feet") ? 2 : (step == to_place.size() - 1 ? 1 : 0);
            if (mc.field_71439_g.field_71071_by.func_70301_a(this.slot_mat[utilSlot]) != ItemStack.field_190927_a) {
               if (mc.field_71439_g.field_71071_by.field_70461_c != this.slot_mat[utilSlot]) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[utilSlot];
               }

               if (!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                  this.isSneaking = true;
               }

               if (this.rotate.get_value(true)) {
                  WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
               }

               int bef = mc.field_71467_ac;
               if (step == to_place.size() - 1) {
                  EntityPlayer found = this.getPlayerFromName(this.aimTarget.func_146103_bH().getName());
                  if (found == null || (int)found.field_70165_t != (int)this.enemyCoords[0] || (int)found.field_70161_v != (int)this.enemyCoords[2]) {
                     this.hasMoved = true;
                     return false;
                  }

                  if (this.fast_anvil.get_value(true)) {
                     mc.field_71467_ac = 0;
                  }
               }

               mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
               if (this.fast_anvil.get_value(true) && step == to_place.size() - 1) {
                  mc.field_71467_ac = bef;
               }

               if (this.pick_d && step == to_place.size() - 1) {
                  EnumFacing prova = WurstplusBlockInteractHelper.getPlaceableSide(new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]));
                  if (prova != null) {
                     mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[3];
                     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]), prova));
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]), prova));
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   private EntityPlayer getPlayerFromName(String name) {
      List<EntityPlayer> playerList = mc.field_71441_e.field_73010_i;
      Iterator var3 = playerList.iterator();

      EntityPlayer entityPlayer;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         entityPlayer = (EntityPlayer)var3.next();
      } while(!entityPlayer.func_146103_bH().getName().equals(name));

      return entityPlayer;
   }

   private boolean getMaterialsSlot() {
      boolean feet = false;
      boolean pick = false;
      if (this.break_mode.in("Feet")) {
         feet = true;
      }

      if (this.break_mode.in("Pick")) {
         pick = true;
      }

      int i;
      for(i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a) {
            if (pick && stack.func_77973_b() instanceof ItemPickaxe) {
               this.slot_mat[3] = i;
            }

            if (stack.func_77973_b() instanceof ItemBlock) {
               Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
               if (block instanceof BlockObsidian) {
                  this.slot_mat[0] = i;
               } else if (block instanceof BlockAnvil) {
                  this.slot_mat[1] = i;
               } else if (feet && (block instanceof BlockPressurePlate || block instanceof BlockButton)) {
                  this.slot_mat[2] = i;
               }
            }
         }
      }

      i = 0;
      int[] var8 = this.slot_mat;
      int var9 = var8.length;

      for(int var6 = 0; var6 < var9; ++var6) {
         int val = var8[var6];
         if (val != -1) {
            ++i;
         }
      }

      return i - (!feet && !pick ? 0 : 1) == 2;
   }

   private boolean is_in_hole() {
      this.sur_block = new Double[][]{{this.aimTarget.field_70165_t + 1.0D, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v}, {this.aimTarget.field_70165_t - 1.0D, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v}, {this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v + 1.0D}, {this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v - 1.0D}};
      this.enemyCoords = new double[]{this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v};
      return !(this.get_block(this.sur_block[0][0], this.sur_block[0][1], this.sur_block[0][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[1][0], this.sur_block[1][1], this.sur_block[1][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[2][0], this.sur_block[2][1], this.sur_block[2][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[3][0], this.sur_block[3][1], this.sur_block[3][2]) instanceof BlockAir);
   }

   private boolean createStructure() {
      if (this.break_mode.in("Feet")) {
         to_place.add(new Vec3d(0.0D, 0.0D, 0.0D));
      }

      to_place.add(new Vec3d(1.0D, 1.0D, 0.0D));
      to_place.add(new Vec3d(-1.0D, 1.0D, 0.0D));
      to_place.add(new Vec3d(0.0D, 1.0D, 1.0D));
      to_place.add(new Vec3d(0.0D, 1.0D, -1.0D));
      to_place.add(new Vec3d(1.0D, 2.0D, 0.0D));
      to_place.add(new Vec3d(-1.0D, 2.0D, 0.0D));
      to_place.add(new Vec3d(0.0D, 2.0D, 1.0D));
      to_place.add(new Vec3d(0.0D, 2.0D, -1.0D));
      int hDistanceMod = this.h_distance.get_value(1);

      for(double distEnemy = (double)mc.field_71439_g.func_70032_d(this.aimTarget); distEnemy > (double)this.decrease.get_value(1); distEnemy -= (double)this.decrease.get_value(1)) {
         --hDistanceMod;
      }

      int add = (int)(mc.field_71439_g.field_70163_u - this.aimTarget.field_70163_u);
      if (add > 1) {
         boolean var17 = true;
      }

      hDistanceMod = (int)((double)hDistanceMod + (mc.field_71439_g.field_70163_u - this.aimTarget.field_70163_u));
      double min_found = Double.MAX_VALUE;
      double[] var10000 = new double[]{-1.0D, -1.0D, -1.0D};
      int cor = -1;
      int i = 0;
      Double[][] var13 = this.sur_block;
      int incr = var13.length;

      int ij;
      for(ij = 0; ij < incr; ++ij) {
         Double[] cord_b = var13[ij];
         var10000 = new double[]{cord_b[0], cord_b[1], cord_b[2]};
         double distance_now;
         if ((distance_now = mc.field_71439_g.func_174818_b(new BlockPos(cord_b[0], cord_b[1], cord_b[2]))) < min_found) {
            min_found = distance_now;
            cor = i;
         }

         ++i;
      }

      boolean possible = false;

      for(incr = 1; this.get_block(this.enemyCoords[0], this.enemyCoords[1] + (double)incr, this.enemyCoords[2]) instanceof BlockAir && incr < hDistanceMod; ++incr) {
         if (!this.anti_crystal.get_value(true)) {
            to_place.add(new Vec3d((double)this.model[cor][0], (double)(this.model[cor][1] + incr), (double)this.model[cor][2]));
         } else {
            for(ij = 0; ij < 4; ++ij) {
               to_place.add(new Vec3d((double)this.model[ij][0], (double)(this.model[ij][1] + incr), (double)this.model[ij][2]));
            }
         }
      }

      if (!(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + (double)incr, this.enemyCoords[2]) instanceof BlockAir)) {
         --incr;
      }

      if (incr >= this.min_h.get_value(1)) {
         possible = true;
      }

      to_place.add(new Vec3d(0.0D, (double)(this.model[cor][1] + incr - 1), 0.0D));
      return possible;
   }

   private Block get_block(double x, double y, double z) {
      return mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
   }
}
