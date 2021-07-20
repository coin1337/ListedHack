package me.listed.listedhack.client.hacks.other;

import java.util.Timer;
import java.util.TimerTask;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusAntiAfk extends WurstplusHack {
   private Timer timer = new Timer();

   public WurstplusAntiAfk() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Anti AFK";
      this.tag = "AntiAFK";
      this.description = "avoids getting kicked for afk";
   }

   public void enable() {
      super.enable();
      if (mc.field_71439_g == null) {
         this.toggle();
      } else {
         this.timer = new Timer();
         this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
               WurstplusHack.mc.field_71439_g.func_71165_d("!pt");
               WurstplusHack.mc.field_71439_g.func_70664_aZ();
            }
         }, 0L, 120000L);
      }
   }

   public void disable() {
      super.disable();
      if (this.timer != null) {
         this.timer.cancel();
      }

   }
}
