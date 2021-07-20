package me.listed.listedhack.client.hacks.visual;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusCapes extends WurstplusHack {
   WurstplusSetting cape = this.create("Cape", "CapeCape", "Listed", this.combobox(new String[]{"Listed"}));

   public WurstplusCapes() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Capes";
      this.tag = "Capes";
      this.description = "see epic capes behind epic dudes";
   }
}
