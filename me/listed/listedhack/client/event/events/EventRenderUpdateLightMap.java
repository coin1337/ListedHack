package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.MinecraftEvent;

public class EventRenderUpdateLightMap extends MinecraftEvent {
   public float PartialTicks;

   public EventRenderUpdateLightMap(float p_PartialTicks) {
      this.PartialTicks = p_PartialTicks;
   }
}
