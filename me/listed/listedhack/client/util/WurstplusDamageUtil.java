package me.listed.listedhack.client.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class WurstplusDamageUtil {
   public static int getItemDamage(ItemStack stack) {
      return stack.func_77958_k() - stack.func_77952_i();
   }

   public static float getDamageInPercent(ItemStack stack) {
      return (float)getItemDamage(stack) / (float)stack.func_77958_k() * 100.0F;
   }

   public static int getRoundedDamage(ItemStack stack) {
      return (int)getDamageInPercent(stack);
   }

   public static boolean hasDurability(ItemStack stack) {
      Item item = stack.func_77973_b();
      return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
   }
}
