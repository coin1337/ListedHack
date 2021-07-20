package me.listed.listedhack.client.hacks.chat;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusAutoCope extends WurstplusHack {
   int diedTime = 0;

   public WurstplusAutoCope() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Auto Cope";
      this.tag = "AutoCope";
      this.description = "auto matically copes";
   }

   public void update() {
      if (this.diedTime > 0) {
         --this.diedTime;
      }

      if (mc.field_71439_g.field_70128_L) {
         this.diedTime = 500;
      }

      if (!mc.field_71439_g.field_70128_L && this.diedTime > 0) {
         int randomNum = (int)(Math.random() * 50.0D + 1.0D);
         if (randomNum == 1) {
            mc.field_71439_g.func_71165_d("I was configing");
         }

         if (randomNum == 2) {
            mc.field_71439_g.func_71165_d("I was desycned");
         }

         if (randomNum == 3) {
            mc.field_71439_g.func_71165_d("Got token logged by listedhack mid fight");
         }

         if (randomNum == 4) {
            mc.field_71439_g.func_71165_d("I was jacking off");
         }

         if (randomNum == 5) {
            mc.field_71439_g.func_71165_d("My fingers are greasy");
         }

         if (randomNum == 6) {
            mc.field_71439_g.func_71165_d("I was tabbed out");
         }

         this.diedTime = 0;
      }

   }
}
