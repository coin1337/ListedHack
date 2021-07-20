package me.listed.listedhack.client.hacks.visual;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusSmallShield extends WurstplusHack {
   WurstplusSetting height = this.create("Height", "height", 150, 0, 360);

   public WurstplusSmallShield() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "SmallShield";
      this.tag = "SmallShield";
      this.description = "Small poop";
   }

   public void update() {
      mc.field_71439_g.field_71155_g = (float)this.height.get_value(1);
   }
}
