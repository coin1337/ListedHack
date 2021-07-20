package me.listed.listedhack.client.hacks;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import net.minecraft.client.gui.GuiScreen;

public class WurstplusClickGUI extends WurstplusHack {
   WurstplusSetting label_frame = this.create("info", "ClickGUIInfoFrame", "Frames");
   WurstplusSetting name_frame_r = this.create("Name R", "ClickGUINameFrameR", 255, 0, 255);
   WurstplusSetting name_frame_g = this.create("Name G", "ClickGUINameFrameG", 255, 0, 255);
   WurstplusSetting name_frame_b = this.create("Name B", "ClickGUINameFrameB", 255, 0, 255);
   WurstplusSetting background_frame_r = this.create("Background R", "ClickGUIBackgroundFrameR", 230, 0, 255);
   WurstplusSetting background_frame_g = this.create("Background G", "ClickGUIBackgroundFrameG", 100, 0, 255);
   WurstplusSetting background_frame_b = this.create("Background B", "ClickGUIBackgroundFrameB", 50, 0, 255);
   WurstplusSetting background_frame_a = this.create("Background A", "ClickGUIBackgroundFrameA", 210, 0, 255);
   WurstplusSetting border_frame_r = this.create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
   WurstplusSetting border_frame_g = this.create("Border G", "ClickGUIBorderFrameG", 255, 0, 255);
   WurstplusSetting border_frame_b = this.create("Border B", "ClickGUIBorderFrameB", 255, 0, 255);
   WurstplusSetting label_widget = this.create("info", "ClickGUIInfoWidget", "Widgets");
   WurstplusSetting name_widget_r = this.create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
   WurstplusSetting name_widget_g = this.create("Name G", "ClickGUINameWidgetG", 255, 0, 255);
   WurstplusSetting name_widget_b = this.create("Name B", "ClickGUINameWidgetB", 255, 0, 255);
   WurstplusSetting background_widget_r = this.create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
   WurstplusSetting background_widget_g = this.create("Background G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
   WurstplusSetting background_widget_b = this.create("Background B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
   WurstplusSetting background_widget_a = this.create("Background A", "ClickGUIBackgroundWidgetA", 100, 0, 255);
   WurstplusSetting border_widget_r = this.create("Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
   WurstplusSetting border_widget_g = this.create("Border G", "ClickGUIBorderWidgetG", 255, 0, 255);
   WurstplusSetting border_widget_b = this.create("Border B", "ClickGUIBorderWidgetB", 255, 0, 255);

   public WurstplusClickGUI() {
      super(WurstplusCategory.WURSTPLUS_CLIENT);
      this.name = "ClickGUI";
      this.tag = "GUI";
      this.description = "The main gui";
   }

   public void update() {
      ListedHack.click_gui.theme_frame_name_r = this.name_frame_r.get_value(1);
      ListedHack.click_gui.theme_frame_name_g = this.name_frame_g.get_value(1);
      ListedHack.click_gui.theme_frame_name_b = this.name_frame_b.get_value(1);
      ListedHack.click_gui.theme_frame_background_r = this.background_frame_r.get_value(1);
      ListedHack.click_gui.theme_frame_background_g = this.background_frame_g.get_value(1);
      ListedHack.click_gui.theme_frame_background_b = this.background_frame_b.get_value(1);
      ListedHack.click_gui.theme_frame_background_a = this.background_frame_a.get_value(1);
      ListedHack.click_gui.theme_frame_border_r = this.border_frame_r.get_value(1);
      ListedHack.click_gui.theme_frame_border_g = this.border_frame_g.get_value(1);
      ListedHack.click_gui.theme_frame_border_b = this.border_frame_b.get_value(1);
      ListedHack.click_gui.theme_widget_name_r = this.name_widget_r.get_value(1);
      ListedHack.click_gui.theme_widget_name_g = this.name_widget_g.get_value(1);
      ListedHack.click_gui.theme_widget_name_b = this.name_widget_b.get_value(1);
      ListedHack.click_gui.theme_widget_background_r = this.background_widget_r.get_value(1);
      ListedHack.click_gui.theme_widget_background_g = this.background_widget_g.get_value(1);
      ListedHack.click_gui.theme_widget_background_b = this.background_widget_b.get_value(1);
      ListedHack.click_gui.theme_widget_background_a = this.background_widget_a.get_value(1);
      ListedHack.click_gui.theme_widget_border_r = this.border_widget_r.get_value(1);
      ListedHack.click_gui.theme_widget_border_g = this.border_widget_g.get_value(1);
      ListedHack.click_gui.theme_widget_border_b = this.border_widget_b.get_value(1);
   }

   public void enable() {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         mc.func_147108_a(ListedHack.click_gui);
      }

   }

   public void disable() {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         mc.func_147108_a((GuiScreen)null);
      }

   }
}
