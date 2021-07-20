package me.listed.listedhack.client.hacks.other;

import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusAutoNomadHut extends WurstplusHack {
   WurstplusSetting rotate = this.create("Rotate", "NomadSmoth", true);
   WurstplusSetting triggerable = this.create("Toggle", "NomadToggle", true);
   WurstplusSetting tick_for_place = this.create("Blocks per tick", "NomadTickToPlace", 2, 1, 8);
   Vec3d[] targets = new Vec3d[]{new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 1.0D), new Vec3d(2.0D, 0.0D, -1.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, 1.0D), new Vec3d(-2.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(1.0D, 0.0D, 2.0D), new Vec3d(-1.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(-1.0D, 0.0D, -2.0D), new Vec3d(1.0D, 0.0D, -2.0D), new Vec3d(2.0D, 1.0D, -1.0D), new Vec3d(2.0D, 1.0D, 1.0D), new Vec3d(-2.0D, 1.0D, 0.0D), new Vec3d(-2.0D, 1.0D, 1.0D), new Vec3d(-2.0D, 1.0D, -1.0D), new Vec3d(0.0D, 1.0D, 2.0D), new Vec3d(1.0D, 1.0D, 2.0D), new Vec3d(-1.0D, 1.0D, 2.0D), new Vec3d(0.0D, 1.0D, -2.0D), new Vec3d(1.0D, 1.0D, -2.0D), new Vec3d(-1.0D, 1.0D, -2.0D), new Vec3d(2.0D, 2.0D, -1.0D), new Vec3d(2.0D, 2.0D, 1.0D), new Vec3d(-2.0D, 2.0D, 1.0D), new Vec3d(-2.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 2.0D), new Vec3d(-1.0D, 2.0D, 2.0D), new Vec3d(1.0D, 2.0D, -2.0D), new Vec3d(-1.0D, 2.0D, -2.0D), new Vec3d(2.0D, 3.0D, 0.0D), new Vec3d(2.0D, 3.0D, -1.0D), new Vec3d(2.0D, 3.0D, 1.0D), new Vec3d(-2.0D, 3.0D, 0.0D), new Vec3d(-2.0D, 3.0D, 1.0D), new Vec3d(-2.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 2.0D), new Vec3d(1.0D, 3.0D, 2.0D), new Vec3d(-1.0D, 3.0D, 2.0D), new Vec3d(0.0D, 3.0D, -2.0D), new Vec3d(1.0D, 3.0D, -2.0D), new Vec3d(-1.0D, 3.0D, -2.0D), new Vec3d(0.0D, 4.0D, 0.0D), new Vec3d(1.0D, 4.0D, 0.0D), new Vec3d(-1.0D, 4.0D, 0.0D), new Vec3d(0.0D, 4.0D, 1.0D), new Vec3d(0.0D, 4.0D, -1.0D), new Vec3d(1.0D, 4.0D, 1.0D), new Vec3d(-1.0D, 4.0D, 1.0D), new Vec3d(-1.0D, 4.0D, -1.0D), new Vec3d(1.0D, 4.0D, -1.0D), new Vec3d(2.0D, 4.0D, 0.0D), new Vec3d(2.0D, 4.0D, 1.0D), new Vec3d(2.0D, 4.0D, -1.0D)};
   int new_slot = 0;
   int old_slot = 0;
   int y_level = 0;
   int tick_runs = 0;
   int blocks_placed = 0;
   int offset_step = 0;
   boolean sneak = false;

   public WurstplusAutoNomadHut() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto FitMC";
      this.tag = "Auto FitMC";
      this.description = "i fucking hate fit";
   }

   public void enable() {
      if (mc.field_71439_g != null) {
         this.old_slot = mc.field_71439_g.field_71071_by.field_70461_c;
         this.new_slot = this.find_in_hotbar();
         if (this.new_slot == -1) {
            WurstplusMessageUtil.send_client_error_message("cannot find obi in hotbar");
            this.set_active(false);
         }

         this.y_level = (int)Math.round(mc.field_71439_g.field_70163_u);
      }

   }

   public void disable() {
      if (mc.field_71439_g != null) {
         if (this.new_slot != this.old_slot && this.old_slot != -1) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.old_slot;
         }

         if (this.sneak) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
            this.sneak = false;
         }

         this.old_slot = -1;
         this.new_slot = -1;
      }

   }

   public void update() {
      if (mc.field_71439_g != null) {
         for(this.blocks_placed = 0; this.blocks_placed < this.tick_for_place.get_value(1); ++this.offset_step) {
            if (this.offset_step >= this.targets.length) {
               this.offset_step = 0;
               break;
            }

            BlockPos offsetPos = new BlockPos(this.targets[this.offset_step]);
            BlockPos targetPos = (new BlockPos(mc.field_71439_g.func_174791_d())).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p()).func_177977_b();
            boolean try_to_place = true;
            if (!mc.field_71441_e.func_180495_p(targetPos).func_185904_a().func_76222_j()) {
               try_to_place = false;
            }

            Iterator var4 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(targetPos)).iterator();

            while(var4.hasNext()) {
               Entity entity = (Entity)var4.next();
               if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                  try_to_place = false;
                  break;
               }
            }

            if (try_to_place && this.place_blocks(targetPos)) {
               ++this.blocks_placed;
            }
         }

         if (this.blocks_placed > 0 && this.new_slot != this.old_slot) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.old_slot;
         }

         ++this.tick_runs;
      }

   }

   private boolean place_blocks(BlockPos pos) {
      if (!mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
         return false;
      } else if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
         return false;
      } else {
         EnumFacing[] var2 = EnumFacing.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EnumFacing side = var2[var4];
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.func_176734_d();
            if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
               mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
               if (WurstplusBlockInteractHelper.blackList.contains(mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                  this.sneak = true;
               }

               Vec3d hitVec = (new Vec3d(neighbor)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(side2.func_176730_m())).func_186678_a(0.5D));
               if (this.rotate.get_value(true)) {
                  WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
               }

               mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
               return true;
            }
         }

         return false;
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
}
