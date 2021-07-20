package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusReverseStep extends WurstplusHack {
   WurstplusSetting height = this.create("Height", "reverstepheight", 2.5D, 0.5D, 10.0D);

   public WurstplusReverseStep() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "ReverseStep";
      this.tag = "ReverseStep";
      this.description = "moves down";
   }

   public void update() {
      if (mc.field_71441_e != null && mc.field_71439_g != null && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70617_f_() && !mc.field_71474_y.field_74314_A.func_151470_d()) {
         if (mc.field_71439_g != null && mc.field_71439_g.field_70122_E && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_70617_f_()) {
            for(double y = 0.0D; y < (double)this.height.get_value(1) + 0.5D; y += 0.01D) {
               if (!mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -y, 0.0D)).isEmpty()) {
                  mc.field_71439_g.field_70181_x = -10.0D;
                  break;
               }
            }
         }

      }
   }
}
