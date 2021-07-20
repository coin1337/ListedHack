package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.WurstplusEventHandler;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusTPS extends WurstplusPinnable {
   public WurstplusTPS() {
      super("TPS", "TPS", 1.0F, 0, 0);
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      String line = "TPS: " + this.getTPS();
      this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
      this.set_width(this.get(line, "width") + 2);
      this.set_height(this.get(line, "height") + 2);
   }

   public String getTPS() {
      try {
         int tps = Math.round(WurstplusEventHandler.INSTANCE.get_tick_rate());
         if (tps >= 16) {
            return "§a" + Integer.toString(tps);
         } else {
            return tps >= 10 ? "§3" + Integer.toString(tps) : "§4" + Integer.toString(tps);
         }
      } catch (Exception var2) {
         return "oh no " + var2;
      }
   }
}
