package me.listed.listedhack.client.guiscreen;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusFrame;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnableButton;
import net.minecraft.client.gui.GuiScreen;

public class WurstplusHUD extends GuiScreen {
   private final WurstplusFrame frame = new WurstplusFrame("ListedHack HUD", "ListedHackHUD", 40, 40);
   private int frame_height;
   public boolean on_gui = false;
   public boolean back = false;

   public WurstplusFrame get_frame_hud() {
      return this.frame;
   }

   public boolean func_73868_f() {
      return false;
   }

   public void func_73866_w_() {
      this.on_gui = true;
      WurstplusFrame.nc_r = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUINameFrameR").get_value(1);
      WurstplusFrame.nc_g = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUINameFrameG").get_value(1);
      WurstplusFrame.nc_b = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUINameFrameB").get_value(1);
      WurstplusFrame.bd_r = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderFrameR").get_value(1);
      WurstplusFrame.bd_g = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderFrameG").get_value(1);
      WurstplusFrame.bd_b = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderFrameB").get_value(1);
      WurstplusFrame.bd_a = 0;
      WurstplusFrame.bdw_r = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderWidgetR").get_value(1);
      WurstplusFrame.bdw_g = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderWidgetG").get_value(1);
      WurstplusFrame.bdw_b = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderWidgetB").get_value(1);
      WurstplusFrame.bdw_a = 255;
      WurstplusPinnableButton.nc_r = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUINameWidgetR").get_value(1);
      WurstplusPinnableButton.nc_g = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUINameWidgetG").get_value(1);
      WurstplusPinnableButton.nc_b = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUINameWidgetB").get_value(1);
      WurstplusPinnableButton.bg_r = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBackgroundWidgetR").get_value(1);
      WurstplusPinnableButton.bg_g = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBackgroundWidgetG").get_value(1);
      WurstplusPinnableButton.bg_b = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBackgroundWidgetB").get_value(1);
      WurstplusPinnableButton.bg_a = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBackgroundWidgetA").get_value(1);
      WurstplusPinnableButton.bd_r = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderWidgetR").get_value(1);
      WurstplusPinnableButton.bd_g = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderWidgetG").get_value(1);
      WurstplusPinnableButton.bd_b = ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "ClickGUIBorderWidgetB").get_value(1);
   }

   public void func_146281_b() {
      if (this.back) {
         ListedHack.get_hack_manager().get_module_with_tag("ClickGUI").set_active(true);
         ListedHack.get_hack_manager().get_module_with_tag("HUD").set_active(false);
      } else {
         ListedHack.get_hack_manager().get_module_with_tag("HUD").set_active(false);
         ListedHack.get_hack_manager().get_module_with_tag("ClickGUI").set_active(false);
      }

      this.on_gui = false;
      ListedHack.get_config_manager().save_settings();
   }

   protected void func_73864_a(int mx, int my, int mouse) {
      this.frame.mouse(mx, my, mouse);
      if (mouse == 0 && this.frame.motion(mx, my) && this.frame.can()) {
         this.frame.set_move(true);
         this.frame.set_move_x(mx - this.frame.get_x());
         this.frame.set_move_y(my - this.frame.get_y());
      }

   }

   protected void func_146286_b(int mx, int my, int state) {
      this.frame.release(mx, my, state);
      this.frame.set_move(false);
   }

   public void func_73863_a(int mx, int my, float tick) {
      this.func_146276_q_();
      this.frame.render(mx, my, 2);
   }
}
