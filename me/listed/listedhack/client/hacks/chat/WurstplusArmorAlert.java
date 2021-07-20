package me.listed.listedhack.client.hacks.chat;

import java.util.Iterator;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.item.ItemStack;

public class WurstplusArmorAlert extends WurstplusHack {
   int diedTime = 0;

   public WurstplusArmorAlert() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Armor Alert";
      this.tag = "ArmorAlert";
      this.description = "notifies you when your armor is low";
   }

   public void update() {
      boolean ArmorDurability = this.getArmorDurability();
      if (ArmorDurability) {
         WurstplusMessageUtil.send_client_message("Your armor is below 50%");
      }

   }

   private boolean getArmorDurability() {
      boolean TotalDurability = false;
      Iterator var2 = mc.field_71439_g.field_71071_by.field_70460_b.iterator();

      ItemStack itemStack;
      do {
         if (!var2.hasNext()) {
            return TotalDurability;
         }

         itemStack = (ItemStack)var2.next();
      } while(itemStack.func_77958_k() / 2 >= itemStack.func_77952_i());

      return true;
   }
}
