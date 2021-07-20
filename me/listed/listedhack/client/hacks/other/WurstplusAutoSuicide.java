package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusAutoSuicide extends WurstplusHack {
   public WurstplusAutoSuicide() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto Suicide";
      this.tag = "AutoSuicide";
      this.description = "kill yourself";
   }

   public void enable() {
      super.enable();
      mc.field_71439_g.func_71165_d("/kill");
      this.set_disable();
   }
}
