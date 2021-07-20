package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.MinecraftEvent;
import net.minecraft.potion.Potion;

public class EventPlayerIsPotionActive extends MinecraftEvent {
   public Potion potion;

   public EventPlayerIsPotionActive(Potion p_Potion) {
      this.potion = p_Potion;
   }
}
