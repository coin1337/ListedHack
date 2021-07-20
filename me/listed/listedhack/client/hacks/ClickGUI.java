package me.listed.listedhack.client.hacks;

import java.awt.Color;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.util.WurstplusTimer;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends WurstplusHack {
   WurstplusSetting red = this.create("Red", "PastGUIR", 255, 0, 255);
   WurstplusSetting green = this.create("Green", "PastGUIG", 255, 0, 255);
   WurstplusSetting blue = this.create("Blue", "PastGUIB", 255, 0, 255);
   WurstplusSetting rainbow = this.create("Rainbow", "Rainbow", true);
   WurstplusSetting font = this.create("Font", "PastGUIFont", "Lato", this.combobox(new String[]{"Lato", "Verdana", "Arial", "None"}));
   WurstplusSetting scroll_speed = this.create("Scroll Speed", "PastGUIScrollSpeed", 10, 0, 20);
   WurstplusSetting button_sound = this.create("Button Sound", "PastGUISound", true);
   WurstplusSetting font_shadow = this.create("Font Shadow", "PastGUIFontShadow", false);
   WurstplusSetting hover_change = this.create("Hover Change", "PastGUIHoverChange", true);
   WurstplusSetting pause_game = this.create("Pause Game", "PastGUIPauseGame", false);
   WurstplusTimer timer = new WurstplusTimer();

   public ClickGUI() {
      super(WurstplusCategory.WURSTPLUS_CLIENT);
      this.name = "ClickGUI";
      this.tag = "ClickGUI";
      this.description = "GUI";
      this.set_bind(54);
   }

   protected void enable() {
      if (!this.nullCheck()) {
         mc.func_147108_a(ListedHack.past_gui);
      }

   }

   protected void disable() {
      if (!this.nullCheck()) {
         mc.func_147108_a((GuiScreen)null);
      }

   }

   public void update() {
      if (this.rainbow.get_value(true)) {
         this.cycle_rainbow();
      }

   }

   public void cycle_rainbow() {
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
      this.red.set_value(color_rgb_o >> 16 & 255);
      this.green.set_value(color_rgb_o >> 8 & 255);
      this.blue.set_value(color_rgb_o & 255);
   }
}
