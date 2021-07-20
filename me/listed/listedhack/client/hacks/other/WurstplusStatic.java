package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.Wrapper;

public class WurstplusStatic extends WurstplusHack {
   public WurstplusStatic() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Static";
      this.tag = "Static";
      this.description = "idk";
   }

   public void update() {
      if (Wrapper.getPlayer().field_70160_al) {
         Wrapper.getPlayer().field_70181_x = 0.0D;
      }

   }
}
