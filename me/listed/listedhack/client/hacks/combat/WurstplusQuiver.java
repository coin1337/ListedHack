package me.listed.listedhack.client.hacks.combat;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class WurstplusQuiver extends WurstplusHack {
   WurstplusSetting speed = this.create("Speed", "QuiverSpeed", true);
   WurstplusSetting strength = this.create("Strength", "QuiverStrength", true);
   int randomVariation;

   public WurstplusQuiver() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Quiver";
      this.tag = "Quiver";
      this.description = "shoots arrow over you";
   }

   public void update() {
      PotionEffect speedEffect = mc.field_71439_g.func_70660_b(Potion.func_188412_a(1));
      PotionEffect strengthEffect = mc.field_71439_g.func_70660_b(Potion.func_188412_a(5));
      boolean hasSpeed = speedEffect != null;
      boolean hasStrength = strengthEffect != null;
      if (mc.field_71439_g.field_71071_by.field_70461_c == this.find_bow_hotbar()) {
         mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(0.0F, -90.0F, true));
      }

      if (this.strength.get_value(true) && !hasStrength && mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() == Items.field_151031_f && this.isArrowInInventory("Arrow of Strength")) {
         if (mc.field_71439_g.func_184612_cw() >= this.getBowCharge()) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
            mc.field_71439_g.func_184597_cx();
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.field_71439_g.func_184598_c(EnumHand.MAIN_HAND);
         } else if (mc.field_71439_g.func_184612_cw() == 0) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.field_71439_g.func_184598_c(EnumHand.MAIN_HAND);
         }
      }

      if (this.speed.get_value(true) && !hasSpeed && mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() == Items.field_151031_f && this.isArrowInInventory("Arrow of Speed")) {
         if (mc.field_71439_g.func_184612_cw() >= this.getBowCharge()) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
            mc.field_71439_g.func_184597_cx();
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.field_71439_g.func_184598_c(EnumHand.MAIN_HAND);
         } else if (mc.field_71439_g.func_184612_cw() == 0) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.field_71439_g.func_184598_c(EnumHand.MAIN_HAND);
         }
      }

   }

   private int find_bow_hotbar() {
      for(int i = 0; i < 9; ++i) {
         if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_185158_cP) {
            return i;
         }
      }

      return -1;
   }

   private boolean isArrowInInventory(String type) {
      boolean inInv = false;

      for(int i = 0; i < 36; ++i) {
         ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (itemStack.func_77973_b() == Items.field_185167_i && itemStack.func_82833_r().equalsIgnoreCase(type)) {
            inInv = true;
            this.switchArrow(i);
            break;
         }
      }

      return inInv;
   }

   private void switchArrow(int oldSlot) {
      WurstplusMessageUtil.send_client_message("Switching arrows!");
      int bowSlot = mc.field_71439_g.field_71071_by.field_70461_c;
      int placeSlot = bowSlot + 1;
      if (placeSlot > 8) {
         placeSlot = 1;
      }

      if (placeSlot != oldSlot) {
         if (mc.field_71462_r instanceof GuiContainer) {
            return;
         }

         mc.field_71442_b.func_187098_a(0, oldSlot, 0, ClickType.PICKUP, mc.field_71439_g);
         mc.field_71442_b.func_187098_a(0, placeSlot, 0, ClickType.PICKUP, mc.field_71439_g);
         mc.field_71442_b.func_187098_a(0, oldSlot, 0, ClickType.PICKUP, mc.field_71439_g);
      }

   }

   private int getBowCharge() {
      if (this.randomVariation == 0) {
         this.randomVariation = 1;
      }

      return 1 + this.randomVariation;
   }
}
