package me.listed.listedhack.client.hacks.chat;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusAutoFax extends WurstplusHack {
   public WurstplusAutoFax() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Auto Fax";
      this.tag = "AutoFax";
      this.description = "spits fax on them haters";
   }

   public void enable() {
      super.enable();
      mc.field_71439_g.func_71165_d("LISTED AND CUMBIA OWNS ME AND ALL");
      this.set_disable();
   }
}
