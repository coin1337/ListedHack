package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;

public class WurstplusEventPlayerJump extends WurstplusEventCancellable {
   public double motion_x;
   public double motion_y;

   public WurstplusEventPlayerJump(double motion_x, double motion_y) {
      this.motion_x = motion_x;
      this.motion_y = motion_y;
   }
}
