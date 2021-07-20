package me.listed.listedhack.client.manager;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusSettingManager {
   public ArrayList<WurstplusSetting> array_setting = new ArrayList();

   public void register(WurstplusSetting setting) {
      this.array_setting.add(setting);
   }

   public ArrayList<WurstplusSetting> get_array_settings() {
      return this.array_setting;
   }

   public WurstplusSetting get_setting_with_tag(WurstplusHack module, String tag) {
      WurstplusSetting setting_requested = null;
      Iterator var4 = this.get_array_settings().iterator();

      while(var4.hasNext()) {
         WurstplusSetting settings = (WurstplusSetting)var4.next();
         if (settings.get_master().equals(module) && settings.get_tag().equalsIgnoreCase(tag)) {
            setting_requested = settings;
         }
      }

      return setting_requested;
   }

   public WurstplusSetting get_setting_with_tag(String tag, String tag_) {
      WurstplusSetting setting_requested = null;
      Iterator var4 = this.get_array_settings().iterator();

      while(var4.hasNext()) {
         WurstplusSetting settings = (WurstplusSetting)var4.next();
         if (settings.get_master().get_tag().equalsIgnoreCase(tag) && settings.get_tag().equalsIgnoreCase(tag_)) {
            setting_requested = settings;
            break;
         }
      }

      return setting_requested;
   }

   public ArrayList<WurstplusSetting> get_settings_with_hack(WurstplusHack module) {
      ArrayList<WurstplusSetting> setting_requesteds = new ArrayList();
      Iterator var3 = this.get_array_settings().iterator();

      while(var3.hasNext()) {
         WurstplusSetting settings = (WurstplusSetting)var3.next();
         if (settings.get_master().equals(module)) {
            setting_requesteds.add(settings);
         }
      }

      return setting_requesteds;
   }
}
