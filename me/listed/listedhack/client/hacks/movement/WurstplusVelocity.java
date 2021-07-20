package me.listed.listedhack.client.hacks.movement;

import java.util.function.Predicate;
import me.listed.listedhack.client.event.WurstplusEventCancellable;
import me.listed.listedhack.client.event.events.WurstplusEventEntity;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class WurstplusVelocity extends WurstplusHack {
   @EventHandler
   private Listener<WurstplusEventPacket.ReceivePacket> damage = new Listener((event) -> {
      if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
         if (event.get_packet() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity knockback = (SPacketEntityVelocity)event.get_packet();
            if (knockback.func_149412_c() == mc.field_71439_g.func_145782_y()) {
               event.cancel();
               knockback.field_149415_b = (int)((float)knockback.field_149415_b * 0.0F);
               knockback.field_149416_c = (int)((float)knockback.field_149416_c * 0.0F);
               knockback.field_149414_d = (int)((float)knockback.field_149414_d * 0.0F);
            }
         } else if (event.get_packet() instanceof SPacketExplosion) {
            event.cancel();
            SPacketExplosion knockbackx = (SPacketExplosion)event.get_packet();
            knockbackx.field_149152_f *= 0.0F;
            knockbackx.field_149153_g *= 0.0F;
            knockbackx.field_149159_h *= 0.0F;
         }
      }

   }, new Predicate[0]);
   @EventHandler
   private Listener<WurstplusEventEntity.WurstplusEventColision> explosion = new Listener((event) -> {
      if (event.get_entity() == mc.field_71439_g) {
         event.cancel();
      }

   }, new Predicate[0]);

   public WurstplusVelocity() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Velocity";
      this.tag = "Velocity";
      this.description = "No kockback";
   }
}
