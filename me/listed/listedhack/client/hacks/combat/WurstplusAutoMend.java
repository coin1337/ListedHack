package me.listed.listedhack.client.hacks.combat;

import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.hacks.other.WurstplusMiddleClickPearl;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumHand;

public class WurstplusAutoMend extends WurstplusHack {
   WurstplusSetting minDamage = this.create("Min Damage", "MinDamage", 50, 1, 100);
   WurstplusSetting maxHeal = this.create("Max Mend", "MaxMend", 90, 1, 100);
   WurstplusSetting predict = this.create("Predict", "Predict", false);
   WurstplusSetting sneakOnly = this.create("Sneak Only", "SneakOnly", true);
   WurstplusSetting noEntityCollision = this.create("No EntityCollision", "noEntityCollision", true);
   char toMend = 0;

   public WurstplusAutoMend() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Mend";
      this.tag = "AutoMend";
      this.description = "automatically mends your armor";
   }

   public void update() {
      if (mc.field_71439_g != null && mc.field_71441_e != null && mc.field_71439_g.field_70173_aa >= 10) {
         int sumOfDamage = 0;
         List<ItemStack> armour = mc.field_71439_g.field_71071_by.field_70460_b;

         int i;
         for(i = 0; i < armour.size(); ++i) {
            ItemStack itemStack = (ItemStack)armour.get(i);
            if (!itemStack.field_190928_g) {
               float damageOnArmor = (float)(itemStack.func_77958_k() - itemStack.func_77952_i());
               float damagePercent = 100.0F - 100.0F * (1.0F - damageOnArmor / (float)itemStack.func_77958_k());
               if (damagePercent <= (float)this.maxHeal.get_value(1)) {
                  if (damagePercent <= (float)this.minDamage.get_value(1)) {
                     this.toMend = (char)(this.toMend | 1 << i);
                  }

                  if (this.predict.get_value(true)) {
                     sumOfDamage = (int)((float)sumOfDamage + ((float)(itemStack.func_77958_k() * this.maxHeal.get_value(1)) / 100.0F - (float)(itemStack.func_77958_k() - itemStack.func_77952_i())));
                  }
               } else {
                  this.toMend = (char)(this.toMend & ~(1 << i));
               }
            }
         }

         if (this.toMend > 0) {
            if (this.predict.get_value(true)) {
               i = mc.field_71441_e.field_72996_f.stream().filter((entity) -> {
                  return entity instanceof EntityXPOrb;
               }).filter((entity) -> {
                  return entity.func_70068_e(mc.field_71439_g) <= 1.0D;
               }).mapToInt((entity) -> {
                  return ((EntityXPOrb)entity).field_70530_e;
               }).sum();
               if (i * 2 < sumOfDamage) {
                  this.mendArmor(mc.field_71439_g.field_71071_by.field_70461_c);
               }
            } else {
               this.mendArmor(mc.field_71439_g.field_71071_by.field_70461_c);
            }
         }

      }
   }

   private void mendArmor(int oldSlot) {
      if (this.noEntityCollision.get_value(true)) {
         Iterator var2 = mc.field_71441_e.field_73010_i.iterator();

         while(var2.hasNext()) {
            EntityPlayer entityPlayer = (EntityPlayer)var2.next();
            if (entityPlayer.func_70032_d(mc.field_71439_g) < 1.0F && entityPlayer != mc.field_71439_g) {
               return;
            }
         }
      }

      if (!this.sneakOnly.get_value(true) || mc.field_71439_g.func_70093_af()) {
         int newSlot = this.findXPSlot();
         if (newSlot != -1) {
            if (oldSlot != newSlot) {
               mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
            }

            boolean offhand = WurstplusMiddleClickPearl.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151062_by;
            mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(0.0F, 90.0F, true));
            mc.field_71442_b.func_187101_a(mc.field_71439_g, mc.field_71441_e, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
         }
      }
   }

   private int findXPSlot() {
      int slot = -1;

      for(int i = 0; i < 9; ++i) {
         if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151062_by) {
            slot = i;
            break;
         }
      }

      return slot;
   }
}
