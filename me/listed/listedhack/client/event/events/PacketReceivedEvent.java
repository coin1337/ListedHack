package me.listed.listedhack.client.event.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketReceivedEvent extends Event {
   private Packet packet;

   public PacketReceivedEvent(Packet packet) {
      this.packet = packet;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public static class Post extends PacketReceivedEvent {
      public Post(Packet packet) {
         super(packet);
      }
   }
}
