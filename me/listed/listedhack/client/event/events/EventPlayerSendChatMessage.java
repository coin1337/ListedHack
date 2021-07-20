package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.MinecraftEvent;

public class EventPlayerSendChatMessage extends MinecraftEvent {
   public String Message;

   public EventPlayerSendChatMessage(String p_Message) {
      this.Message = p_Message;
   }
}
