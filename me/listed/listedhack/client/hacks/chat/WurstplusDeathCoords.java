package me.listed.listedhack.client.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusDeathCoords extends WurstplusHack {
   public WurstplusDeathCoords() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Death Coords";
      this.tag = "DeathCoords";
      this.description = "tells you where you died";
   }

   public void update() {
      if (mc.field_71439_g.field_70128_L) {
         WurstplusMessageUtil.send_client_message("You just died" + ChatFormatting.WHITE + " at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(mc.field_71439_g.field_70165_t) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(mc.field_71439_g.field_70163_u) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(mc.field_71439_g.field_70161_v) + ChatFormatting.GRAY + "]");
         this.set_disable();
      }

   }
}
