package me.listed.listedhack.client.hacks.combat;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class WurstplusAutoGapple extends WurstplusHack {
   WurstplusSetting delay = this.create("Delay", "GappleDelay", false);
   private boolean switching = false;
   private int last_slot;

   public WurstplusAutoGapple() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Gapple";
      this.tag = "AutoGapple";
      this.description = "put gapple in offhand";
   }

   public void update() {
      if (mc.field_71462_r == null || mc.field_71462_r instanceof GuiInventory) {
         if (this.switching) {
            this.swap_items(this.last_slot, 2);
            return;
         }

         this.swap_items(this.get_item_slot(), this.delay.get_value(true) ? 1 : 0);
      }

   }

   private int get_item_slot() {
      if (Items.field_151153_ao == mc.field_71439_g.func_184592_cb().func_77973_b()) {
         return -1;
      } else {
         for(int i = 36; i >= 0; --i) {
            Item item = mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == Items.field_151153_ao) {
               if (i < 9) {
                  return -1;
               }

               return i;
            }
         }

         return -1;
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
}
