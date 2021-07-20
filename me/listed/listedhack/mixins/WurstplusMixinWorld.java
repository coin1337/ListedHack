package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.WurstplusEventEntityRemoved;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({World.class})
public class WurstplusMixinWorld {
   @Inject(
      method = {"onEntityRemoved"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onEntityRemoved(Entity event_packet, CallbackInfo p_Info) {
      WurstplusEventEntityRemoved l_Event = new WurstplusEventEntityRemoved(event_packet);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
   }
}
