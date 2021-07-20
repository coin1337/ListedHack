package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.network.Packet;

public class WurstplusEventPacket extends WurstplusEventCancellable {
   private final Packet packet;

   public WurstplusEventPacket(Packet packet) {
      this.packet = packet;
   }

   public Packet get_packet() {
      return this.packet;
   }

   public static class SendPacket extends WurstplusEventPacket {
      public SendPacket(Packet packet) {
         super(packet);
      }
   }

   public static class ReceivePacket extends WurstplusEventPacket {
      public ReceivePacket(Packet packet) {
         super(packet);
      }
   }
}
