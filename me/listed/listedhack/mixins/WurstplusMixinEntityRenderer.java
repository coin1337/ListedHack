package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.EventRenderHurtCameraEffect;
import me.listed.listedhack.client.event.events.EventRenderUpdateLightMap;
import me.listed.listedhack.client.event.events.WurstplusEventSetupFog;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public class WurstplusMixinEntityRenderer {
   @Inject(
      method = {"setupFog"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void setupFog(int startCoords, float partialTicks, CallbackInfo p_Info) {
      WurstplusEventSetupFog event = new WurstplusEventSetupFog(startCoords, partialTicks);
      WurstplusEventBus.EVENT_BUS.post(event);
      if (!event.isCancelled()) {
         ;
      }
   }

   @Inject(
      method = {"hurtCameraEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void hurtCameraEffect(float ticks, CallbackInfo info) {
      EventRenderHurtCameraEffect l_Event = new EventRenderHurtCameraEffect(ticks);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         info.cancel();
      }

   }

   @Inject(
      method = {"updateLightmap"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void updateLightmap(float partialTicks, CallbackInfo p_Info) {
      EventRenderUpdateLightMap l_Event = new EventRenderUpdateLightMap(partialTicks);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }
}
