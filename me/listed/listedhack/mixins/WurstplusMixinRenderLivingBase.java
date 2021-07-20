package me.listed.listedhack.mixins;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.events.WurstplusEventRenderEntityModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({RenderLivingBase.class})
public abstract class WurstplusMixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {
   protected WurstplusMixinRenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
      super(renderManagerIn);
   }

   @Redirect(
      method = {"renderModel"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"
)
   )
   private void renderModelHook(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (ListedHack.get_hack_manager().get_module_with_tag("Chams").is_active()) {
         WurstplusEventRenderEntityModel event = new WurstplusEventRenderEntityModel(0, modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
         ListedHack.get_hack_manager().get_module_with_tag("Chams").on_render_model(event);
         if (event.isCancelled()) {
            return;
         }
      }

      modelBase.func_78088_a(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
   }
}
