package me.listed.listedhack.client.hacks.combat;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusBlockUtil;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.math.BlockPos;

public class WurstplusAutoBed extends WurstplusHack {
   WurstplusSetting delay = this.create("Delay", "BedAuraDelay", 6, 0, 20);
   WurstplusSetting range = this.create("Range", "BedAuraRange", 5, 0, 6);
   WurstplusSetting hard = this.create("Hard Rotate", "BedAuraRotate", false);
   WurstplusSetting swing = this.create("Swing", "BedAuraSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
   WurstplusSetting r = this.create("R", "R", 20, 0, 255);
   WurstplusSetting g = this.create("G", "G", 20, 0, 255);
   WurstplusSetting b = this.create("B", "B", 180, 0, 255);
   WurstplusSetting a = this.create("A", "A", 255, 0, 255);
   WurstplusSetting rainbow_mode = this.create("Rainbow", "Rainbow", true);
   private BlockPos render_pos;
   private int counter;
   private WurstplusAutoBed.spoof_face spoof_looking;

   public WurstplusAutoBed() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Bed";
      this.tag = "AutoBed";
      this.description = "fucking endcrystal.me";
   }

   protected void enable() {
      this.render_pos = null;
      this.counter = 0;
   }

   protected void disable() {
      this.render_pos = null;
   }

   public void update() {
      if (this.rainbow_mode.get_value(true)) {
         this.cycle_rainbow();
      }

      if (mc.field_71439_g != null) {
         if (this.counter > this.delay.get_value(1)) {
            this.counter = 0;
            this.place_bed();
            this.break_bed();
            this.refill_bed();
         }

         ++this.counter;
      }
   }

   public void cycle_rainbow() {
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
      this.r.set_value(color_rgb_o >> 16 & 255);
      this.g.set_value(color_rgb_o >> 8 & 255);
      this.b.set_value(color_rgb_o & 255);
   }

   public void refill_bed() {
      if (!(mc.field_71462_r instanceof GuiContainer) && mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() == Items.field_190931_a) {
         for(int i = 9; i < 35; ++i) {
            if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151104_aV) {
               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.SWAP, mc.field_71439_g);
               break;
            }
         }
      }

   }

   public void place_bed() {
      if (this.find_bed() == -1) {
         WurstplusMessageUtil.send_client_error_message("Cannot find beds in hotbar");
         this.set_disable();
      } else {
         int bed_slot = this.find_bed();
         BlockPos best_pos = null;
         EntityPlayer best_target = null;
         float best_distance = (float)this.range.get_value(1);
         Iterator var5 = ((List)mc.field_71441_e.field_73010_i.stream().filter((entityPlayer) -> {
            return !WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_());
         }).collect(Collectors.toList())).iterator();

         while(var5.hasNext()) {
            EntityPlayer player = (EntityPlayer)var5.next();
            if (player != mc.field_71439_g && !(best_distance < mc.field_71439_g.func_70032_d(player))) {
               boolean face_place = true;
               BlockPos pos = get_pos_floor(player).func_177977_b();
               BlockPos pos2 = this.check_side_block(pos);
               if (pos2 != null) {
                  best_pos = pos2.func_177984_a();
                  best_target = player;
                  best_distance = mc.field_71439_g.func_70032_d(player);
                  face_place = false;
               }

               if (face_place) {
                  BlockPos upos = get_pos_floor(player);
                  BlockPos upos2 = this.check_side_block(upos);
                  if (upos2 != null) {
                     best_pos = upos2.func_177984_a();
                     best_target = player;
                     best_distance = mc.field_71439_g.func_70032_d(player);
                  }
               }
            }
         }

         if (best_target == null) {
            WurstplusMessageUtil.send_client_message("Cannot find player");
            this.set_disable();
         } else {
            this.render_pos = best_pos;
            if (this.spoof_looking == WurstplusAutoBed.spoof_face.NORTH) {
               if (this.hard.get_value(true)) {
                  mc.field_71439_g.field_70177_z = 180.0F;
               }

               mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(180.0F, 0.0F, mc.field_71439_g.field_70122_E));
            } else if (this.spoof_looking == WurstplusAutoBed.spoof_face.SOUTH) {
               if (this.hard.get_value(true)) {
                  mc.field_71439_g.field_70177_z = 0.0F;
               }

               mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(0.0F, 0.0F, mc.field_71439_g.field_70122_E));
            } else if (this.spoof_looking == WurstplusAutoBed.spoof_face.WEST) {
               if (this.hard.get_value(true)) {
                  mc.field_71439_g.field_70177_z = 90.0F;
               }

               mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(90.0F, 0.0F, mc.field_71439_g.field_70122_E));
            } else if (this.spoof_looking == WurstplusAutoBed.spoof_face.EAST) {
               if (this.hard.get_value(true)) {
                  mc.field_71439_g.field_70177_z = -90.0F;
               }

               mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(-90.0F, 0.0F, mc.field_71439_g.field_70122_E));
            }

            WurstplusBlockUtil.placeBlock(best_pos, bed_slot, false, false, this.swing);
         }
      }
   }

   public void break_bed() {
      BlockPos pos;
      for(Iterator var1 = ((List)WurstplusBlockInteractHelper.getSphere(get_pos_floor(mc.field_71439_g), (float)this.range.get_value(1), this.range.get_value(1), false, true, 0).stream().filter(WurstplusAutoBed::is_bed).collect(Collectors.toList())).iterator(); var1.hasNext(); WurstplusBlockUtil.openBlock(pos)) {
         pos = (BlockPos)var1.next();
         if (mc.field_71439_g.func_70093_af()) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
         }
      }

   }

   public int find_bed() {
      for(int i = 0; i < 9; ++i) {
         if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151104_aV) {
            return i;
         }
      }

      return -1;
   }

   public BlockPos check_side_block(BlockPos pos) {
      if (mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(pos.func_177974_f().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
         this.spoof_looking = WurstplusAutoBed.spoof_face.WEST;
         return pos.func_177974_f();
      } else if (mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(pos.func_177978_c().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
         this.spoof_looking = WurstplusAutoBed.spoof_face.SOUTH;
         return pos.func_177978_c();
      } else if (mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(pos.func_177976_e().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
         this.spoof_looking = WurstplusAutoBed.spoof_face.EAST;
         return pos.func_177976_e();
      } else if (mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(pos.func_177968_d().func_177984_a()).func_177230_c() == Blocks.field_150350_a) {
         this.spoof_looking = WurstplusAutoBed.spoof_face.NORTH;
         return pos.func_177968_d();
      } else {
         return null;
      }
   }

   public static BlockPos get_pos_floor(EntityPlayer player) {
      return new BlockPos(Math.floor(player.field_70165_t), Math.floor(player.field_70163_u), Math.floor(player.field_70161_v));
   }

   public static boolean is_bed(BlockPos pos) {
      Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
      return block == Blocks.field_150324_C;
   }

   public void render(WurstplusEventRender event) {
      if (this.render_pos != null) {
         RenderHelp.prepare("lines");
         RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)this.render_pos.func_177958_n(), (float)this.render_pos.func_177956_o(), (float)this.render_pos.func_177952_p(), 1.0F, 0.2F, 1.0F, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
         RenderHelp.release();
      }
   }

   static enum spoof_face {
      EAST,
      WEST,
      NORTH,
      SOUTH;
   }
}
