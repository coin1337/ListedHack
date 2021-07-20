package me.listed.listedhack.mixins.accessor;

import net.minecraft.item.ItemStack;

public interface ItemRenderer {
   float getPrevEquippedProgressMainHand();

   void setEquippedProgressMainHand(float var1);

   float getPrevEquippedProgressOffHand();

   void setEquippedProgressOffHand(float var1);

   void setItemStackMainHand(ItemStack var1);

   void setItemStackOffHand(ItemStack var1);
}
