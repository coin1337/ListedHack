package me.listed.listedhack.client.hacks.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.EventNetworkPacketEvent;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WurstplusAutoPiston extends WurstplusHack {
   private boolean noMaterials = false;
   private boolean hasMoved = false;
   private boolean isSneaking = false;
   private boolean yUnder = false;
   private boolean isHole = true;
   private boolean enoughSpace = true;
   private boolean redstoneBlockMode = false;
   private boolean fastModeActive = false;
   private boolean broken;
   private boolean brokenCrystalBug;
   private boolean brokenRedstoneTorch;
   private boolean deadPl;
   private boolean rotationPlayerMoved;
   private int oldSlot = -1;
   private int stage;
   private int delayTimeTicks;
   private int stuck = 0;
   private int hitTryTick;
   private int nCrystal;
   private long startTime;
   private long endTime;
   private int[] slot_mat;
   private int[] delayTable;
   private int[] meCoordsInt;
   private int[] enemyCoordsInt;
   private double[] enemyCoordsDouble;
   private WurstplusAutoPiston.structureTemp toPlace;
   int[][] disp_surblock = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}};
   Double[][] sur_block = new Double[4][3];
   private EntityPlayer aimTarget;
   WurstplusSetting breakType = this.create("Break Types", "AutoPistonBreakTypes", "Swing", this.combobox(new String[]{"Swing", "Packet"}));
   WurstplusSetting placeMode = this.create("Place Mode", "AutoPistonPlaceMode", "Torch", this.combobox(new String[]{"Block", "Torch", "Both"}));
   WurstplusSetting target = this.create("Target Mode", "AutoPistonTargetMode", "Nearest", this.combobox(new String[]{"Nearest", "Looking"}));
   WurstplusSetting range = this.create("Range", "AutoPistonRange", 4.91D, 0.0D, 6.0D);
   WurstplusSetting crystalDeltaBreak = this.create("Center Break", "AutoPistonCenterBreak", 1, 0, 5);
   WurstplusSetting blocksPerTick = this.create("Blocks Per Tick", "AutoPistonBPS", 4, 0, 20);
   WurstplusSetting supBlocksDelay = this.create("Surround Delay", "AutoPistonSurroundDelay", 4, 0, 20);
   WurstplusSetting startDelay = this.create("Start Delay", "AutoPistonStartDelay", 4, 0, 20);
   WurstplusSetting pistonDelay = this.create("Piston Delay", "AutoPistonPistonDelay", 2, 0, 20);
   WurstplusSetting crystalDelay = this.create("Crystal Delay", "AutoPistonCrystalDelay", 2, 0, 20);
   WurstplusSetting midHitDelay = this.create("Mid HitDelay", "AutoPistonMidHitDelay", 5, 0, 20);
   WurstplusSetting hitDelay = this.create("Hit Delay", "AutoPistonHitDelay", 2, 0, 20);
   WurstplusSetting stuckDetector = this.create("Stuck Check", "AutoPistonStuckDetector", 35, 0, 200);
   WurstplusSetting maxYincr = this.create("Max Y", "AutoPistonMaxY", 3, 0, 5);
   WurstplusSetting blockPlayer = this.create("Block Player", "AutoPistonBlockPlayer", true);
   WurstplusSetting rotate = this.create("Rotate", "AutoPistonRotate", false);
   WurstplusSetting confirmBreak = this.create("Confirm Break", "AutoPistonConfirmBreak", true);
   WurstplusSetting confirmPlace = this.create("Confirm Place", "AutoPistonConfirmPlace", true);
   WurstplusSetting allowCheapMode = this.create("Cheap Mode", "AutoPistonCheapMode", false);
   WurstplusSetting betterPlacement = this.create("Better Place", "AutoPistonBetterPlace", true);
   WurstplusSetting bypassObsidian = this.create("Bypass", "AutoPistonBypass", false);
   WurstplusSetting antiWeakness = this.create("Anti Weakness", "AutoPistonAntiWeakness", false);
   WurstplusSetting chatMsg = this.create("Chat Messages", "AutoPistongChatMSG", true);
   private double CrystalDeltaBreak;
   private static boolean isSpoofingAngles;
   private static double yaw;
   private static double pitch;
   @EventHandler
   private final Listener<EventNetworkPacketEvent> packetSendListener;

   public WurstplusAutoPiston() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.CrystalDeltaBreak = (double)this.crystalDeltaBreak.get_value(1) * 0.1D;
      this.packetSendListener = new Listener((event) -> {
         Packet packet = event.getPacket();
         if (packet instanceof CPacketPlayer && isSpoofingAngles) {
            ((CPacketPlayer)packet).field_149476_e = (float)yaw;
            ((CPacketPlayer)packet).field_149473_f = (float)pitch;
         }

      }, new Predicate[0]);
      this.name = "Auto Piston";
      this.tag = "AutoPiston";
      this.description = "Automatically pushes crystals into holes";
   }

   protected void enable() {
      this.initValues();
      if (!this.getAimTarget()) {
         this.playerChecks();
      }
   }

   private boolean getAimTarget() {
      if (this.target.in("Nearest")) {
         this.aimTarget = findClosestTarget((double)this.range.get_value(1), this.aimTarget);
      } else {
         this.aimTarget = findLookingPlayer((double)this.range.get_value(1));
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
            this.meCoordsInt = new int[]{(int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70163_u, (int)mc.field_71439_g.field_70161_v};
            this.antiAutoDestruction();
            this.enoughSpace = this.createStructure();
         } else {
            this.isHole = false;
         }
      } else {
         this.noMaterials = true;
      }

   }

   private void antiAutoDestruction() {
      if (this.redstoneBlockMode || this.rotate.get_value(true)) {
         this.betterPlacement.set_value(false);
      }

   }

   private void initValues() {
      this.aimTarget = null;
      this.delayTable = new int[]{this.startDelay.get_value(1), this.supBlocksDelay.get_value(1), this.pistonDelay.get_value(1), this.crystalDelay.get_value(1), this.hitDelay.get_value(1)};
      this.toPlace = new WurstplusAutoPiston.structureTemp(0.0D, 0, (List)null);
      this.isHole = true;
      this.hasMoved = this.rotationPlayerMoved = this.deadPl = this.broken = this.brokenCrystalBug = this.brokenRedstoneTorch = this.yUnder = this.redstoneBlockMode = this.fastModeActive = false;
      this.slot_mat = new int[]{-1, -1, -1, -1, -1, -1};
      this.stage = this.delayTimeTicks = this.stuck = 0;
      if (mc.field_71439_g == null) {
         this.set_disable();
      } else {
         if (this.chatMsg.get_value(true)) {
            WurstplusMessageUtil.send_client_error_message("AutoPiston off");
         }

         this.oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
      }
   }

   protected void disable() {
      if (mc.field_71439_g != null) {
         if (this.chatMsg.get_value(true)) {
            String output = "";
            String materialsNeeded = "";
            if (this.aimTarget == null) {
               output = "No target found";
            } else if (this.yUnder) {
               output = String.format("you cannot be 2+ blocks under the enemy or %d above", this.maxYincr.get_value(1));
            } else if (this.noMaterials) {
               output = "No Materials Detected";
               materialsNeeded = this.getMissingMaterials();
            } else if (!this.isHole) {
               output = "The enemy is not in a hole";
            } else if (!this.enoughSpace) {
               output = "Not enough space";
            } else if (this.hasMoved) {
               output = "Out of range";
            } else if (this.deadPl) {
               output = "Enemy is dead";
            } else if (this.rotationPlayerMoved) {
               output = "You cannot move from your hole if you have rotation on";
            }

            if (!materialsNeeded.equals("")) {
               WurstplusMessageUtil.send_client_error_message("Materials missing:" + materialsNeeded);
            }

            WurstplusMessageUtil.send_client_error_message(output + "");
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

   public void update() {
      if (mc.field_71439_g == null) {
         this.set_disable();
      } else if (this.delayTimeTicks < this.delayTable[this.stage]) {
         ++this.delayTimeTicks;
      } else {
         this.delayTimeTicks = 0;
         if (this.enemyCoordsDouble != null && this.aimTarget != null) {
            if (this.aimTarget.field_70128_L) {
               this.deadPl = true;
            }

            if (this.rotate.get_value(true) && (int)mc.field_71439_g.field_70165_t != this.meCoordsInt[0] && (int)mc.field_71439_g.field_70161_v != this.meCoordsInt[2]) {
               this.rotationPlayerMoved = true;
            }

            if ((int)this.aimTarget.field_70165_t != (int)this.enemyCoordsDouble[0] || (int)this.aimTarget.field_70161_v != (int)this.enemyCoordsDouble[2]) {
               this.hasMoved = true;
            }

            if (!this.checkVariable()) {
               if (this.placeSupport()) {
                  switch(this.stage) {
                  case 1:
                     if (this.fastModeActive || this.breakRedstone()) {
                        if (this.fastModeActive && !this.checkCrystalPlace()) {
                           this.stage = 2;
                        } else {
                           this.placeBlockThings(this.stage);
                        }
                     }
                     break;
                  case 2:
                     if (this.fastModeActive || !this.confirmPlace.get_value(true) || this.checkPistonPlace()) {
                        this.placeBlockThings(this.stage);
                     }
                     break;
                  case 3:
                     if (this.fastModeActive || !this.confirmPlace.get_value(true) || this.checkCrystalPlace()) {
                        this.placeBlockThings(this.stage);
                        this.hitTryTick = 0;
                        if (this.fastModeActive && !this.checkPistonPlace()) {
                           this.stage = 1;
                        }
                     }
                     break;
                  case 4:
                     this.destroyCrystalAlgo();
                  }
               }

            }
         } else {
            if (this.aimTarget == null) {
               this.aimTarget = findLookingPlayer((double)this.range.get_value(1));
               if (this.aimTarget != null) {
                  this.playerChecks();
               }
            } else {
               this.checkVariable();
            }

         }
      }
   }

   public void destroyCrystalAlgo() {
      Entity crystal = null;
      Iterator var2 = mc.field_71441_e.field_72996_f.iterator();

      while(true) {
         Entity t;
         do {
            do {
               if (!var2.hasNext()) {
                  if (this.confirmBreak.get_value(true) && this.broken && crystal == null) {
                     this.stage = this.stuck = 0;
                     this.broken = false;
                  }

                  if (crystal != null) {
                     this.breakCrystalPiston(crystal);
                     if (this.confirmBreak.get_value(true)) {
                        this.broken = true;
                     } else {
                        this.stage = this.stuck = 0;
                     }
                  } else if (++this.stuck >= this.stuckDetector.get_value(1)) {
                     boolean found = false;
                     Iterator var7 = mc.field_71441_e.field_72996_f.iterator();

                     while(var7.hasNext()) {
                        Entity t = (Entity)var7.next();
                        if (t instanceof EntityEnderCrystal && (int)t.field_70165_t == (int)((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 1)).field_72450_a && (int)t.field_70161_v == (int)((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 1)).field_72449_c) {
                           found = true;
                           break;
                        }
                     }

                     if (!found) {
                        BlockPos offsetPosPist = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 2));
                        BlockPos pos = (new BlockPos(this.aimTarget.func_174791_d())).func_177982_a(offsetPosPist.func_177958_n(), offsetPosPist.func_177956_o(), offsetPosPist.func_177952_p());
                        if (this.confirmBreak.get_value(true) && this.brokenRedstoneTorch && this.get_block((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p()) instanceof BlockAir) {
                           this.stage = 1;
                           this.brokenRedstoneTorch = false;
                        } else {
                           EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
                           if (side != null) {
                              this.breakRedstone();
                              if (this.confirmBreak.get_value(true)) {
                                 this.brokenRedstoneTorch = true;
                              } else {
                                 this.stage = 1;
                              }

                              WurstplusMessageUtil.send_client_message("Stuck detected: crystal not placed");
                           }
                        }
                     } else {
                        boolean ext = false;
                        Iterator var11 = mc.field_71441_e.field_72996_f.iterator();

                        while(var11.hasNext()) {
                           Entity t = (Entity)var11.next();
                           if (t instanceof EntityEnderCrystal && (int)t.field_70165_t == (int)((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 1)).field_72450_a && (int)t.field_70161_v == (int)((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 1)).field_72449_c) {
                              ext = true;
                              break;
                           }
                        }

                        if (this.confirmBreak.get_value(true) && this.brokenCrystalBug && !ext) {
                           this.stage = this.stuck = 0;
                           this.brokenCrystalBug = false;
                        }

                        if (ext) {
                           this.breakCrystalPiston(crystal);
                           if (this.confirmBreak.get_value(true)) {
                              this.brokenCrystalBug = true;
                           } else {
                              this.stage = this.stuck = 0;
                           }

                           WurstplusMessageUtil.send_client_message("Stuck detected: crystal is stuck in the moving piston");
                        }
                     }
                  }

                  return;
               }

               t = (Entity)var2.next();
            } while(!(t instanceof EntityEnderCrystal));
         } while(((int)t.field_70165_t != this.enemyCoordsInt[0] || (int)(t.field_70161_v - this.CrystalDeltaBreak) != this.enemyCoordsInt[2] && (int)(t.field_70161_v + this.CrystalDeltaBreak) != this.enemyCoordsInt[2]) && ((int)t.field_70161_v != this.enemyCoordsInt[2] || (int)(t.field_70165_t - this.CrystalDeltaBreak) != this.enemyCoordsInt[0] && (int)(t.field_70165_t + this.CrystalDeltaBreak) != this.enemyCoordsInt[0]));

         crystal = t;
      }
   }

   private String getMissingMaterials() {
      StringBuilder output = new StringBuilder();
      if (this.slot_mat[0] == -1) {
         output.append(" Obsidian");
      }

      if (this.slot_mat[1] == -1) {
         output.append(" Piston");
      }

      if (this.slot_mat[2] == -1) {
         output.append(" Crystals");
      }

      if (this.slot_mat[3] == -1) {
         output.append(" Redstone");
      }

      if (this.antiWeakness.get_value(true) && this.slot_mat[4] == -1) {
         output.append(" Sword");
      }

      if (this.redstoneBlockMode && this.slot_mat[5] == -1) {
         output.append(" Pick");
      }

      return output.toString();
   }

   private void printTimeCrystals() {
      this.endTime = System.currentTimeMillis();
      WurstplusMessageUtil.send_client_message("3 crystal, time took: " + (this.endTime - this.startTime));
      this.nCrystal = 0;
      this.startTime = System.currentTimeMillis();
   }

   private void breakCrystalPiston(Entity crystal) {
      if (this.hitTryTick++ >= this.midHitDelay.get_value(1)) {
         this.hitTryTick = 0;
         if (this.antiWeakness.get_value(true)) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[4];
         }

         if (this.rotate.get_value(true)) {
            lookAtPacket(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, mc.field_71439_g);
         }

         if (this.breakType.in("Swing")) {
            breakCrystal(crystal);
         } else if (this.breakType.in("Packet")) {
            try {
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(crystal));
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            } catch (NullPointerException var3) {
            }
         }

         if (this.rotate.get_value(true)) {
            resetRotation();
         }

      }
   }

   private boolean breakRedstone() {
      BlockPos offsetPosPist = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 2));
      BlockPos pos = (new BlockPos(this.aimTarget.func_174791_d())).func_177982_a(offsetPosPist.func_177958_n(), offsetPosPist.func_177956_o(), offsetPosPist.func_177952_p());
      if (!(this.get_block((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p()) instanceof BlockAir)) {
         this.breakBlock(pos);
         return false;
      } else {
         return true;
      }
   }

   private void breakBlock(BlockPos pos) {
      if (this.redstoneBlockMode) {
         mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[5];
      }

      EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
      if (side != null) {
         if (this.rotate.get_value(true)) {
            BlockPos neighbour = pos.func_177972_a(side);
            EnumFacing opposite = side.func_176734_d();
            Vec3d hitVec = (new Vec3d(neighbour)).func_72441_c(0.5D, 1.0D, 0.5D).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
            WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
         }

         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
      }

   }

   private boolean checkPistonPlace() {
      BlockPos targetPosPist = this.compactBlockPos(1);
      if (!(this.get_block((double)targetPosPist.func_177958_n(), (double)targetPosPist.func_177956_o(), (double)targetPosPist.func_177952_p()) instanceof BlockPistonBase)) {
         --this.stage;
         return false;
      } else {
         return true;
      }
   }

   private boolean checkCrystalPlace() {
      Iterator var1 = mc.field_71441_e.field_72996_f.iterator();

      Entity t;
      do {
         if (!var1.hasNext()) {
            --this.stage;
            return false;
         }

         t = (Entity)var1.next();
      } while(!(t instanceof EntityEnderCrystal) || (int)t.field_70165_t != (int)(this.aimTarget.field_70165_t + ((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 1)).field_72450_a) || (int)t.field_70161_v != (int)(this.aimTarget.field_70161_v + ((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 1)).field_72449_c));

      return true;
   }

   private boolean placeSupport() {
      int checksDone = 0;
      int blockDone = 0;
      if (this.toPlace.supportBlock > 0) {
         do {
            BlockPos targetPos = this.getTargetPos(checksDone);
            if (this.placeBlock(targetPos, 0, 0.0D, 0.0D, 1.0D)) {
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

   private boolean placeBlock(BlockPos pos, int step, double offsetX, double offsetZ, double offsetY) {
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
            Vec3d hitVec = (new Vec3d(neighbour)).func_72441_c(0.5D + offsetX, offsetY, 0.5D + offsetZ).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
            Block neighbourBlock = mc.field_71441_e.func_180495_p(neighbour).func_177230_c();

            try {
               if (this.slot_mat[step] != 11 && mc.field_71439_g.field_71071_by.func_70301_a(this.slot_mat[step]) == ItemStack.field_190927_a) {
                  this.noMaterials = true;
                  return false;
               }

               if (mc.field_71439_g.field_71071_by.field_70461_c != this.slot_mat[step]) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.slot_mat[step] == 11 ? mc.field_71439_g.field_71071_by.field_70461_c : this.slot_mat[step];
               }
            } catch (Exception var22) {
               WurstplusMessageUtil.send_client_message("Fatal Error during the creation of the structure. Please, report this bug in the gamesense disc server");
               Logger LOGGER = LogManager.getLogger("ListedHack");
               LOGGER.error("[Elevator] error during the creation of the structure.");
               if (var22.getMessage() != null) {
                  LOGGER.error("[Elevator] error message: " + var22.getClass().getName() + " " + var22.getMessage());
               } else {
                  LOGGER.error("[Elevator] cannot find the cause");
               }

               int i5 = false;
               if (var22.getStackTrace().length != 0) {
                  LOGGER.error("[Elevator] StackTrace Start");
                  StackTraceElement[] var18 = var22.getStackTrace();
                  int var19 = var18.length;

                  for(int var20 = 0; var20 < var19; ++var20) {
                     StackTraceElement errorMess = var18[var20];
                     LOGGER.error("[Elevator] " + errorMess.toString());
                  }

                  LOGGER.error("[Elevator] StackTrace End");
               }

               WurstplusMessageUtil.send_client_message(Integer.toString(step));
               this.set_disable();
            }

            if (!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
               this.isSneaking = true;
            }

            if (this.rotate.get_value(true) || step == 1) {
               Vec3d positionHit = hitVec;
               if (!this.rotate.get_value(true) && step == 1) {
                  positionHit = new Vec3d(mc.field_71439_g.field_70165_t + offsetX, mc.field_71439_g.field_70163_u + (offsetY == -1.0D ? offsetY : 0.0D), mc.field_71439_g.field_70161_v + offsetZ);
               }

               WurstplusBlockInteractHelper.faceVectorPacketInstant(positionHit);
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

   public void placeBlockThings(int step) {
      BlockPos targetPos = this.compactBlockPos(step);
      this.placeBlock(targetPos, step, (double)this.toPlace.offsetX, (double)this.toPlace.offsetZ, (double)this.toPlace.offsetY);
      ++this.stage;
   }

   public BlockPos compactBlockPos(int step) {
      BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + step - 1));
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
      WurstplusAutoPiston.structureTemp addedStructure = new WurstplusAutoPiston.structureTemp(Double.MAX_VALUE, 0, (List)null);

      int var8;
      int var9;
      try {
         if (this.meCoordsInt[1] - this.enemyCoordsInt[1] > -1 && this.meCoordsInt[1] - this.enemyCoordsInt[1] <= this.maxYincr.get_value(1)) {
            for(int startH = 1; startH >= 0; --startH) {
               if (addedStructure.to_place == null) {
                  int incr = 0;
                  ArrayList highSup = new ArrayList();

                  while(this.meCoordsInt[1] > this.enemyCoordsInt[1] + incr) {
                     ++incr;
                     int[][] var39 = this.disp_surblock;
                     var8 = var39.length;

                     for(var9 = 0; var9 < var8; ++var9) {
                        int[] cordSupport = var39[var9];
                        highSup.add(new Vec3d((double)cordSupport[0], (double)incr, (double)cordSupport[2]));
                     }
                  }

                  incr += startH;
                  int i = -1;
                  Double[][] var41 = this.sur_block;
                  var9 = var41.length;

                  for(int var44 = 0; var44 < var9; ++var44) {
                     Double[] cord_b = var41[var44];
                     ++i;
                     double[] crystalCordsAbs = new double[]{cord_b[0], cord_b[1] + (double)incr, cord_b[2]};
                     int[] crystalCordsRel = new int[]{this.disp_surblock[i][0], this.disp_surblock[i][1] + incr, this.disp_surblock[i][2]};
                     double distanceNowCrystal;
                     if ((distanceNowCrystal = mc.field_71439_g.func_70011_f(crystalCordsAbs[0], crystalCordsAbs[1], crystalCordsAbs[2])) < addedStructure.distance && this.get_block(crystalCordsAbs[0], crystalCordsAbs[1], crystalCordsAbs[2]) instanceof BlockAir && this.get_block(crystalCordsAbs[0], crystalCordsAbs[1] + 1.0D, crystalCordsAbs[2]) instanceof BlockAir && !someoneInCoords(crystalCordsAbs[0], crystalCordsAbs[2])) {
                        double[] pistonCordAbs = new double[3];
                        int[] pistonCordRel = new int[3];
                        double minFound;
                        int checkBehind;
                        int idx;
                        if (!this.rotate.get_value(true) && this.betterPlacement.get_value(true)) {
                           double distancePist = Double.MAX_VALUE;
                           int[][] var21 = this.disp_surblock;
                           checkBehind = var21.length;

                           for(idx = 0; idx < checkBehind; ++idx) {
                              int[] disp = var21[idx];
                              BlockPos blockPiston = new BlockPos(crystalCordsAbs[0] + (double)disp[0], crystalCordsAbs[1], crystalCordsAbs[2] + (double)disp[2]);
                              if (!((minFound = mc.field_71439_g.func_174831_c(blockPiston)) > distancePist) && (this.get_block((double)blockPiston.func_177958_n(), (double)blockPiston.func_177956_o(), (double)blockPiston.func_177952_p()) instanceof BlockPistonBase || this.get_block((double)blockPiston.func_177958_n(), (double)blockPiston.func_177956_o(), (double)blockPiston.func_177952_p()) instanceof BlockAir) && !someoneInCoords(crystalCordsAbs[0] + (double)disp[0], crystalCordsAbs[2] + (double)disp[2]) && this.get_block((double)(blockPiston.func_177958_n() - crystalCordsRel[0]), (double)blockPiston.func_177956_o(), (double)(blockPiston.func_177952_p() - crystalCordsRel[2])) instanceof BlockAir) {
                                 distancePist = minFound;
                                 pistonCordAbs = new double[]{crystalCordsAbs[0] + (double)disp[0], crystalCordsAbs[1], crystalCordsAbs[2] + (double)disp[2]};
                                 pistonCordRel = new int[]{crystalCordsRel[0] + disp[0], crystalCordsRel[1], crystalCordsRel[2] + disp[2]};
                              }
                           }

                           if (distancePist == Double.MAX_VALUE) {
                              continue;
                           }
                        } else {
                           pistonCordAbs = new double[]{crystalCordsAbs[0] + (double)this.disp_surblock[i][0], crystalCordsAbs[1], crystalCordsAbs[2] + (double)this.disp_surblock[i][2]};
                           Block tempBlock;
                           if ((tempBlock = this.get_block(pistonCordAbs[0], pistonCordAbs[1], pistonCordAbs[2])) instanceof BlockPistonBase == (tempBlock instanceof BlockAir) || someoneInCoords(pistonCordAbs[0], pistonCordAbs[2])) {
                              continue;
                           }

                           pistonCordRel = new int[]{crystalCordsRel[0] * 2, crystalCordsRel[1], crystalCordsRel[2] * 2};
                        }

                        if (this.rotate.get_value(true)) {
                           int[] pistonCordInt = new int[]{(int)pistonCordAbs[0], (int)pistonCordAbs[1], (int)pistonCordAbs[2]};
                           boolean behindBol = false;
                           int[] var47 = new int[]{0, 2};
                           int var20 = var47.length;

                           for(int var49 = 0; var49 < var20; ++var49) {
                              checkBehind = var47[var49];
                              if (this.meCoordsInt[checkBehind] == pistonCordInt[checkBehind]) {
                                 idx = checkBehind == 2 ? 0 : 2;
                                 if (pistonCordInt[idx] >= this.enemyCoordsInt[idx] == this.meCoordsInt[idx] >= this.enemyCoordsInt[idx]) {
                                    behindBol = true;
                                 }
                              }
                           }

                           if (!behindBol && Math.abs(this.meCoordsInt[0] - this.enemyCoordsInt[0]) == 2 && Math.abs(this.meCoordsInt[2] - this.enemyCoordsInt[2]) == 2 && (this.meCoordsInt[0] == pistonCordInt[0] && Math.abs(this.meCoordsInt[2] - pistonCordInt[2]) >= 2 || this.meCoordsInt[2] == pistonCordInt[2] && Math.abs(this.meCoordsInt[0] - pistonCordInt[0]) >= 2)) {
                              behindBol = true;
                           }

                           if (!behindBol && Math.abs(this.meCoordsInt[0] - this.enemyCoordsInt[0]) > 2 && this.meCoordsInt[2] != this.enemyCoordsInt[2] || Math.abs(this.meCoordsInt[2] - this.enemyCoordsInt[2]) > 2 && this.meCoordsInt[0] != this.enemyCoordsInt[0]) {
                              behindBol = true;
                           }

                           if (behindBol) {
                              continue;
                           }
                        }

                        double[] redstoneCoordsAbs = new double[3];
                        int[] redstoneCoordsRel = new int[3];
                        minFound = Double.MAX_VALUE;
                        double minNow = -1.0D;
                        boolean foundOne = true;
                        int[][] var52 = this.disp_surblock;
                        int supportBlock = var52.length;

                        int i2;
                        int[] valueBegin;
                        for(i2 = 0; i2 < supportBlock; ++i2) {
                           int[] pos = var52[i2];
                           double[] torchCoords = new double[]{pistonCordAbs[0] + (double)pos[0], pistonCordAbs[1], pistonCordAbs[2] + (double)pos[2]};
                           if (!((minNow = mc.field_71439_g.func_70011_f(torchCoords[0], torchCoords[1], torchCoords[2])) >= minFound) && (!this.redstoneBlockMode || pos[0] == crystalCordsRel[0]) && !someoneInCoords(torchCoords[0], torchCoords[2]) && (this.get_block(torchCoords[0], torchCoords[1], torchCoords[2]) instanceof BlockRedstoneTorch || this.get_block(torchCoords[0], torchCoords[1], torchCoords[2]) instanceof BlockAir) && ((int)torchCoords[0] != (int)crystalCordsAbs[0] || (int)torchCoords[2] != (int)crystalCordsAbs[2])) {
                              boolean torchFront = false;
                              valueBegin = new int[]{0, 2};
                              int var31 = valueBegin.length;

                              for(int var32 = 0; var32 < var31; ++var32) {
                                 int part = valueBegin[var32];
                                 int contPart = part == 0 ? 2 : 0;
                                 if ((int)torchCoords[contPart] == (int)pistonCordAbs[contPart] && (int)torchCoords[part] == this.enemyCoordsInt[part]) {
                                    torchFront = true;
                                 }
                              }

                              if (!torchFront) {
                                 redstoneCoordsAbs = new double[]{torchCoords[0], torchCoords[1], torchCoords[2]};
                                 redstoneCoordsRel = new int[]{pistonCordRel[0] + pos[0], pistonCordRel[1], pistonCordRel[2] + pos[2]};
                                 foundOne = false;
                                 minFound = minNow;
                              }
                           }
                        }

                        if (!foundOne) {
                           if (this.redstoneBlockMode && this.allowCheapMode.get_value(true) && this.get_block(redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0D, redstoneCoordsAbs[2]) instanceof BlockAir) {
                              pistonCordAbs = new double[]{redstoneCoordsAbs[0], redstoneCoordsAbs[1], redstoneCoordsAbs[2]};
                              pistonCordRel = new int[]{redstoneCoordsRel[0], redstoneCoordsRel[1], redstoneCoordsRel[2]};
                              redstoneCoordsAbs = new double[]{redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0D, (double)redstoneCoordsRel[2]};
                              redstoneCoordsRel = new int[]{redstoneCoordsRel[0], redstoneCoordsRel[1] - 1, redstoneCoordsRel[2]};
                              this.fastModeActive = true;
                           }

                           List<Vec3d> toPlaceTemp = new ArrayList();
                           supportBlock = 0;
                           if (this.get_block(crystalCordsAbs[0], crystalCordsAbs[1] - 1.0D, crystalCordsAbs[2]) instanceof BlockAir) {
                              toPlaceTemp.add(new Vec3d((double)crystalCordsRel[0], (double)(crystalCordsRel[1] - 1), (double)crystalCordsRel[2]));
                              ++supportBlock;
                           }

                           if (!this.fastModeActive && this.get_block(pistonCordAbs[0], pistonCordAbs[1] - 1.0D, pistonCordAbs[2]) instanceof BlockAir) {
                              toPlaceTemp.add(new Vec3d((double)pistonCordRel[0], (double)(pistonCordRel[1] - 1), (double)pistonCordRel[2]));
                              ++supportBlock;
                           }

                           if (!this.fastModeActive) {
                              if (!this.redstoneBlockMode && this.get_block(redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0D, redstoneCoordsAbs[2]) instanceof BlockAir) {
                                 toPlaceTemp.add(new Vec3d((double)redstoneCoordsRel[0], (double)(redstoneCoordsRel[1] - 1), (double)redstoneCoordsRel[2]));
                                 ++supportBlock;
                              }
                           } else if (this.get_block(redstoneCoordsAbs[0] - (double)crystalCordsRel[0], redstoneCoordsAbs[1] - 1.0D, redstoneCoordsAbs[2] - (double)crystalCordsRel[2]) instanceof BlockAir) {
                              toPlaceTemp.add(new Vec3d((double)(redstoneCoordsRel[0] - crystalCordsRel[0]), (double)redstoneCoordsRel[1], (double)(redstoneCoordsRel[2] - crystalCordsRel[2])));
                              ++supportBlock;
                           }

                           toPlaceTemp.add(new Vec3d((double)pistonCordRel[0], (double)pistonCordRel[1], (double)pistonCordRel[2]));
                           toPlaceTemp.add(new Vec3d((double)crystalCordsRel[0], (double)crystalCordsRel[1], (double)crystalCordsRel[2]));
                           toPlaceTemp.add(new Vec3d((double)redstoneCoordsRel[0], (double)redstoneCoordsRel[1], (double)redstoneCoordsRel[2]));
                           if (incr > 1) {
                              for(i2 = 0; i2 < highSup.size(); ++i2) {
                                 toPlaceTemp.add(0, highSup.get(i2));
                                 ++supportBlock;
                              }
                           }

                           float offsetZ;
                           float offsetX;
                           if (this.disp_surblock[i][0] != 0) {
                              offsetX = this.rotate.get_value(true) ? (float)this.disp_surblock[i][0] / 2.0F : (float)this.disp_surblock[i][0];
                              if (this.rotate.get_value(true)) {
                                 if (mc.field_71439_g.func_70092_e(pistonCordAbs[0], pistonCordAbs[1], pistonCordAbs[2] + 0.5D) > mc.field_71439_g.func_70092_e(pistonCordAbs[0], pistonCordAbs[1], pistonCordAbs[2] - 0.5D)) {
                                    offsetZ = -0.5F;
                                 } else {
                                    offsetZ = 0.5F;
                                 }
                              } else {
                                 offsetZ = (float)this.disp_surblock[i][2];
                              }
                           } else {
                              offsetZ = this.rotate.get_value(true) ? (float)this.disp_surblock[i][2] / 2.0F : (float)this.disp_surblock[i][2];
                              if (this.rotate.get_value(true)) {
                                 if (mc.field_71439_g.func_70092_e(pistonCordAbs[0] + 0.5D, pistonCordAbs[1], pistonCordAbs[2]) > mc.field_71439_g.func_70092_e(pistonCordAbs[0] - 0.5D, pistonCordAbs[1], pistonCordAbs[2])) {
                                    offsetX = -0.5F;
                                 } else {
                                    offsetX = 0.5F;
                                 }
                              } else {
                                 offsetX = (float)this.disp_surblock[i][0];
                              }
                           }

                           float offsetY = this.meCoordsInt[1] - this.enemyCoordsInt[1] == -1 ? 0.0F : 1.0F;
                           addedStructure.replaceValues(distanceNowCrystal, supportBlock, toPlaceTemp, -1, offsetX, offsetZ, offsetY);
                           if (this.blockPlayer.get_value(true)) {
                              Vec3d valuesStart = (Vec3d)addedStructure.to_place.get(addedStructure.supportBlock + 1);
                              valueBegin = new int[]{(int)(-valuesStart.field_72450_a), (int)valuesStart.field_72448_b, (int)(-valuesStart.field_72449_c)};
                              if (this.bypassObsidian.get_value(true) && (int)mc.field_71439_g.field_70163_u != this.enemyCoordsInt[1]) {
                                 addedStructure.to_place.add(0, new Vec3d(0.0D, (double)incr, 0.0D));
                                 addedStructure.to_place.add(0, new Vec3d((double)valueBegin[0], (double)incr, (double)valueBegin[2]));
                                 addedStructure.supportBlock += 2;
                              } else {
                                 addedStructure.to_place.add(0, new Vec3d(0.0D, (double)(incr + 1), 0.0D));
                                 addedStructure.to_place.add(0, new Vec3d((double)valueBegin[0], (double)(incr + 1), (double)valueBegin[2]));
                                 addedStructure.to_place.add(0, new Vec3d((double)valueBegin[0], (double)incr, (double)valueBegin[2]));
                                 addedStructure.supportBlock += 3;
                              }
                           }

                           this.toPlace = addedStructure;
                        }
                     }
                  }
               }
            }
         } else {
            this.yUnder = true;
         }
      } catch (Exception var35) {
         WurstplusMessageUtil.send_client_message("Fatal Error during the creation of the structure. Please, report this bug in the GameSense discord server");
         Logger LOGGER = LogManager.getLogger("ListedHack");
         LOGGER.error("[AutoPiston] error during the creation of the structure.");
         if (var35.getMessage() != null) {
            LOGGER.error("[AutoPiston] error message: " + var35.getClass().getName() + " " + var35.getMessage());
         } else {
            LOGGER.error("[AutoPiston] cannot find the cause");
         }

         int i5 = 0;
         if (var35.getStackTrace().length != 0) {
            LOGGER.error("[AutoPiston] StackTrace Start");
            StackTraceElement[] var7 = var35.getStackTrace();
            var8 = var7.length;

            for(var9 = 0; var9 < var8; ++var9) {
               StackTraceElement errorMess = var7[var9];
               LOGGER.error("[AutoPiston] " + errorMess.toString());
            }

            LOGGER.error("[AutoPiston] StackTrace End");
         }

         if (this.aimTarget != null) {
            LOGGER.error("[AutoPiston] closest target is not null");
         } else {
            LOGGER.error("[AutoPiston] closest target is null somehow");
         }

         Double[][] var38 = this.sur_block;
         var8 = var38.length;

         for(var9 = 0; var9 < var8; ++var9) {
            Double[] cord_b = var38[var9];
            if (cord_b != null) {
               LOGGER.error("[AutoPiston] " + i5 + " is not null");
            } else {
               LOGGER.error("[AutoPiston] " + i5 + " is null");
            }

            ++i5;
         }
      }

      return addedStructure.to_place != null;
   }

   public static boolean someoneInCoords(double x, double z) {
      int xCheck = (int)x;
      int zCheck = (int)z;
      List<EntityPlayer> playerList = mc.field_71441_e.field_73010_i;
      Iterator var7 = playerList.iterator();

      EntityPlayer player;
      do {
         if (!var7.hasNext()) {
            return false;
         }

         player = (EntityPlayer)var7.next();
      } while((int)player.field_70165_t != xCheck || (int)player.field_70161_v != zCheck);

      return true;
   }

   private boolean getMaterialsSlot() {
      if (mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemEndCrystal) {
         this.slot_mat[2] = 11;
      }

      if (this.placeMode.in("Block")) {
         this.redstoneBlockMode = true;
      }

      int i;
      for(i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a) {
            if (this.slot_mat[2] == -1 && stack.func_77973_b() instanceof ItemEndCrystal) {
               this.slot_mat[2] = i;
            } else if (this.antiWeakness.get_value(true) && stack.func_77973_b() instanceof ItemSword) {
               this.slot_mat[4] = i;
            } else if (stack.func_77973_b() instanceof ItemPickaxe) {
               this.slot_mat[5] = i;
            }

            if (stack.func_77973_b() instanceof ItemBlock) {
               Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
               if (block instanceof BlockObsidian) {
                  this.slot_mat[0] = i;
               } else if (block instanceof BlockPistonBase) {
                  this.slot_mat[1] = i;
               } else if (!this.placeMode.in("Block") && block instanceof BlockRedstoneTorch) {
                  this.slot_mat[3] = i;
                  this.redstoneBlockMode = false;
               } else if (!this.placeMode.in("Torch") && block.field_149770_b.equals("blockRedstone")) {
                  this.slot_mat[3] = i;
                  this.redstoneBlockMode = true;
               }
            }
         }
      }

      if (!this.redstoneBlockMode) {
         this.slot_mat[5] = -1;
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

      return i >= 4 + (this.antiWeakness.get_value(true) ? 1 : 0) + (this.redstoneBlockMode ? 1 : 0);
   }

   private boolean is_in_hole() {
      this.sur_block = new Double[][]{{this.aimTarget.field_70165_t + 1.0D, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v}, {this.aimTarget.field_70165_t - 1.0D, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v}, {this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v + 1.0D}, {this.aimTarget.field_70165_t, this.aimTarget.field_70163_u, this.aimTarget.field_70161_v - 1.0D}};
      return !(this.get_block(this.sur_block[0][0], this.sur_block[0][1], this.sur_block[0][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[1][0], this.sur_block[1][1], this.sur_block[1][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[2][0], this.sur_block[2][1], this.sur_block[2][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[3][0], this.sur_block[3][1], this.sur_block[3][2]) instanceof BlockAir);
   }

   private Block get_block(double x, double y, double z) {
      return mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
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

   public static boolean basicChecksEntity(Entity pl) {
      return pl == mc.field_71439_g || WurstplusFriendUtil.isFriend(pl.func_70005_c_()) || pl.field_70128_L;
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

   public static void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
      double[] v = calculateLookAt(px, py, pz, me);
      setYawAndPitch((float)v[0], (float)v[1]);
   }

   public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
      double dirx = me.field_70165_t - px;
      double diry = me.field_70163_u - py;
      double dirz = me.field_70161_v - pz;
      double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
      dirx /= len;
      diry /= len;
      dirz /= len;
      double pitch = Math.asin(diry);
      double yaw = Math.atan2(dirz, dirx);
      pitch = pitch * 180.0D / 3.141592653589793D;
      yaw = yaw * 180.0D / 3.141592653589793D;
      yaw += 90.0D;
      return new double[]{yaw, pitch};
   }

   public static void setYawAndPitch(float yaw1, float pitch1) {
      yaw = (double)yaw1;
      pitch = (double)pitch1;
      isSpoofingAngles = true;
   }

   public static void breakCrystal(Entity crystal) {
      try {
         mc.field_71442_b.func_78764_a(mc.field_71439_g, crystal);
         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
      } catch (NullPointerException var2) {
      }

   }

   public static void resetRotation() {
      if (isSpoofingAngles) {
         yaw = (double)mc.field_71439_g.field_70177_z;
         pitch = (double)mc.field_71439_g.field_70125_A;
         isSpoofingAngles = false;
      }

   }

   private static class structureTemp {
      public double distance;
      public int supportBlock;
      public List<Vec3d> to_place;
      public int direction;
      public float offsetX;
      public float offsetY;
      public float offsetZ;

      public structureTemp(double distance, int supportBlock, List<Vec3d> to_place) {
         this.distance = distance;
         this.supportBlock = supportBlock;
         this.to_place = to_place;
         this.direction = -1;
      }

      public void replaceValues(double distance, int supportBlock, List<Vec3d> to_place, int direction, float offsetX, float offsetZ, float offsetY) {
         this.distance = distance;
         this.supportBlock = supportBlock;
         this.to_place = to_place;
         this.direction = direction;
         this.offsetX = offsetX;
         this.offsetZ = offsetZ;
         this.offsetY = offsetY;
      }
   }
}
