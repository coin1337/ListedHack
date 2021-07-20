package me.listed.listedhack.client.hacks.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class WurstplusStopEXP extends WurstplusHack {
   WurstplusSetting helmet_boot_percent = this.create("Helment Boots %", "StopEXHelmet", 80, 0, 100);
   WurstplusSetting chest_leggings_percent = this.create("Chest Leggins %", "StopEXChest", 100, 0, 100);
   private boolean should_cancel = false;
   @EventHandler
   private Listener<WurstplusEventPacket.SendPacket> packet_event = new Listener((event) -> {
      if (event.get_packet() instanceof CPacketPlayerTryUseItem && this.should_cancel) {
         event.cancel();
      }

   }, new Predicate[0]);

   public WurstplusStopEXP() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Stop EXP";
      this.tag = "StopEXP";
      this.description = "jumpy has a good idea?? (nvm this is dumb)";
   }

   public void update() {
      int counter = 0;
      Iterator var2 = this.get_armor().entrySet().iterator();

      while(true) {
         double percent;
         do {
            Entry armor_slot;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               armor_slot = (Entry)var2.next();
               ++counter;
            } while(((ItemStack)armor_slot.getValue()).func_190926_b());

            ItemStack stack = (ItemStack)armor_slot.getValue();
            double max_dam = (double)stack.func_77958_k();
            double dam_left = (double)(stack.func_77958_k() - stack.func_77952_i());
            percent = dam_left / max_dam * 100.0D;
            if (counter == 1 || counter == 4) {
               if (percent >= (double)this.helmet_boot_percent.get_value(1)) {
                  if (this.is_holding_exp()) {
                     this.should_cancel = true;
                  } else {
                     this.should_cancel = false;
                  }
               } else {
                  this.should_cancel = false;
               }
            }
         } while(counter != 2 && counter != 3);

         if (percent >= (double)this.chest_leggings_percent.get_value(1)) {
            if (this.is_holding_exp()) {
               this.should_cancel = true;
            } else {
               this.should_cancel = false;
            }
         } else {
            this.should_cancel = false;
         }
      }
   }

   private Map<Integer, ItemStack> get_armor() {
      return this.get_inv_slots(5, 8);
   }

   private Map<Integer, ItemStack> get_inv_slots(int current, int last) {
      HashMap full_inv_slots;
      for(full_inv_slots = new HashMap(); current <= last; ++current) {
         full_inv_slots.put(current, mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
      }

      return full_inv_slots;
   }

   public boolean is_holding_exp() {
      return mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle || mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemExpBottle;
   }
}
