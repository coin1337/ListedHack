package me.listed.listedhack.client.hacks;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;

public class WurstplusClickHUD extends WurstplusHack {
   WurstplusSetting frame_view = this.create("info", "HUDStringsList", "Strings");
   WurstplusSetting strings_r = this.create("Color R", "HUDStringsColorR", 255, 0, 255);
   WurstplusSetting strings_g = this.create("Color G", "HUDStringsColorG", 255, 0, 255);
   WurstplusSetting strings_b = this.create("Color B", "HUDStringsColorB", 255, 0, 255);
   WurstplusSetting strings_a = this.create("Alpha", "HUDStringsColorA", 230, 0, 255);
   WurstplusSetting compass_scale = this.create("Compass Scale", "HUDCompassScale", 16, 1, 60);
   WurstplusSetting arraylist_mode = this.create("ArrayList", "HUDArrayList", "Free", this.combobox(new String[]{"Free", "Top R", "Top L", "Bottom R", "Bottom L"}));
   WurstplusSetting show_all_pots = this.create("All Potions", "HUDAllPotions", false);
   WurstplusSetting max_player_list = this.create("Max Players", "HUDMaxPlayers", 24, 1, 64);

   public WurstplusClickHUD() {
      super(WurstplusCategory.WURSTPLUS_CLIENT);
      this.name = "HUD";
      this.tag = "HUD";
      this.description = "hud stuff";
   }

   public void enable() {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         ListedHack.get_hack_manager().get_module_with_tag("ClickGUI").set_active(false);
         ListedHack.click_hud.back = false;
         mc.func_147108_a(ListedHack.click_hud);
      }

   }
}
