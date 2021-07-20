package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.MinecraftEvent;
import net.minecraft.entity.MoverType;

public class EventMove extends MinecraftEvent {
   public MoverType Type;
   public double X;
   public double Y;
   public double Z;

   public EventMove(MoverType p_Type, double p_X, double p_Y, double p_Z) {
      this.Type = p_Type;
      this.X = p_X;
      this.Y = p_Y;
      this.Z = p_Z;
   }
}
