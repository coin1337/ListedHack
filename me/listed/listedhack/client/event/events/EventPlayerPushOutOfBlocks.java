package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.MinecraftEvent;

public class EventPlayerPushOutOfBlocks extends MinecraftEvent {
   public double X;
   public double Y;
   public double Z;

   public EventPlayerPushOutOfBlocks(double p_X, double p_Y, double p_Z) {
      this.X = p_X;
      this.Y = p_Y;
      this.Z = p_Z;
   }
}
