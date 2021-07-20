package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class WurstplusAutoWither extends WurstplusHack {
   WurstplusSetting range = this.create("Range", "WitherRange", 4, 0, 6);
   private int head_slot;
   private int sand_slot;

   public WurstplusAutoWither() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto Wither";
      this.tag = "AutoWither";
      this.description = "makes withers";
   }

   protected void enable() {
   }

   public boolean has_blocks() {
      int count = 0;

      int i;
      ItemStack stack;
      for(i = 0; i < 9; ++i) {
         stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block instanceof BlockSoulSand) {
               this.sand_slot = i;
               ++count;
               break;
            }
         }
      }

      for(i = 0; i < 9; ++i) {
         stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack.func_77973_b() == Items.field_151144_bL && stack.func_77952_i() == 1) {
            this.head_slot = i;
            ++count;
            break;
         }
      }

      return count == 2;
   }
}
