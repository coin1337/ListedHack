package me.listed.listedhack.client.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.event.events.WurstplusEventRenderEntityModel;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.Listenable;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public abstract class WurstplusHack implements Listenable {
   public WurstplusCategory category;
   public String name = "";
   public String tag = "";
   public String description = "";
   public int bind = -1;
   public boolean state_module;
   public boolean toggle_message = true;
   public boolean widget_usage = false;
   public static final Minecraft mc = Minecraft.func_71410_x();

   public WurstplusHack(WurstplusCategory category) {
      this.category = category;
   }

   public void set_bind(int key) {
      this.bind = key;
   }

   public boolean nullCheck() {
      return mc.field_71439_g == null || mc.field_71441_e == null;
   }

   public void set_if_can_send_message_toggle(boolean value) {
      this.toggle_message = value;
   }

   public boolean is_active() {
      return this.state_module;
   }

   public boolean using_widget() {
      return this.widget_usage;
   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }

   public String get_description() {
      return this.description;
   }

   public int get_bind(int type) {
      return this.bind;
   }

   public String get_bind(String type) {
      String converted_bind = "null";
      if (this.get_bind(0) < 0) {
         converted_bind = "NONE";
      }

      if (!converted_bind.equals("NONE")) {
         String key = Keyboard.getKeyName(this.get_bind(0));
         converted_bind = Character.toUpperCase(key.charAt(0)) + (key.length() != 1 ? key.substring(1).toLowerCase() : "");
      } else {
         converted_bind = "None";
      }

      return converted_bind;
   }

   public WurstplusCategory get_category() {
      return this.category;
   }

   public boolean can_send_message_when_toggle() {
      return this.toggle_message;
   }

   public void set_disable() {
      this.state_module = false;
      this.disable();
      WurstplusEventBus.EVENT_BUS.unsubscribe((Listenable)this);
   }

   public void set_enable() {
      this.state_module = true;
      this.enable();
      WurstplusEventBus.EVENT_BUS.subscribe((Listenable)this);
   }

   public void set_active(boolean value) {
      if (this.state_module != value) {
         if (value) {
            this.set_enable();
         } else {
            this.set_disable();
         }
      }

      if (!this.tag.equals("GUI") && !this.tag.equals("HUD") && this.toggle_message) {
         WurstplusMessageUtil.toggle_message(this);
      }

   }

   public void toggle() {
      this.set_active(!this.is_active());
   }

   protected WurstplusSetting create(String name, String tag, int value, int min, int max) {
      ListedHack.get_setting_manager().register(new WurstplusSetting(this, name, tag, value, min, max));
      return ListedHack.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected WurstplusSetting create(String name, String tag, double value, double min, double max) {
      ListedHack.get_setting_manager().register(new WurstplusSetting(this, name, tag, value, min, max));
      return ListedHack.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected WurstplusSetting create(String name, String tag, boolean value) {
      ListedHack.get_setting_manager().register(new WurstplusSetting(this, name, tag, value));
      return ListedHack.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected WurstplusSetting create(String name, String tag, String value) {
      ListedHack.get_setting_manager().register(new WurstplusSetting(this, name, tag, value));
      return ListedHack.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected WurstplusSetting create(String name, String tag, String value, List<String> values) {
      ListedHack.get_setting_manager().register(new WurstplusSetting(this, name, tag, values, value));
      return ListedHack.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected List<String> combobox(String... item) {
      return new ArrayList(Arrays.asList(item));
   }

   public void render(WurstplusEventRender event) {
   }

   public void render() {
   }

   public void update() {
   }

   public void event_widget() {
   }

   protected void disable() {
   }

   protected void enable() {
   }

   public String array_detail() {
      return null;
   }

   public void on_render_model(WurstplusEventRenderEntityModel event) {
   }
}
