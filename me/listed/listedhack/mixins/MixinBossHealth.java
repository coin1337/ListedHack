package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.EventRenderBossHealth;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiBossOverlay.class})
public class MixinBossHealth {
   @Inject(
      method = {"renderBossHealth"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderBossHealth(CallbackInfo p_Info) {
      EventRenderBossHealth l_Event = new EventRenderBossHealth();
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }
}
