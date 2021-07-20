package me.listed.listedhack.client.hacks;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;

public class WurstplusManager extends WurstplusHack {
   WurstplusSetting rainbowPrefix = this.create("Rainbow Prefix", "RainbowPrefix", true);

   public WurstplusManager() {
      super(WurstplusCategory.WURSTPLUS_CLIENT);
      this.name = "Manager";
      this.tag = "Manager";
      this.description = "client manager";
   }
}
