package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.gui.ScaledResolution;

public class WurstplusEventGameOverlay extends WurstplusEventCancellable {
   public float partial_ticks;
   private ScaledResolution scaled_resolution;

   public WurstplusEventGameOverlay(float partial_ticks, ScaledResolution scaled_resolution) {
      this.partial_ticks = partial_ticks;
      this.scaled_resolution = scaled_resolution;
   }

   public ScaledResolution get_scaled_resoltion() {
      return this.scaled_resolution;
   }
}
