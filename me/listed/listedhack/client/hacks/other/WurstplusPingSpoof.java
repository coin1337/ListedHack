package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusPingSpoof extends WurstplusHack {
   public WurstplusPingSpoof() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Ping Spoof";
      this.tag = "Ping Spoof";
      this.description = "get bad ping";
   }

   public void enable() {
      super.enable();
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      WurstplusMessageUtil.send_client_message("sending 100 packets to your router... ");
      this.set_disable();
   }
}
