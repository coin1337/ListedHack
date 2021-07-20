package me.listed.listedhack.client.util;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class WurstplusTabUtil {
   public static String get_player_name(NetworkPlayerInfo info) {
      String name = info.func_178854_k() != null ? info.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a(info.func_178850_i(), info.func_178845_a().getName());
      if (WurstplusFriendUtil.isFriend(name)) {
         return section_sign() + "9" + name;
      } else {
         return WurstplusEnemyUtil.isEnemy(name) ? section_sign() + "4" + name : name;
      }
   }

   public static String section_sign() {
      return "§";
   }
}
