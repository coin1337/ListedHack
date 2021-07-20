package me.listed.listedhack.client.event;

import me.zero.alpine.fork.event.type.Cancellable;
import net.minecraft.client.Minecraft;

public class MinecraftEvent extends Cancellable {
   private static final Minecraft mc = Minecraft.func_71410_x();
   private MinecraftEvent.Era era;
   private final float partialTicks;

   public MinecraftEvent() {
      this.era = MinecraftEvent.Era.PRE;
      this.partialTicks = mc.func_184121_ak();
   }

   public MinecraftEvent(MinecraftEvent.Era p_Era) {
      this.era = MinecraftEvent.Era.PRE;
      this.partialTicks = mc.func_184121_ak();
      this.era = p_Era;
   }

   public MinecraftEvent.Era getEra() {
      return this.era;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public static enum Era {
      PRE,
      PERI,
      POST;
   }
}
