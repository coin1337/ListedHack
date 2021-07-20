package me.listed.listedhack.client.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.hud.WurstplusArmorDurabilityWarner;
import me.listed.listedhack.client.guiscreen.hud.WurstplusArmorPreview;
import me.listed.listedhack.client.guiscreen.hud.WurstplusArrayList;
import me.listed.listedhack.client.guiscreen.hud.WurstplusCompass;
import me.listed.listedhack.client.guiscreen.hud.WurstplusCoordinates;
import me.listed.listedhack.client.guiscreen.hud.WurstplusCrystalCount;
import me.listed.listedhack.client.guiscreen.hud.WurstplusDirection;
import me.listed.listedhack.client.guiscreen.hud.WurstplusEXPCount;
import me.listed.listedhack.client.guiscreen.hud.WurstplusEffectHud;
import me.listed.listedhack.client.guiscreen.hud.WurstplusEntityList;
import me.listed.listedhack.client.guiscreen.hud.WurstplusFPS;
import me.listed.listedhack.client.guiscreen.hud.WurstplusFriendList;
import me.listed.listedhack.client.guiscreen.hud.WurstplusGappleCount;
import me.listed.listedhack.client.guiscreen.hud.WurstplusInventoryPreview;
import me.listed.listedhack.client.guiscreen.hud.WurstplusInventoryXCarryPreview;
import me.listed.listedhack.client.guiscreen.hud.WurstplusLogo;
import me.listed.listedhack.client.guiscreen.hud.WurstplusPing;
import me.listed.listedhack.client.guiscreen.hud.WurstplusPlayerList;
import me.listed.listedhack.client.guiscreen.hud.WurstplusPvpHud;
import me.listed.listedhack.client.guiscreen.hud.WurstplusSpeedometer;
import me.listed.listedhack.client.guiscreen.hud.WurstplusSurroundBlocks;
import me.listed.listedhack.client.guiscreen.hud.WurstplusTPS;
import me.listed.listedhack.client.guiscreen.hud.WurstplusTime;
import me.listed.listedhack.client.guiscreen.hud.WurstplusTotemCount;
import me.listed.listedhack.client.guiscreen.hud.WurstplusUser;
import me.listed.listedhack.client.guiscreen.hud.WurstplusWatermark;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusHUDManager {
   public static ArrayList<WurstplusPinnable> array_hud = new ArrayList();

   public WurstplusHUDManager() {
      this.add_component_pinnable(new WurstplusWatermark());
      this.add_component_pinnable(new WurstplusArrayList());
      this.add_component_pinnable(new WurstplusCoordinates());
      this.add_component_pinnable(new WurstplusInventoryPreview());
      this.add_component_pinnable(new WurstplusInventoryXCarryPreview());
      this.add_component_pinnable(new WurstplusArmorPreview());
      this.add_component_pinnable(new WurstplusUser());
      this.add_component_pinnable(new WurstplusTotemCount());
      this.add_component_pinnable(new WurstplusCrystalCount());
      this.add_component_pinnable(new WurstplusEXPCount());
      this.add_component_pinnable(new WurstplusGappleCount());
      this.add_component_pinnable(new WurstplusTime());
      this.add_component_pinnable(new WurstplusLogo());
      this.add_component_pinnable(new WurstplusFPS());
      this.add_component_pinnable(new WurstplusPing());
      this.add_component_pinnable(new WurstplusSurroundBlocks());
      this.add_component_pinnable(new WurstplusFriendList());
      this.add_component_pinnable(new WurstplusArmorDurabilityWarner());
      this.add_component_pinnable(new WurstplusPvpHud());
      this.add_component_pinnable(new WurstplusCompass());
      this.add_component_pinnable(new WurstplusEffectHud());
      this.add_component_pinnable(new WurstplusSpeedometer());
      this.add_component_pinnable(new WurstplusEntityList());
      this.add_component_pinnable(new WurstplusTPS());
      this.add_component_pinnable(new WurstplusPlayerList());
      this.add_component_pinnable(new WurstplusDirection());
      array_hud.sort(Comparator.comparing(WurstplusPinnable::get_title));
   }

   public void add_component_pinnable(WurstplusPinnable module) {
      array_hud.add(module);
   }

   public ArrayList<WurstplusPinnable> get_array_huds() {
      return array_hud;
   }

   public void render() {
      Iterator var1 = this.get_array_huds().iterator();

      while(var1.hasNext()) {
         WurstplusPinnable pinnables = (WurstplusPinnable)var1.next();
         if (pinnables.is_active()) {
            pinnables.render();
         }
      }

   }

   public WurstplusPinnable get_pinnable_with_tag(String tag) {
      WurstplusPinnable pinnable_requested = null;
      Iterator var3 = this.get_array_huds().iterator();

      while(var3.hasNext()) {
         WurstplusPinnable pinnables = (WurstplusPinnable)var3.next();
         if (pinnables.get_tag().equalsIgnoreCase(tag)) {
            pinnable_requested = pinnables;
         }
      }

      return pinnable_requested;
   }
}
