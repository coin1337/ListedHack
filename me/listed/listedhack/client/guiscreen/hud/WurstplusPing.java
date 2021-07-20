package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusPing extends WurstplusPinnable {
   public WurstplusPing() {
      super("Ping", "Ping", 1.0F, 0, 0);
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      String line = "Ping: " + this.get_ping();
      this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
      this.set_width(this.get(line, "width") + 2);
      this.set_height(this.get(line, "height") + 2);
   }

   public String get_ping() {
      try {
         int ping = this.mc.func_147114_u().func_175102_a(this.mc.field_71439_g.func_110124_au()).func_178853_c();
         if (ping <= 50) {
            return "§a" + Integer.toString(ping);
         } else {
            return ping <= 150 ? "§3" + Integer.toString(ping) : "§4" + Integer.toString(ping);
         }
      } catch (Exception var2) {
         return "oh no";
      }
   }
}
