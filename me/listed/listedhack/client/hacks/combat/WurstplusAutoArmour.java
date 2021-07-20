package me.listed.listedhack.client.hacks.combat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class WurstplusAutoArmour extends WurstplusHack {
   WurstplusSetting delay = this.create("Delay", "AADelay", 2, 0, 5);
   WurstplusSetting smart_mode = this.create("Smart Mode", "AASmartMode", true);
   WurstplusSetting put_back = this.create("Equip Armour", "AAEquipArmour", true);
   WurstplusSetting player_range = this.create("Player Range", "AAPlayerRange", 8, 0, 20);
   WurstplusSetting crystal_range = this.create("Crystal Range", "AACrystalRange", 13, 0, 20);
   WurstplusSetting boot_percent = this.create("Boot Percent", "AATBootPercent", 80, 0, 100);
   WurstplusSetting chest_percent = this.create("Chest Percent", "AATChestPercent", 80, 0, 100);
   private int delay_count;

   public WurstplusAutoArmour() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Armor";
      this.tag = "AutoArmor";
      this.description = "WATCH UR BOOTS";
   }

   protected void enable() {
      this.delay_count = 0;
   }

   public void update() {
      if (mc.field_71439_g.field_70173_aa % 2 != 0) {
         boolean flag = false;
         if (this.delay_count < this.delay.get_value(0)) {
            ++this.delay_count;
         } else {
            this.delay_count = 0;
            if (this.smart_mode.get_value(true) && !this.is_crystal_in_range(this.crystal_range.get_value(1)) && !this.is_player_in_range(this.player_range.get_value(1))) {
               flag = true;
            }

            if (flag) {
               if (mc.field_71474_y.field_74313_G.func_151470_d() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
                  this.take_off();
               }

            } else if (this.put_back.get_value(true)) {
               if (!(mc.field_71462_r instanceof GuiContainer) || mc.field_71462_r instanceof InventoryEffectRenderer) {
                  int[] bestArmorSlots = new int[4];
                  int[] bestArmorValues = new int[4];

                  int armorType;
                  ItemStack stack;
                  for(armorType = 0; armorType < 4; ++armorType) {
                     stack = mc.field_71439_g.field_71071_by.func_70440_f(armorType);
                     if (stack.func_77973_b() instanceof ItemArmor) {
                        bestArmorValues[armorType] = ((ItemArmor)stack.func_77973_b()).field_77879_b;
                     }

                     bestArmorSlots[armorType] = -1;
                  }

                  for(armorType = 0; armorType < 36; ++armorType) {
                     stack = mc.field_71439_g.field_71071_by.func_70301_a(armorType);
                     if (stack.func_190916_E() <= 1 && stack.func_77973_b() instanceof ItemArmor) {
                        ItemArmor armor = (ItemArmor)stack.func_77973_b();
                        int armorType = armor.field_77881_a.ordinal() - 2;
                        if (armorType != 2 || !mc.field_71439_g.field_71071_by.func_70440_f(armorType).func_77973_b().equals(Items.field_185160_cR)) {
                           int armorValue = armor.field_77879_b;
                           if (armorValue > bestArmorValues[armorType]) {
                              bestArmorSlots[armorType] = armorType;
                              bestArmorValues[armorType] = armorValue;
                           }
                        }
                     }
                  }

                  for(armorType = 0; armorType < 4; ++armorType) {
                     int slot = bestArmorSlots[armorType];
                     if (slot != -1) {
                        ItemStack oldArmor = mc.field_71439_g.field_71071_by.func_70440_f(armorType);
                        if (oldArmor != ItemStack.field_190927_a || mc.field_71439_g.field_71071_by.func_70447_i() != -1) {
                           if (slot < 9) {
                              slot += 36;
                           }

                           mc.field_71442_b.func_187098_a(0, 8 - armorType, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                           mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.QUICK_MOVE, mc.field_71439_g);
                           break;
                        }
                     }
                  }

               }
            }
         }
      }
   }

   public boolean is_player_in_range(int range) {
      Iterator var2 = ((List)mc.field_71441_e.field_73010_i.stream().filter((entityPlayer) -> {
         return !WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_());
      }).collect(Collectors.toList())).iterator();

      Entity player;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         player = (Entity)var2.next();
      } while(player == mc.field_71439_g || !(mc.field_71439_g.func_70032_d(player) < (float)range));

      return true;
   }

   public boolean is_crystal_in_range(int range) {
      Iterator var2 = ((List)mc.field_71441_e.field_72996_f.stream().filter((entity) -> {
         return entity instanceof EntityEnderCrystal;
      }).collect(Collectors.toList())).iterator();

      Entity c;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         c = (Entity)var2.next();
      } while(!(mc.field_71439_g.func_70032_d(c) < (float)range));

      return true;
   }

   public void take_off() {
      if (this.is_space()) {
         Iterator var1 = get_armour().entrySet().iterator();

         Entry armorSlot;
         ItemStack stack;
         do {
            if (!var1.hasNext()) {
               return;
            }

            armorSlot = (Entry)var1.next();
            stack = (ItemStack)armorSlot.getValue();
         } while(!this.is_healed(stack));

         mc.field_71442_b.func_187098_a(0, (Integer)armorSlot.getKey(), 0, ClickType.QUICK_MOVE, mc.field_71439_g);
      }
   }

   public boolean is_space() {
      Iterator var1 = get_inv().entrySet().iterator();

      ItemStack stack;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         Entry<Integer, ItemStack> invSlot = (Entry)var1.next();
         stack = (ItemStack)invSlot.getValue();
      } while(stack.func_77973_b() != Items.field_190931_a);

      return true;
   }

   private static Map<Integer, ItemStack> get_inv() {
      return get_inv_slots(9, 44);
   }

   private static Map<Integer, ItemStack> get_armour() {
      return get_inv_slots(5, 8);
   }

   private static Map<Integer, ItemStack> get_inv_slots(int current, int last) {
      HashMap fullInventorySlots;
      for(fullInventorySlots = new HashMap(); current <= last; ++current) {
         fullInventorySlots.put(current, mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
      }

      return fullInventorySlots;
   }

   public boolean is_healed(ItemStack item) {
      double max_dam;
      double dam_left;
      double percent;
      if (item.func_77973_b() != Items.field_151175_af && item.func_77973_b() != Items.field_151161_ac) {
         max_dam = (double)item.func_77958_k();
         dam_left = (double)(item.func_77958_k() - item.func_77952_i());
         percent = dam_left / max_dam * 100.0D;
         return percent >= (double)this.chest_percent.get_value(1);
      } else {
         max_dam = (double)item.func_77958_k();
         dam_left = (double)(item.func_77958_k() - item.func_77952_i());
         percent = dam_left / max_dam * 100.0D;
         return percent >= (double)this.boot_percent.get_value(1);
      }
   }
}
