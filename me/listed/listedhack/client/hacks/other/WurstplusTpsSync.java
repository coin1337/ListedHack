package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusTpsSync extends WurstplusHack {
   WurstplusSetting Mine = this.create("Mine", "Mine", true);
   WurstplusSetting Attack = this.create("Attack", "Attack", false);

   public WurstplusTpsSync() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "TpsSync";
      this.tag = "TpsSync";
      this.description = "My asshole hurts bruh";
   }
}
