package me.listed.listedhack.client.hacks.combat;

import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSurround extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "SurroundMode", "Normal", this.combobox(new String[]{"Normal", "Face", "Anticity"}));
   WurstplusSetting rotate = this.create("Rotate", "SurroundSmoth", true);
   WurstplusSetting hybrid = this.create("Hybrid", "SurroundHybrid", true);
   WurstplusSetting triggerable = this.create("Toggle", "SurroundToggle", true);
   WurstplusSetting center = this.create("Center", "SurroundCenter", true);
   WurstplusSetting tick_for_place = this.create("Blocks per tick", "SurroundTickToPlace", 2, 1, 8);
   WurstplusSetting tick_timeout = this.create("Ticks til timeout", "SurroundTicks", 20, 10, 50);
   WurstplusSetting swing = this.create("Swing", "SurroundSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
   private int y_level = 0;
   private int tick_runs = 0;
   private int offset_step = 0;
   private Vec3d center_block;
   Vec3d[] surround_targets;
   Vec3d[] surround_targets_city;
   Vec3d[] surround_targets_face;

   public WurstplusSurround() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.center_block = Vec3d.field_186680_a;
      this.surround_targets = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D)};
      this.surround_targets_city = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, -1.0D)};
      this.surround_targets_face = new Vec3d[]{new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D)};
      this.name = "Surround";
      this.tag = "Surround";
      this.description = "surround urself with obi and such";
   }

   public void enable() {
      if (this.find_in_hotbar() == -1) {
         WurstplusMessageUtil.send_client_error_message("No obby");
         this.set_disable();
      } else {
         if (mc.field_71439_g != null) {
            this.y_level = (int)Math.round(mc.field_71439_g.field_70163_u);
            this.center_block = this.get_center(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
            if (this.center.get_value(true)) {
               mc.field_71439_g.field_70159_w = 0.0D;
               mc.field_71439_g.field_70179_y = 0.0D;
            }
         }

      }
   }

   public void update() {
      if (mc.field_71439_g != null) {
         if (this.center_block != Vec3d.field_186680_a && this.center.get_value(true)) {
            double x_diff = Math.abs(this.center_block.field_72450_a - mc.field_71439_g.field_70165_t);
            double z_diff = Math.abs(this.center_block.field_72449_c - mc.field_71439_g.field_70161_v);
            if (x_diff <= 0.1D && z_diff <= 0.1D) {
               this.center_block = Vec3d.field_186680_a;
            } else {
               double motion_x = this.center_block.field_72450_a - mc.field_71439_g.field_70165_t;
               double motion_z = this.center_block.field_72449_c - mc.field_71439_g.field_70161_v;
               mc.field_71439_g.field_70159_w = motion_x / 2.0D;
               mc.field_71439_g.field_70179_y = motion_z / 2.0D;
            }
         }

         if ((int)Math.round(mc.field_71439_g.field_70163_u) != this.y_level && this.hybrid.get_value(true)) {
            this.set_disable();
            return;
         }

         if (!this.triggerable.get_value(true) && this.tick_runs >= this.tick_timeout.get_value(1)) {
            this.tick_runs = 0;
            this.set_disable();
            return;
         }

         for(int blocks_placed = 0; blocks_placed < this.tick_for_place.get_value(1); ++this.offset_step) {
            if (this.offset_step >= (this.mode.in("Face") ? this.surround_targets_face.length : (this.mode.in("Normal") ? this.surround_targets.length : this.surround_targets_city.length))) {
               this.offset_step = 0;
               break;
            }

            BlockPos offsetPos = new BlockPos(this.mode.in("Face") ? this.surround_targets_face[this.offset_step] : (this.mode.in("Normal") ? this.surround_targets[this.offset_step] : this.surround_targets_city[this.offset_step]));
            BlockPos targetPos = (new BlockPos(mc.field_71439_g.func_174791_d())).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p());
            boolean try_to_place = true;
            if (!mc.field_71441_e.func_180495_p(targetPos).func_185904_a().func_76222_j()) {
               try_to_place = false;
            }

            Iterator var11 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(targetPos)).iterator();

            while(var11.hasNext()) {
               Entity entity = (Entity)var11.next();
               if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                  try_to_place = false;
                  break;
               }
            }

            if (try_to_place && WurstplusBlockUtil.placeBlock(targetPos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
               ++blocks_placed;
            }
         }

         ++this.tick_runs;
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

   public Vec3d get_center(double posX, double posY, double posZ) {
      double x = Math.floor(posX) + 0.5D;
      double y = Math.floor(posY);
      double z = Math.floor(posZ) + 0.5D;
      return new Vec3d(x, y, z);
   }
}
