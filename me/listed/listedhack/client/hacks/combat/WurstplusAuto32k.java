package me.listed.listedhack.client.hacks.combat;

import java.util.Objects;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class WurstplusAuto32k extends WurstplusHack {
   private BlockPos pos;
   private int hopper_slot;
   private int redstone_slot;
   private int shulker_slot;
   private int ticks_past;
   private int[] rot;
   private boolean setup;
   private boolean place_redstone;
   private boolean dispenser_done;
   WurstplusSetting place_mode = this.create("Place Mode", "AutotkPlaceMode", "Auto", this.combobox(new String[]{"Auto", "Looking", "Hopper"}));
   WurstplusSetting swing = this.create("Swing", "AutotkSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
   WurstplusSetting delay = this.create("Delay", "AutotkDelay", 4, 0, 10);
   WurstplusSetting rotate = this.create("Rotate", "Autotkrotate", false);
   WurstplusSetting debug = this.create("Debug", "AutotkDebug", false);

   public WurstplusAuto32k() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto 32k";
      this.tag = "Auto32k";
      this.description = "fastest in the west";
   }

   protected void enable() {
      this.ticks_past = 0;
      this.setup = false;
      this.dispenser_done = false;
      this.place_redstone = false;
      this.hopper_slot = -1;
      int dispenser_slot = -1;
      this.redstone_slot = -1;
      this.shulker_slot = -1;
      int block_slot = -1;

      int z;
      for(z = 0; z < 9; ++z) {
         Item item = mc.field_71439_g.field_71071_by.func_70301_a(z).func_77973_b();
         if (item == Item.func_150898_a(Blocks.field_150438_bZ)) {
            this.hopper_slot = z;
         } else if (item == Item.func_150898_a(Blocks.field_150367_z)) {
            dispenser_slot = z;
         } else if (item == Item.func_150898_a(Blocks.field_150451_bX)) {
            this.redstone_slot = z;
         } else if (item instanceof ItemShulkerBox) {
            this.shulker_slot = z;
         } else if (item instanceof ItemBlock) {
            block_slot = z;
         }
      }

      if ((this.hopper_slot == -1 || dispenser_slot == -1 || this.redstone_slot == -1 || this.shulker_slot == -1 || block_slot == -1) && !this.place_mode.in("Hopper")) {
         WurstplusMessageUtil.send_client_message("missing item");
         this.set_disable();
      } else if (this.hopper_slot != -1 && this.shulker_slot != -1) {
         if (this.place_mode.in("Looking")) {
            RayTraceResult r = mc.field_71439_g.func_174822_a(5.0D, mc.func_184121_ak());
            this.pos = ((RayTraceResult)Objects.requireNonNull(r)).func_178782_a().func_177984_a();
            double pos_x = (double)this.pos.func_177958_n() - mc.field_71439_g.field_70165_t;
            double pos_z = (double)this.pos.func_177952_p() - mc.field_71439_g.field_70161_v;
            this.rot = Math.abs(pos_x) > Math.abs(pos_z) ? (pos_x > 0.0D ? new int[]{-1, 0} : new int[]{1, 0}) : (pos_z > 0.0D ? new int[]{0, -1} : new int[]{0, 1});
            if (WurstplusBlockUtil.canPlaceBlock(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(this.rot[0], 0, this.rot[1])) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(0, 1, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(0, 2, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(this.rot[0], 1, this.rot[1]))) {
               WurstplusBlockUtil.placeBlock(this.pos, block_slot, this.rotate.get_value(true), false, this.swing);
               WurstplusBlockUtil.rotatePacket((double)this.pos.func_177982_a(-this.rot[0], 1, -this.rot[1]).func_177958_n() + 0.5D, (double)(this.pos.func_177956_o() + 1), (double)this.pos.func_177982_a(-this.rot[0], 1, -this.rot[1]).func_177952_p() + 0.5D);
               WurstplusBlockUtil.placeBlock(this.pos.func_177984_a(), dispenser_slot, false, false, this.swing);
               WurstplusBlockUtil.openBlock(this.pos.func_177984_a());
               this.setup = true;
            } else {
               WurstplusMessageUtil.send_client_message("unable to place");
               this.set_disable();
            }
         } else {
            int x;
            int y;
            if (this.place_mode.in("Auto")) {
               for(z = -2; z <= 2; ++z) {
                  for(y = -1; y <= 1; ++y) {
                     for(x = -2; x <= 2; ++x) {
                        this.rot = Math.abs(z) > Math.abs(x) ? (z > 0 ? new int[]{-1, 0} : new int[]{1, 0}) : (x > 0 ? new int[]{0, -1} : new int[]{0, 1});
                        this.pos = mc.field_71439_g.func_180425_c().func_177982_a(z, y, x);
                        if (mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(mc.field_71439_g.func_174791_d().func_72441_c((double)((float)z - (float)this.rot[0] / 2.0F), (double)y + 0.5D, (double)(x + this.rot[1] / 2))) <= 4.5D && mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(mc.field_71439_g.func_174791_d().func_72441_c((double)z + 0.5D, (double)y + 2.5D, (double)x + 0.5D)) <= 4.5D && WurstplusBlockUtil.canPlaceBlock(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(this.rot[0], 0, this.rot[1])) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(0, 1, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(0, 2, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.func_177982_a(this.rot[0], 1, this.rot[1]))) {
                           WurstplusBlockUtil.placeBlock(this.pos, block_slot, this.rotate.get_value(true), false, this.swing);
                           WurstplusBlockUtil.rotatePacket((double)this.pos.func_177982_a(-this.rot[0], 1, -this.rot[1]).func_177958_n() + 0.5D, (double)(this.pos.func_177956_o() + 1), (double)this.pos.func_177982_a(-this.rot[0], 1, -this.rot[1]).func_177952_p() + 0.5D);
                           WurstplusBlockUtil.placeBlock(this.pos.func_177984_a(), dispenser_slot, false, false, this.swing);
                           WurstplusBlockUtil.openBlock(this.pos.func_177984_a());
                           this.setup = true;
                           return;
                        }
                     }
                  }
               }

               WurstplusMessageUtil.send_client_message("unable to place");
               this.set_disable();
            } else {
               for(z = -2; z <= 2; ++z) {
                  for(y = -1; y <= 2; ++y) {
                     for(x = -2; x <= 2; ++x) {
                        if ((z != 0 || y != 0 || x != 0) && (z != 0 || y != 1 || x != 0) && WurstplusBlockUtil.isBlockEmpty(mc.field_71439_g.func_180425_c().func_177982_a(z, y, x)) && mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(mc.field_71439_g.func_174791_d().func_72441_c((double)z + 0.5D, (double)y + 0.5D, (double)x + 0.5D)) < 4.5D && WurstplusBlockUtil.isBlockEmpty(mc.field_71439_g.func_180425_c().func_177982_a(z, y + 1, x)) && mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(mc.field_71439_g.func_174791_d().func_72441_c((double)z + 0.5D, (double)y + 1.5D, (double)x + 0.5D)) < 4.5D) {
                           WurstplusBlockUtil.placeBlock(mc.field_71439_g.func_180425_c().func_177982_a(z, y, x), this.hopper_slot, this.rotate.get_value(true), false, this.swing);
                           WurstplusBlockUtil.placeBlock(mc.field_71439_g.func_180425_c().func_177982_a(z, y + 1, x), this.shulker_slot, this.rotate.get_value(true), false, this.swing);
                           WurstplusBlockUtil.openBlock(mc.field_71439_g.func_180425_c().func_177982_a(z, y, x));
                           this.pos = mc.field_71439_g.func_180425_c().func_177982_a(z, y, x);
                           this.dispenser_done = true;
                           this.place_redstone = true;
                           this.setup = true;
                           return;
                        }
                     }
                  }
               }
            }
         }

      } else {
         WurstplusMessageUtil.send_client_message("missing item");
         this.set_disable();
      }
   }

   public void update() {
      if (this.ticks_past > 50 && !(mc.field_71462_r instanceof GuiHopper)) {
         WurstplusMessageUtil.send_client_message("inactive too long, disabling");
         this.set_disable();
      } else {
         if (this.setup && this.ticks_past > this.delay.get_value(1)) {
            if (!this.dispenser_done) {
               try {
                  mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, 36 + this.shulker_slot, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                  this.dispenser_done = true;
                  if (this.debug.get_value(true)) {
                     WurstplusMessageUtil.send_client_message("sent item");
                  }
               } catch (Exception var4) {
               }
            }

            if (!this.place_redstone) {
               WurstplusBlockUtil.placeBlock(this.pos.func_177982_a(0, 2, 0), this.redstone_slot, this.rotate.get_value(true), false, this.swing);
               if (this.debug.get_value(true)) {
                  WurstplusMessageUtil.send_client_message("placed redstone");
               }

               this.place_redstone = true;
               return;
            }

            if (!this.place_mode.in("Hopper") && mc.field_71441_e.func_180495_p(this.pos.func_177982_a(this.rot[0], 1, this.rot[1])).func_177230_c() instanceof BlockShulkerBox && mc.field_71441_e.func_180495_p(this.pos.func_177982_a(this.rot[0], 0, this.rot[1])).func_177230_c() != Blocks.field_150438_bZ && this.place_redstone && this.dispenser_done && !(mc.field_71462_r instanceof GuiInventory)) {
               WurstplusBlockUtil.placeBlock(this.pos.func_177982_a(this.rot[0], 0, this.rot[1]), this.hopper_slot, this.rotate.get_value(true), false, this.swing);
               WurstplusBlockUtil.openBlock(this.pos.func_177982_a(this.rot[0], 0, this.rot[1]));
               if (this.debug.get_value(true)) {
                  WurstplusMessageUtil.send_client_message("in the hopper");
               }
            }

            if (mc.field_71462_r instanceof GuiHopper) {
               GuiHopper gui = (GuiHopper)mc.field_71462_r;

               for(int slot = 32; slot <= 40; ++slot) {
                  if (EnchantmentHelper.func_77506_a(Enchantments.field_185302_k, gui.field_147002_h.func_75139_a(slot).func_75211_c()) > 5) {
                     mc.field_71439_g.field_71071_by.field_70461_c = slot - 32;
                     break;
                  }
               }

               if (!(((Slot)gui.field_147002_h.field_75151_b.get(0)).func_75211_c().func_77973_b() instanceof ItemAir)) {
                  boolean swapReady = true;
                  if (((GuiContainer)mc.field_71462_r).field_147002_h.func_75139_a(0).func_75211_c().field_190928_g) {
                     swapReady = false;
                  }

                  if (!((GuiContainer)mc.field_71462_r).field_147002_h.func_75139_a(this.shulker_slot + 32).func_75211_c().field_190928_g) {
                     swapReady = false;
                  }

                  if (swapReady) {
                     mc.field_71442_b.func_187098_a(((GuiContainer)mc.field_71462_r).field_147002_h.field_75152_c, 0, this.shulker_slot, ClickType.SWAP, mc.field_71439_g);
                     this.disable();
                  }
               }
            }
         }

         ++this.ticks_past;
      }
   }
}
