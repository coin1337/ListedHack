package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusParkour extends WurstplusHack {
   public WurstplusParkour() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Parkour";
      this.tag = "Parkour";
      this.description = "auto jumps";
   }

   public void update() {
      if (!this.nullCheck()) {
         if (mc.field_71439_g.field_70122_E && !mc.field_71439_g.func_70093_af() && !mc.field_71474_y.field_74311_E.func_151468_f() && !mc.field_71474_y.field_74314_A.func_151468_f() && mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -0.5D, 0.0D).func_72321_a(-0.001D, 0.0D, -0.001D)).isEmpty()) {
            mc.field_71439_g.func_70664_aZ();
         }

      }
   }
}
