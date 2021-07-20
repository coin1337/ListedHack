package me.listed.listedhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusPlayerList extends WurstplusPinnable {
   DecimalFormat df_health = new DecimalFormat("#.#");

   public WurstplusPlayerList() {
      super("Player List", "PlayerList", 1.0F, 0, 0);
   }

   public void render() {
      int counter = 12;
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      this.df_health.setRoundingMode(RoundingMode.HALF_UP);
      List<EntityPlayer> entity_list = this.mc.field_71441_e.field_73010_i;
      Map<String, Integer> players = new HashMap();
      Iterator var8 = entity_list.iterator();

      while(var8.hasNext()) {
         EntityPlayer player = (EntityPlayer)var8.next();
         StringBuilder sb_health = new StringBuilder();
         if (player != this.mc.field_71439_g) {
            String posString = player.field_70163_u > this.mc.field_71439_g.field_70163_u ? ChatFormatting.DARK_GREEN + "+" : (player.field_70163_u == this.mc.field_71439_g.field_70163_u ? " " : ChatFormatting.DARK_RED + "-");
            float hp_raw = player.func_110143_aJ() + player.func_110139_bj();
            String hp = this.df_health.format((double)hp_raw);
            sb_health.append('§');
            if (hp_raw >= 20.0F) {
               sb_health.append("a");
            } else if (hp_raw >= 10.0F) {
               sb_health.append("e");
            } else if (hp_raw >= 5.0F) {
               sb_health.append("6");
            } else {
               sb_health.append("c");
            }

            sb_health.append(hp);
            players.put(posString + " " + sb_health.toString() + " " + (WurstplusFriendUtil.isFriend(player.func_70005_c_()) ? ChatFormatting.GREEN : ChatFormatting.RESET) + player.func_70005_c_(), (int)this.mc.field_71439_g.func_70032_d(player));
         }
      }

      if (!players.isEmpty()) {
         Map<String, Integer> players = sortByValue(players);
         int max = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDMaxPlayers").get_value(1);
         int count = 0;
         Iterator var17 = players.entrySet().iterator();

         while(var17.hasNext()) {
            Entry<String, Integer> player = (Entry)var17.next();
            if (max < count) {
               return;
            }

            ++count;
            counter += 12;
            String line = (String)player.getKey() + " " + player.getValue();
            this.create_line(line, this.docking(1, line), counter, nl_r, nl_g, nl_b, nl_a);
         }

         this.set_width(24);
         this.set_height(counter + 2);
      }
   }

   public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
      List<Entry<K, V>> list = new LinkedList(map.entrySet());
      list.sort(Entry.comparingByValue());
      Map<K, V> result = new LinkedHashMap();
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         Entry<K, V> entry = (Entry)var3.next();
         result.put(entry.getKey(), entry.getValue());
      }

      return result;
   }
}
