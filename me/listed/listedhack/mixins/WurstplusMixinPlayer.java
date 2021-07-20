package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.WurstplusEventPlayerTravel;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityPlayer.class})
public class WurstplusMixinPlayer extends WurstplusMixinEntity {
   @Inject(
      method = {"travel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
      WurstplusEventPlayerTravel event_packet = new WurstplusEventPlayerTravel(strafe, vertical, forward);
      WurstplusEventBus.EVENT_BUS.post(event_packet);
      if (event_packet.isCancelled()) {
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         info.cancel();
      }

   }
}
