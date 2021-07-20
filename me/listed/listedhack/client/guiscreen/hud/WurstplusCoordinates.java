package me.listed.listedhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusCoordinates extends WurstplusPinnable {
   ChatFormatting dg;
   ChatFormatting db;
   ChatFormatting dr;

   public WurstplusCoordinates() {
      super("Coordinates", "Coordinates", 1.0F, 0, 0);
      this.dg = ChatFormatting.DARK_GRAY;
      this.db = ChatFormatting.DARK_BLUE;
      this.dr = ChatFormatting.DARK_RED;
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      String x = ListedHack.g + "[" + ListedHack.r + Integer.toString((int)this.mc.field_71439_g.field_70165_t) + ListedHack.g + "]" + ListedHack.r;
      String y = ListedHack.g + "[" + ListedHack.r + Integer.toString((int)this.mc.field_71439_g.field_70163_u) + ListedHack.g + "]" + ListedHack.r;
      String z = ListedHack.g + "[" + ListedHack.r + Integer.toString((int)this.mc.field_71439_g.field_70161_v) + ListedHack.g + "]" + ListedHack.r;
      String x_nether = ListedHack.g + "[" + ListedHack.r + Long.toString(Math.round(this.mc.field_71439_g.field_71093_bK != -1 ? this.mc.field_71439_g.field_70165_t / 8.0D : this.mc.field_71439_g.field_70165_t * 8.0D)) + ListedHack.g + "]" + ListedHack.r;
      String z_nether = ListedHack.g + "[" + ListedHack.r + Long.toString(Math.round(this.mc.field_71439_g.field_71093_bK != -1 ? this.mc.field_71439_g.field_70161_v / 8.0D : this.mc.field_71439_g.field_70161_v * 8.0D)) + ListedHack.g + "]" + ListedHack.r;
      String line = "XYZ " + x + y + z + " XZ " + x_nether + z_nether;
      this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
      this.set_width(this.get(line, "width"));
      this.set_height(this.get(line, "height") + 2);
   }
}
