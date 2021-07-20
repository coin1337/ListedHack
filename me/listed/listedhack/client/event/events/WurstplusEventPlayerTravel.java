package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;

public class WurstplusEventPlayerTravel extends WurstplusEventCancellable {
   public float Strafe;
   public float Vertical;
   public float Forward;

   public WurstplusEventPlayerTravel(float p_Strafe, float p_Vertical, float p_Forward) {
      this.Strafe = p_Strafe;
      this.Vertical = p_Vertical;
      this.Forward = p_Forward;
   }
}
