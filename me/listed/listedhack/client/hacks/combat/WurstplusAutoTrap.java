package me.listed.listedhack.client.hacks.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockUtil;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusAutoTrap extends WurstplusHack {
   WurstplusSetting place_mode = this.create("Place Mode", "TrapPlaceMode", "Normal", this.combobox(new String[]{"Extra", "Face", "Normal", "Feet"}));
   WurstplusSetting blocks_per_tick = this.create("Speed", "TrapSpeed", 4, 0, 8);
   WurstplusSetting rotate = this.create("Rotation", "TrapRotation", true);
   WurstplusSetting chad_mode = this.create("Chad Mode", "TrapChadMode", true);
   WurstplusSetting swing = this.create("Swing", "TrapSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
   private final Vec3d[] offsets_default = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 1.0D), new Vec3d(1.0D, 3.0D, 0.0D), new Vec3d(-1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
   private final Vec3d[] offsets_face = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 1.0D), new Vec3d(1.0D, 3.0D, 0.0D), new Vec3d(-1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
   private final Vec3d[] offsets_feet = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 1.0D), new Vec3d(1.0D, 3.0D, 0.0D), new Vec3d(-1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
   private final Vec3d[] offsets_extra = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D), new Vec3d(0.0D, 4.0D, 0.0D)};
   private String last_tick_target_name = "";
   private int offset_step = 0;
   private int timeout_ticker = 0;
   private int y_level;
   private boolean first_run = true;

   public WurstplusAutoTrap() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Trap";
      this.tag = "AutoTrap";
      this.description = "cover people in obsidian :o";
   }

   public void enable() {
      this.timeout_ticker = 0;
      this.y_level = (int)Math.round(mc.field_71439_g.field_70163_u);
      this.first_run = true;
      if (this.find_obi_in_hotbar() == -1) {
         WurstplusMessageUtil.send_client_error_message("No obby");
         this.set_disable();
      }

   }

   public void update() {
      int timeout_ticks = 20;
      if (this.timeout_ticker > timeout_ticks && this.chad_mode.get_value(true)) {
         this.timeout_ticker = 0;
         this.set_disable();
      } else {
         EntityPlayer closest_target = this.find_closest_target();
         if (closest_target == null) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
         } else if (this.chad_mode.get_value(true) && (int)Math.round(mc.field_71439_g.field_70163_u) != this.y_level) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
         } else {
            if (this.first_run) {
               this.first_run = false;
               this.last_tick_target_name = closest_target.func_70005_c_();
            } else if (!this.last_tick_target_name.equals(closest_target.func_70005_c_())) {
               this.last_tick_target_name = closest_target.func_70005_c_();
               this.offset_step = 0;
            }

            List<Vec3d> place_targets = new ArrayList();
            if (this.place_mode.in("Normal")) {
               Collections.addAll(place_targets, this.offsets_default);
            } else if (this.place_mode.in("Extra")) {
               Collections.addAll(place_targets, this.offsets_extra);
            } else if (this.place_mode.in("Feet")) {
               Collections.addAll(place_targets, this.offsets_feet);
            } else {
               Collections.addAll(place_targets, this.offsets_face);
            }

            for(int blocks_placed = 0; blocks_placed < this.blocks_per_tick.get_value(1); ++this.offset_step) {
               if (this.offset_step >= place_targets.size()) {
                  this.offset_step = 0;
                  break;
               }

               BlockPos offset_pos = new BlockPos((Vec3d)place_targets.get(this.offset_step));
               BlockPos target_pos = (new BlockPos(closest_target.func_174791_d())).func_177977_b().func_177982_a(offset_pos.func_177958_n(), offset_pos.func_177956_o(), offset_pos.func_177952_p());
               boolean should_try_place = true;
               if (!mc.field_71441_e.func_180495_p(target_pos).func_185904_a().func_76222_j()) {
                  should_try_place = false;
               }

               Iterator var8 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(target_pos)).iterator();

               while(var8.hasNext()) {
                  Entity entity = (Entity)var8.next();
                  if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                     should_try_place = false;
                     break;
                  }
               }

               if (should_try_place && WurstplusBlockUtil.placeBlock(target_pos, this.find_obi_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                  ++blocks_placed;
               }
            }

            ++this.timeout_ticker;
         }
      }
   }

   private int find_obi_in_hotbar() {
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

   public EntityPlayer find_closest_target() {
      if (mc.field_71441_e.field_73010_i.isEmpty()) {
         return null;
      } else {
         EntityPlayer closestTarget = null;
         Iterator var2 = mc.field_71441_e.field_73010_i.iterator();

         while(true) {
            EntityPlayer target;
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var2.hasNext()) {
                              return closestTarget;
                           }

                           target = (EntityPlayer)var2.next();
                        } while(target == mc.field_71439_g);
                     } while(WurstplusFriendUtil.isFriend(target.func_70005_c_()));
                  } while(!WurstplusEntityUtil.isLiving(target));
               } while(target.func_110143_aJ() <= 0.0F);
            } while(closestTarget != null && mc.field_71439_g.func_70032_d(target) > mc.field_71439_g.func_70032_d(closestTarget));

            closestTarget = target;
         }
      }
   }
}
