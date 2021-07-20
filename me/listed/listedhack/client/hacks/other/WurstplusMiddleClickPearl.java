package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

public class WurstplusMiddleClickPearl extends WurstplusHack {
   private boolean clicked = false;

   public WurstplusMiddleClickPearl() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Middleclick Pearl";
      this.tag = "MiddleclickPearl";
      this.description = "throws a pearl when you middle click";
   }

   public void update() {
      if (Mouse.isButtonDown(2)) {
         if (!this.clicked) {
            this.throwPearl();
         }

         this.clicked = true;
      } else {
         this.clicked = false;
      }

   }

   private void throwPearl() {
      int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
      boolean offhand = mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151079_bi;
      if (pearlSlot != -1 || offhand) {
         int oldslot = mc.field_71439_g.field_71071_by.field_70461_c;
         if (!offhand) {
            InventoryUtil.switchToSlot(pearlSlot);
         }

         mc.field_71442_b.func_187101_a(mc.field_71439_g, mc.field_71441_e, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
         if (!offhand) {
            InventoryUtil.switchToSlot(oldslot);
         }
      }

   }
}
