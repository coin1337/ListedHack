package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusAutoCrash extends WurstplusHack {
   public WurstplusAutoCrash() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto Crash";
      this.tag = "AutoCrash";
      this.description = "Crashes your game";
   }

   public void update() {
      ListedHack.get_hack_manager().get_module_with_tag("Poop").set_active(true);
      this.set_disable();
   }
}
