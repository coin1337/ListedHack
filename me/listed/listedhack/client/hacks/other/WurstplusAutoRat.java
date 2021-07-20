package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusAutoRat extends WurstplusHack {
   public WurstplusAutoRat() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto Rat";
      this.tag = "AutoRat";
      this.description = "auto rats your pc :devil:";
   }

   public void enable() {
      super.enable();
      WurstplusMessageUtil.send_client_message("grabbing your future accounts... ");
      WurstplusMessageUtil.send_client_message("grabbing your future waypoints... ");
      WurstplusMessageUtil.send_client_message("grabbing your ip... ");
      WurstplusMessageUtil.send_client_message("grabbing your chrome login data file... ");
      WurstplusMessageUtil.send_client_message("grabbing your mother... ");
      WurstplusMessageUtil.send_client_message("grabbing your nudes... ");
      WurstplusMessageUtil.send_client_message("grabbing your homework folder... ");
      WurstplusMessageUtil.send_client_message("grabbing your discord tokens... ");
      WurstplusMessageUtil.send_client_message("grabbing your minecraft tokens... ");
      WurstplusMessageUtil.send_client_message("deleting your c drive... ");
      WurstplusMessageUtil.send_client_message("deleting your 10 terabyte hentai folder... ");
      this.set_disable();
   }
}
