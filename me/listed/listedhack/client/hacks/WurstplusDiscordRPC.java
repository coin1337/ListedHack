package me.listed.listedhack.client.hacks;

import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusDiscordRPC extends WurstplusHack {
   public WurstplusDiscordRPC() {
      super(WurstplusCategory.WURSTPLUS_CLIENT);
      this.name = "DiscordRPC";
      this.tag = "DiscordRPC";
      this.description = "Discord Rich Presence";
   }

   public void enable() {
      if (!this.nullCheck()) {
         WurstplusMessageUtil.send_client_message("Discord Rich Presence on");
         RPCHandler.start();
      }
   }

   public void disable() {
      WurstplusMessageUtil.send_client_message("Discord Rich Presence off");
      RPCHandler.shutdown();
   }
}
