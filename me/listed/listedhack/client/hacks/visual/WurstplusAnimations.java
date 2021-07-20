package me.listed.listedhack.client.hacks.visual;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.util.EnumHand;

public class WurstplusAnimations extends WurstplusHack {
   WurstplusSetting animation = this.create("1.8 Animations", "1.8 Animations", true);

   public WurstplusAnimations() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Animations";
      this.tag = "Animations";
      this.description = "1.8 animations";
   }

   public void update() {
      if ((double)mc.field_71460_t.field_78516_c.field_187470_g >= 0.9D) {
         mc.field_71460_t.field_78516_c.field_187469_f = 1.0F;
         mc.field_71460_t.field_78516_c.field_187467_d = mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND);
      }

      if ((double)mc.field_71460_t.field_78516_c.field_187472_i >= 0.9D) {
         mc.field_71460_t.field_78516_c.field_187471_h = 1.0F;
         mc.field_71460_t.field_78516_c.field_187468_e = mc.field_71439_g.func_184586_b(EnumHand.OFF_HAND);
      }

   }
}
