package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.EventRenderEnchantingTable;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityEnchantmentTableRenderer.class})
public class MixinEnchantmentTable {
   @Inject(
      method = {"render"},
      at = {@At("INVOKE")},
      cancellable = true
   )
   private void renderEnchantingTableBook(TileEntityEnchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
      EventRenderEnchantingTable l_Event = new EventRenderEnchantingTable();
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         ci.cancel();
      }

   }
}
