package me.listed.listedhack.client.hacks.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusPair;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class WurstplusAutoReplenish extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "AutoReplenishMode", "All", this.combobox(new String[]{"All", "Crystals", "Xp", "Both"}));
   WurstplusSetting threshold = this.create("Threshold", "AutoReplenishThreshold", 32, 1, 63);
   WurstplusSetting tickdelay = this.create("Delay", "AutoReplenishDelay", 2, 1, 10);
   private int delay_step = 0;

   public WurstplusAutoReplenish() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Hotbar Replenish";
      this.tag = "HotbarReplenish";
      this.description = "chad this doesnt desync you i swear";
   }

   public void update() {
      if (!(mc.field_71462_r instanceof GuiContainer)) {
         if (this.delay_step < this.tickdelay.get_value(1)) {
            ++this.delay_step;
         } else {
            this.delay_step = 0;
            WurstplusPair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
            if (slots != null) {
               int inventorySlot = (Integer)slots.getKey();
               int hotbarSlot = (Integer)slots.getValue();
               mc.field_71442_b.func_187098_a(0, inventorySlot, 0, ClickType.PICKUP, mc.field_71439_g);
               mc.field_71442_b.func_187098_a(0, hotbarSlot, 0, ClickType.PICKUP, mc.field_71439_g);
               mc.field_71442_b.func_187098_a(0, inventorySlot, 0, ClickType.PICKUP, mc.field_71439_g);
               mc.field_71442_b.func_78765_e();
            }
         }
      }
   }

   private WurstplusPair<Integer, Integer> findReplenishableHotbarSlot() {
      WurstplusPair<Integer, Integer> returnPair = null;
      Iterator var2 = this.get_hotbar().entrySet().iterator();

      while(var2.hasNext()) {
         Entry<Integer, ItemStack> hotbarSlot = (Entry)var2.next();
         ItemStack stack = (ItemStack)hotbarSlot.getValue();
         if (!stack.field_190928_g && stack.func_77973_b() != Items.field_190931_a && stack.func_77985_e() && stack.field_77994_a < stack.func_77976_d() && stack.field_77994_a <= this.threshold.get_value(1)) {
            int inventorySlot = this.findCompatibleInventorySlot(stack);
            if (inventorySlot != -1) {
               returnPair = new WurstplusPair(inventorySlot, hotbarSlot.getKey());
            }
         }
      }

      return returnPair;
   }

   private int findCompatibleInventorySlot(ItemStack hotbarStack) {
      int inventorySlot = -1;
      int smallestStackSize = 999;
      Iterator var4 = this.get_inventory().entrySet().iterator();

      while(var4.hasNext()) {
         Entry<Integer, ItemStack> entry = (Entry)var4.next();
         ItemStack inventoryStack = (ItemStack)entry.getValue();
         if (!inventoryStack.field_190928_g && inventoryStack.func_77973_b() != Items.field_190931_a && this.isCompatibleStacks(hotbarStack, inventoryStack)) {
            int currentStackSize = ((ItemStack)mc.field_71439_g.field_71069_bz.func_75138_a().get((Integer)entry.getKey())).field_77994_a;
            if (smallestStackSize > currentStackSize) {
               smallestStackSize = currentStackSize;
               inventorySlot = (Integer)entry.getKey();
            }
         }
      }

      return inventorySlot;
   }

   private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2) {
      if (!stack1.func_77973_b().equals(stack2.func_77973_b())) {
         return false;
      } else {
         if (stack1.func_77973_b() instanceof ItemBlock && stack2.func_77973_b() instanceof ItemBlock) {
            Block block1 = ((ItemBlock)stack1.func_77973_b()).func_179223_d();
            Block block2 = ((ItemBlock)stack2.func_77973_b()).func_179223_d();
            if (!block1.field_149764_J.equals(block2.field_149764_J)) {
               return false;
            }
         }

         return stack1.func_82833_r().equals(stack2.func_82833_r()) && stack1.func_77952_i() == stack2.func_77952_i();
      }
   }

   private Map<Integer, ItemStack> get_inventory() {
      return this.get_inv_slots(9, 35);
   }

   private Map<Integer, ItemStack> get_hotbar() {
      return this.get_inv_slots(36, 44);
   }

   private Map<Integer, ItemStack> get_inv_slots(int current, int last) {
      HashMap fullInventorySlots;
      for(fullInventorySlots = new HashMap(); current <= last; ++current) {
         fullInventorySlots.put(current, (ItemStack)mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
      }

      return fullInventorySlots;
   }
}
