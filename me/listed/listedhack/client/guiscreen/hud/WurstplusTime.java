package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.util.WurstplusTimeUtil;

public class WurstplusTime extends WurstplusPinnable {
   public WurstplusTime() {
      super("Time", "Time", 1.0F, 0, 0);
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      String line = "";
      line = line + (WurstplusTimeUtil.get_hour() < 10 ? "0" + WurstplusTimeUtil.get_hour() : WurstplusTimeUtil.get_hour());
      line = line + ":";
      line = line + (WurstplusTimeUtil.get_minuite() < 10 ? "0" + WurstplusTimeUtil.get_minuite() : WurstplusTimeUtil.get_minuite());
      line = line + ":";
      line = line + (WurstplusTimeUtil.get_second() < 10 ? "0" + WurstplusTimeUtil.get_second() : WurstplusTimeUtil.get_second());
      this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
      this.set_width(this.get(line, "width") + 2);
      this.set_height(this.get(line, "height") + 2);
   }
}
