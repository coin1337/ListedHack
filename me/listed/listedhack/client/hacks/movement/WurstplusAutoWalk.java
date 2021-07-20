package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.settings.KeyBinding;

public class WurstplusAutoWalk extends WurstplusHack {
   public WurstplusAutoWalk() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Auto Walk";
      this.tag = "AutoWalk";
      this.description = "automatically walks";
   }

   public void update() {
      if (!this.nullCheck()) {
         KeyBinding.func_74510_a(mc.field_71474_y.field_74351_w.func_151463_i(), true);
      }
   }
}
