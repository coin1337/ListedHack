package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;

public class WurstplusEventMotionUpdate extends WurstplusEventCancellable {
   public int stage;

   public WurstplusEventMotionUpdate(int stage) {
      this.stage = stage;
   }
}
