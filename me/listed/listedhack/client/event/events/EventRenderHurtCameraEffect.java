package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.MinecraftEvent;

public class EventRenderHurtCameraEffect extends MinecraftEvent {
   public float Ticks;

   public EventRenderHurtCameraEffect(float p_Ticks) {
      this.Ticks = p_Ticks;
   }
}
