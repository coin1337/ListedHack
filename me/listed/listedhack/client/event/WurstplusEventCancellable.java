package me.listed.listedhack.client.event;

import me.zero.alpine.fork.event.type.Cancellable;
import net.minecraft.client.Minecraft;

public class WurstplusEventCancellable extends Cancellable {
   private final WurstplusEventCancellable.Era era_switch;
   private final float partial_ticks;

   public WurstplusEventCancellable() {
      this.era_switch = WurstplusEventCancellable.Era.EVENT_PRE;
      this.partial_ticks = Minecraft.func_71410_x().func_184121_ak();
   }

   public WurstplusEventCancellable.Era get_era() {
      return this.era_switch;
   }

   public float get_partial_ticks() {
      return this.partial_ticks;
   }

   public static enum Era {
      EVENT_PRE,
      EVENT_PERI,
      EVENT_POST;
   }
}
