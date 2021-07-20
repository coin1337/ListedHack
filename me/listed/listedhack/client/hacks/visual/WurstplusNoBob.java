package me.listed.listedhack.client.hacks.visual;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusNoBob extends WurstplusHack {
   public WurstplusNoBob() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "NoBob";
      this.tag = "NoBob";
      this.description = "very op";
   }

   public void update() {
      if (!this.nullCheck()) {
         mc.field_71439_g.field_70140_Q = 4.0F;
         mc.field_71474_y.field_74336_f = false;
      }
   }
}
