package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;

public class WurstplusEventSetupFog extends WurstplusEventCancellable {
   public int start_coords;
   public float partial_ticks;

   public WurstplusEventSetupFog(int coords, float ticks) {
      this.start_coords = coords;
      this.partial_ticks = ticks;
   }
}
