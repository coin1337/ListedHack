package me.listed.listedhack.client.hacks.visual;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.init.MobEffects;

public class WurstplusFullBright extends WurstplusHack {
   private float prior_gamma;

   public WurstplusFullBright() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Full Bright";
      this.tag = "FullBright";
      this.description = "bright";
   }

   protected void enable() {
      this.prior_gamma = mc.field_71474_y.field_74333_Y;
   }

   protected void disable() {
      mc.field_71474_y.field_74333_Y = this.prior_gamma;
   }

   public void update() {
      mc.field_71474_y.field_74333_Y = 1000.0F;
      mc.field_71439_g.func_184589_d(MobEffects.field_76439_r);
   }
}
