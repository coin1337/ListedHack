package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusTimer extends WurstplusHack {
   WurstplusSetting speed = this.create("Speed", "Speed", 4.0D, 0.0D, 10.0D);

   public WurstplusTimer() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Timer";
      this.tag = "Timer";
      this.description = "timer";
   }

   public void disable() {
      mc.field_71428_T.field_194149_e = 50.0F;
   }

   public void update() {
      mc.field_71428_T.field_194149_e = 50.0F / ((float)this.speed.get_value(1) == 0.0F ? 0.1F : (float)this.speed.get_value(1));
   }
}
