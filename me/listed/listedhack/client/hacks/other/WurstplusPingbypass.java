package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusPingbypass extends WurstplusHack {
   public WurstplusPingbypass() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Ping Bypass";
      this.tag = "Pingbypass";
      this.description = "get gud ping";
   }

   public void enable() {
      super.enable();
      mc.field_71439_g.func_71165_d("my ping is now 69 thanks to ListedHack");
      this.set_disable();
   }
}
