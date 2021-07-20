package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.EventRenderArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LayerArmorBase.class})
public class MixinLayerArmorBase {
   @Inject(
      method = {"renderArmorLayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderArmorLayer(EntityLivingBase p_Entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo p_Info) {
      EventRenderArmorLayer l_Event = new EventRenderArmorLayer(p_Entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, slotIn);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }
}
