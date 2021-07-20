package me.listed.listedhack.client.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class InventoryUtil {
   public static int findFirst(Class<? extends Item> clazz) {
      int b = -1;

      for(int a = 0; a < 9; ++a) {
         if (WurstplusEntityUtil.mc.field_71439_g.field_71071_by.func_70301_a(a).func_77973_b().getClass().equals(clazz)) {
            b = a;
            break;
         }
      }

      return b;
   }

   public static int find(Class<? extends Item> clazz) {
      int b = -1;

      for(int a = 0; a < 9; ++a) {
         if (WurstplusEntityUtil.mc.field_71439_g.field_71071_by.func_70301_a(a).func_77973_b().getClass().equals(clazz)) {
            b = a;
         }
      }

      return b;
   }

   public static int findFirst(Item item) {
      int b = -1;

      for(int a = 0; a < 9; ++a) {
         if (WurstplusEntityUtil.mc.field_71439_g.field_71071_by.func_70301_a(a).func_77973_b() == item) {
            b = a;
            break;
         }
      }

      return b;
   }

   public static int find(Item item) {
      int b = -1;

      for(int a = 0; a < 9; ++a) {
         if (WurstplusEntityUtil.mc.field_71439_g.field_71071_by.func_70301_a(a).func_77973_b() == item) {
            b = a;
         }
      }

      return b;
   }

   public static boolean switchTo(Item item) {
      int a = find(item);
      if (a == -1) {
         return false;
      } else {
         WurstplusEntityUtil.mc.field_71439_g.field_71071_by.field_70461_c = a;
         WurstplusEntityUtil.mc.field_71442_b.func_78765_e();
         return true;
      }
   }

   public static int getBlockInHotbar(Block block) {
      for(int i = 0; i < 9; ++i) {
         Item item = WurstplusEntityUtil.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
         if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d().equals(block)) {
            return i;
         }
      }

      return -1;
   }

   public static void switchToSlot(Block block) {
      if (getBlockInHotbar(block) != -1 && WurstplusEntityUtil.mc.field_71439_g.field_71071_by.field_70461_c != getBlockInHotbar(block)) {
         WurstplusEntityUtil.mc.field_71439_g.field_71071_by.field_70461_c = getBlockInHotbar(block);
         WurstplusEntityUtil.mc.field_71442_b.func_78765_e();
      }

   }

   public static int findHotbarBlock(Class clazz) {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = WurstplusEntityUtil.mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a) {
            if (clazz.isInstance(stack.func_77973_b())) {
               return i;
            }

            if (stack.func_77973_b() instanceof ItemBlock) {
               Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
               if (clazz.isInstance(block)) {
                  return i;
               }
            }
         }
      }

      return -1;
   }

   public static void switchToSlot(int slot) {
      if (slot != -1 && WurstplusEntityUtil.mc.field_71439_g.field_71071_by.field_70461_c != slot) {
         WurstplusEntityUtil.mc.field_71439_g.field_71071_by.field_70461_c = slot;
         WurstplusEntityUtil.mc.field_71442_b.func_78765_e();
      }

   }
}
