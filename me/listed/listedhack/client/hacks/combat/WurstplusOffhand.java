package me.listed.listedhack.client.hacks.combat;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class WurstplusOffhand extends WurstplusHack {
   WurstplusSetting switch_mode = this.create("Offhand", "OffhandOffhand", "Totem", this.combobox(new String[]{"Totem", "Crystal", "Gapple", "Pressure Plate"}));
   WurstplusSetting totem_switch = this.create("Totem HP", "OffhandTotemHP", 16, 0, 36);
   WurstplusSetting ca_check = this.create("CACheck", "CACheck", false);
   WurstplusSetting gapple_in_hole = this.create("Gapple In Hole", "OffhandGapple", false);
   WurstplusSetting gapple_hole_hp = this.create("Gapple Hole HP", "OffhandGappleHP", 8, 0, 36);
   WurstplusSetting delay = this.create("Delay", "OffhandDelay", false);
   private boolean switching = false;
   private int last_slot;

   public WurstplusOffhand() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Offhand";
      this.tag = "Offhand";
      this.description = "Switches shit to ur offhand";
   }

   public void update() {
      if (mc.field_71462_r == null || mc.field_71462_r instanceof GuiInventory) {
         if (ListedHack.get_hack_manager().get_module_with_tag("AutoTotem").is_active()) {
            WurstplusMessageUtil.send_client_error_message("AutoTotem is not compatible with offhand anymore");
            this.set_disable();
         }

         if (this.switching) {
            this.swap_items(this.last_slot, 2);
            return;
         }

         float hp = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj();
         if (!(hp > (float)this.totem_switch.get_value(1))) {
            this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.get_value(true) ? 1 : 0);
            return;
         }

         if (this.ca_check.get_value(true)) {
            if (this.switch_mode.in("Crystal") && ListedHack.get_hack_manager().get_module_with_tag("AutoCrystal").is_active()) {
               this.swap_items(this.get_item_slot(Items.field_185158_cP), 0);
               return;
            }
         } else if (this.switch_mode.in("Crystal") && !this.ca_check.get_value(true)) {
            this.swap_items(this.get_item_slot(Items.field_185158_cP), 0);
            return;
         }

         if (this.gapple_in_hole.get_value(true) && hp > (float)this.gapple_hole_hp.get_value(1) && this.is_in_hole()) {
            this.swap_items(this.get_item_slot(Items.field_151153_ao), this.delay.get_value(true) ? 1 : 0);
            return;
         }

         if (this.switch_mode.in("Totem")) {
            this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.get_value(true) ? 1 : 0);
            return;
         }

         if (this.switch_mode.in("Gapple")) {
            this.swap_items(this.get_item_slot(Items.field_151153_ao), this.delay.get_value(true) ? 1 : 0);
            return;
         }

         if (this.switch_mode.in("Crystal") && !ListedHack.get_hack_manager().get_module_with_tag("AutoCrystal").is_active() && this.ca_check.get_value(true)) {
            this.swap_items(this.get_item_slot(Items.field_190929_cY), 0);
            return;
         }

         if (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190931_a) {
            this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.get_value(true) ? 1 : 0);
         }
      }

   }

   public void swap_items(int slot, int step) {
      if (slot != -1) {
         if (step == 0) {
            mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, mc.field_71439_g);
         }

         if (step == 1) {
            mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, mc.field_71439_g);
            this.switching = true;
            this.last_slot = slot;
         }

         if (step == 2) {
            mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, mc.field_71439_g);
            this.switching = false;
         }

         mc.field_71442_b.func_78765_e();
      }
   }

   private boolean is_in_hole() {
      BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
      return mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a;
   }

   private int get_item_slot(Item input) {
      if (input == mc.field_71439_g.func_184592_cb().func_77973_b()) {
         return -1;
      } else {
         for(int i = 36; i >= 0; --i) {
            Item item = mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == input) {
               if (i < 9) {
                  if (input == Items.field_151153_ao) {
                     return -1;
                  }

                  i += 36;
               }

               return i;
            }
         }

         return -1;
      }
   }
}
