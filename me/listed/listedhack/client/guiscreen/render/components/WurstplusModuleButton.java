package me.listed.listedhack.client.guiscreen.render.components;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.components.widgets.WurstplusButton;
import me.listed.listedhack.client.guiscreen.render.components.widgets.WurstplusButtonBind;
import me.listed.listedhack.client.guiscreen.render.components.widgets.WurstplusCombobox;
import me.listed.listedhack.client.guiscreen.render.components.widgets.WurstplusLabel;
import me.listed.listedhack.client.guiscreen.render.components.widgets.WurstplusSlider;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusModuleButton {
   private WurstplusHack module;
   private WurstplusFrame master;
   private ArrayList<WurstplusAbstractWidget> widget;
   private String module_name;
   private boolean opened;
   private int x;
   private int y;
   private int width;
   private int height;
   private int opened_height;
   private int save_y;
   private WurstplusDraw font = new WurstplusDraw(1.0F);
   private int border_a = 200;
   private int border_size = 1;
   private int master_height_cache;
   public int settings_height;
   private int count;

   public WurstplusModuleButton(WurstplusHack module, WurstplusFrame master) {
      this.module = module;
      this.master = master;
      this.widget = new ArrayList();
      this.module_name = module.get_name();
      this.x = 0;
      this.y = 0;
      this.width = this.font.get_string_width(module.get_name()) + 5;
      this.height = this.font.get_string_height();
      this.opened_height = this.height;
      this.save_y = 0;
      this.opened = false;
      this.master_height_cache = master.get_height();
      this.settings_height = this.y + 10;
      this.count = 0;
      Iterator var3 = ListedHack.get_setting_manager().get_settings_with_hack(module).iterator();

      while(true) {
         WurstplusSetting settings;
         do {
            if (!var3.hasNext()) {
               int size = ListedHack.get_setting_manager().get_settings_with_hack(module).size();
               if (this.count >= size) {
                  this.widget.add(new WurstplusButtonBind(master, this, "bind", this.settings_height));
                  this.settings_height += 10;
               }

               return;
            }

            settings = (WurstplusSetting)var3.next();
            if (settings.get_type().equals("button")) {
               this.widget.add(new WurstplusButton(master, this, settings.get_tag(), this.settings_height));
               this.settings_height += 10;
               ++this.count;
            }

            if (settings.get_type().equals("combobox")) {
               this.widget.add(new WurstplusCombobox(master, this, settings.get_tag(), this.settings_height));
               this.settings_height += 10;
               ++this.count;
            }

            if (settings.get_type().equals("label")) {
               this.widget.add(new WurstplusLabel(master, this, settings.get_tag(), this.settings_height));
               this.settings_height += 10;
               ++this.count;
            }
         } while(!settings.get_type().equals("doubleslider") && !settings.get_type().equals("integerslider"));

         this.widget.add(new WurstplusSlider(master, this, settings.get_tag(), this.settings_height));
         this.settings_height += 10;
         ++this.count;
      }
   }

   public WurstplusHack get_module() {
      return this.module;
   }

   public WurstplusFrame get_master() {
      return this.master;
   }

   public void set_pressed(boolean value) {
      this.module.set_active(value);
   }

   public void set_width(int width) {
      this.width = width;
   }

   public void set_height(int height) {
      this.height = height;
   }

   public void set_x(int x) {
      this.x = x;
   }

   public void set_y(int y) {
      this.y = y;
   }

   public void set_open(boolean value) {
      this.opened = value;
   }

   public boolean get_state() {
      return this.module.is_active();
   }

   public int get_settings_height() {
      return this.settings_height;
   }

   public int get_cache_height() {
      return this.master_height_cache;
   }

   public int get_width() {
      return this.width;
   }

   public int get_height() {
      return this.height;
   }

   public int get_x() {
      return this.x;
   }

   public int get_y() {
      return this.y;
   }

   public int get_save_y() {
      return this.save_y;
   }

   public boolean is_open() {
      return this.opened;
   }

   public boolean is_binding() {
      boolean value_requested = false;
      Iterator var2 = this.widget.iterator();

      while(var2.hasNext()) {
         WurstplusAbstractWidget widgets = (WurstplusAbstractWidget)var2.next();
         if (widgets.is_binding()) {
            value_requested = true;
         }
      }

      return value_requested;
   }

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.get_height();
   }

   public void does_widgets_can(boolean can) {
      Iterator var2 = this.widget.iterator();

      while(var2.hasNext()) {
         WurstplusAbstractWidget widgets = (WurstplusAbstractWidget)var2.next();
         widgets.does_can(can);
      }

   }

   public void bind(char char_, int key) {
      Iterator var3 = this.widget.iterator();

      while(var3.hasNext()) {
         WurstplusAbstractWidget widgets = (WurstplusAbstractWidget)var3.next();
         widgets.bind(char_, key);
      }

   }

   public void mouse(int mx, int my, int mouse) {
      Iterator var4 = this.widget.iterator();

      while(var4.hasNext()) {
         WurstplusAbstractWidget widgets = (WurstplusAbstractWidget)var4.next();
         widgets.mouse(mx, my, mouse);
      }

      if (mouse == 0 && this.motion(mx, my)) {
         this.master.does_can(false);
         this.set_pressed(!this.get_state());
      }

      if (mouse == 1 && this.motion(mx, my)) {
         this.master.does_can(false);
         this.set_open(!this.is_open());
         this.master.refresh_frame(this, 0);
      }

   }

   public void button_release(int mx, int my, int mouse) {
      Iterator var4 = this.widget.iterator();

      while(var4.hasNext()) {
         WurstplusAbstractWidget widgets = (WurstplusAbstractWidget)var4.next();
         widgets.release(mx, my, mouse);
      }

      this.master.does_can(true);
   }

   public void render(int mx, int my, int separe) {
      this.set_width(this.master.get_width() - separe);
      this.save_y = this.y + this.master.get_y() - 10;
      int nm_r = ListedHack.click_gui.theme_widget_name_r;
      int nm_g = ListedHack.click_gui.theme_widget_name_g;
      int nm_b = ListedHack.click_gui.theme_widget_name_b;
      int nm_a = ListedHack.click_gui.theme_widget_name_a;
      int bg_r = ListedHack.click_gui.theme_widget_background_r;
      int bg_g = ListedHack.click_gui.theme_widget_background_g;
      int bg_b = ListedHack.click_gui.theme_widget_background_b;
      int bg_a = ListedHack.click_gui.theme_widget_background_a;
      int bd_r = ListedHack.click_gui.theme_widget_border_r;
      int bd_g = ListedHack.click_gui.theme_widget_border_g;
      int bd_b = ListedHack.click_gui.theme_widget_border_b;
      WurstplusDraw var10000;
      if (this.module.is_active()) {
         WurstplusDraw.draw_rect(this.x, this.save_y, this.x + this.width - separe, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
         var10000 = this.font;
         WurstplusDraw.draw_string(this.module_name, this.x + separe, this.save_y, nm_r, nm_g, nm_b, nm_a);
      } else {
         var10000 = this.font;
         WurstplusDraw.draw_string(this.module_name, this.x + separe, this.save_y, nm_r, nm_g, nm_b, nm_a);
      }

      Iterator var15 = this.widget.iterator();

      while(var15.hasNext()) {
         WurstplusAbstractWidget widgets = (WurstplusAbstractWidget)var15.next();
         widgets.set_x(this.get_x());
         boolean is_passing_in_widget = this.opened ? widgets.motion_pass(mx, my) : false;
         if (this.motion(mx, my) || is_passing_in_widget) {
            WurstplusDraw.draw_rect(this.master.get_x() - 1, this.save_y, this.master.get_width() + 1, this.opened_height, bd_r, bd_g, bd_b, this.border_a, this.border_size, "right-left");
         }

         if (this.opened) {
            this.opened_height = this.height + this.settings_height - 10;
            widgets.render(this.get_save_y(), separe, mx, my);
         } else {
            this.opened_height = this.height;
         }
      }

   }
}
