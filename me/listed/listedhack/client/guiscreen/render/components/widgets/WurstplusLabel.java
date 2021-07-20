package me.listed.listedhack.client.guiscreen.render.components.widgets;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.components.WurstplusAbstractWidget;
import me.listed.listedhack.client.guiscreen.render.components.WurstplusFrame;
import me.listed.listedhack.client.guiscreen.render.components.WurstplusModuleButton;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class WurstplusLabel extends WurstplusAbstractWidget {
   private WurstplusFrame frame;
   private WurstplusModuleButton master;
   private WurstplusSetting setting;
   private String label_name;
   private int x;
   private int y;
   private int width;
   private int height;
   private int save_y;
   private boolean can;
   private boolean info;
   private final WurstplusDraw font = new WurstplusDraw(1.0F);
   private int border_size = 0;

   public WurstplusLabel(WurstplusFrame frame, WurstplusModuleButton master, String tag, int update_postion) {
      this.frame = frame;
      this.master = master;
      this.setting = ListedHack.get_setting_manager().get_setting_with_tag(master.get_module(), tag);
      this.x = master.get_x();
      this.y = update_postion;
      this.save_y = this.y;
      this.width = master.get_width();
      this.height = this.font.get_string_height();
      this.label_name = this.setting.get_name();
      if (this.setting.get_name().equalsIgnoreCase("info")) {
         this.info = true;
      }

      this.can = true;
   }

   public WurstplusSetting get_setting() {
      return this.setting;
   }

   public void does_can(boolean value) {
      this.can = value;
   }

   public void set_x(int x) {
      this.x = x;
   }

   public void set_y(int y) {
      this.y = y;
   }

   public void set_width(int width) {
      this.width = width;
   }

   public void set_height(int height) {
      this.height = height;
   }

   public int get_x() {
      return this.x;
   }

   public int get_y() {
      return this.y;
   }

   public int get_width() {
      return this.width;
   }

   public int get_height() {
      return this.height;
   }

   public int get_save_y() {
      return this.save_y;
   }

   public boolean motion_pass(int mx, int my) {
      return this.motion(mx, my);
   }

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.get_height();
   }

   public boolean can() {
      return this.can;
   }

   public void mouse(int mx, int my, int mouse) {
      if (mouse == 0 && this.motion(mx, my) && this.master.is_open() && this.can()) {
         this.frame.does_can(false);
      }

   }

   public void render(int master_y, int separe, int absolute_x, int absolute_y) {
      this.set_width(this.master.get_width() - separe);
      String s = "me";
      this.save_y = this.y + master_y;
      int ns_r = ListedHack.click_gui.theme_widget_name_r;
      int ns_g = ListedHack.click_gui.theme_widget_name_g;
      int ns_b = ListedHack.click_gui.theme_widget_name_b;
      int ns_a = ListedHack.click_gui.theme_widget_name_a;
      int bg_r = ListedHack.click_gui.theme_widget_background_r;
      int bg_g = ListedHack.click_gui.theme_widget_background_g;
      int bg_b = ListedHack.click_gui.theme_widget_background_b;
      int bg_a = ListedHack.click_gui.theme_widget_background_a;
      int bd_r = ListedHack.click_gui.theme_widget_border_r;
      int bd_g = ListedHack.click_gui.theme_widget_border_g;
      int bd_b = ListedHack.click_gui.theme_widget_border_b;
      int bd_a = true;
      if (this.motion(absolute_x, absolute_y) && this.setting.get_master().using_widget()) {
         this.setting.get_master().event_widget();
         GL11.glPushMatrix();
         GL11.glEnable(3553);
         GL11.glEnable(3042);
         GlStateManager.func_179147_l();
         GL11.glPopMatrix();
         RenderHelp.release_gl();
      }

      if (this.info) {
         WurstplusDraw.draw_string(this.setting.get_value(s), this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
      } else {
         WurstplusDraw.draw_string(this.label_name + " \"" + this.setting.get_value(s) + "\"", this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
      }

   }
}
