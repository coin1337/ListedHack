package me.listed.listedhack.client.hacks.movement;

import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.listedhack.client.util.WurstplusTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

public class WurstplusVClip extends WurstplusHack {
   WurstplusSetting offset = this.create("Strength", "Strength", -5.599999904632568D, -20.0D, 20.0D);
   WurstplusSetting delay = this.create("Delay", "Delay", 100, 0, 1000);
   WurstplusTimer timer = new WurstplusTimer();
   int lastHotbarSlot;
   int playerHotbarSlot;
   boolean isSneaking;

   public WurstplusVClip() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "VClip";
      this.tag = "VClip";
      this.description = "tps you to y 0";
   }

   protected void enable() {
      if (mc.field_71439_g != null) {
         if (mc.func_71356_B()) {
            WurstplusMessageUtil.send_client_error_message("You are in singleplayer");
            this.set_disable();
         } else {
            BlockPos pos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
            if (this.intersectsWithEntity(pos)) {
               WurstplusMessageUtil.send_client_error_message("Intercepted by entity");
            }

            this.playerHotbarSlot = mc.field_71439_g.field_71071_by.field_70461_c;
            this.lastHotbarSlot = -1;
            mc.field_71439_g.func_70664_aZ();
            this.timer.reset();
         }
      }
   }

   protected void disable() {
      if (mc.field_71439_g != null) {
         if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
         }

         if (this.isSneaking) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
            this.isSneaking = false;
         }

         this.playerHotbarSlot = -1;
         this.lastHotbarSlot = -1;
      }
   }

   public void update() {
      if (this.timer.passed((long)this.delay.get_value(1))) {
      }

      if (this.isSneaking) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
         this.isSneaking = false;
      }

      ListedHack.get_hack_manager().get_module_with_tag("NoVoid").set_active(true);
      mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)this.offset.get_value(1), mc.field_71439_g.field_70161_v, false));
      mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.41999998688698D, mc.field_71439_g.field_70161_v, true));
      mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.7531999805211997D, mc.field_71439_g.field_70161_v, true));
      mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.00133597911214D, mc.field_71439_g.field_70161_v, true));
      mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.16610926093821D, mc.field_71439_g.field_70161_v, true));
      BlockPos offsetPos = new BlockPos(0, -1, 0);
      BlockPos targetPos = (new BlockPos(mc.field_71439_g.func_174791_d())).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p());
      if (this.placeBlock(targetPos)) {
         if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
            this.lastHotbarSlot = this.playerHotbarSlot;
         }

         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)this.offset.get_value(1), mc.field_71439_g.field_70161_v, false));
      }

      this.set_disable();
   }

   private boolean placeBlock(BlockPos pos) {
      Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
      if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
         return false;
      } else {
         EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
         if (side == null) {
            return false;
         } else {
            BlockPos neighbour = pos.func_177972_a(side);
            EnumFacing opposite = side.func_176734_d();
            if (!WurstplusBlockInteractHelper.canBeClicked(neighbour)) {
               return false;
            } else {
               Vec3d hitVec = (new Vec3d(neighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
               Block neighbourBlock = mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
               int obiSlot = this.find_in_hotbar();
               if (obiSlot == -1) {
                  this.set_disable();
               }

               if (this.lastHotbarSlot != obiSlot) {
                  mc.field_71439_g.field_71071_by.field_70461_c = obiSlot;
                  this.lastHotbarSlot = obiSlot;
               }

               if (!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                  this.isSneaking = true;
               }

               mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
               mc.field_71467_ac = 4;
               if (mc.field_71442_b.func_178889_l().equals(GameType.CREATIVE)) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
               }

               return true;
            }
         }
      }
   }

   private int find_in_hotbar() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block instanceof BlockEnderChest) {
               return i;
            }

            if (block instanceof BlockObsidian) {
               return i;
            }
         }
      }

      return -1;
   }

   private boolean intersectsWithEntity(BlockPos pos) {
      Iterator var2 = mc.field_71441_e.field_72996_f.iterator();

      Entity entity;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         entity = (Entity)var2.next();
      } while(entity.equals(mc.field_71439_g) || entity instanceof EntityItem || entity instanceof EntityXPOrb || !(new AxisAlignedBB(pos)).func_72326_a(entity.func_174813_aQ()));

      return true;
   }
}
