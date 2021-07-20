package me.listed.listedhack.mixins;

import me.listed.listedhack.ListedHack;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public abstract class WurstplusMixinItemRenderer {
   private boolean injection = true;

   @Shadow
   public abstract void func_187457_a(AbstractClientPlayer var1, float var2, float var3, EnumHand var4, float var5, ItemStack var6, float var7);

   @Inject(
      method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderItemInFirstPersonHook(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo info) {
      if (this.injection) {
         info.cancel();
         float xOffset = 0.0F;
         float yOffset = 0.0F;
         this.injection = false;
         if (hand == EnumHand.MAIN_HAND) {
            if (ListedHack.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active()) {
               xOffset = (float)ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainX").get_value(1);
               yOffset = (float)ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainY").get_value(1);
            }
         } else if (ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVOffset").get_value(true) && ListedHack.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active()) {
            xOffset = (float)ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVOffsetX").get_value(1);
            yOffset = (float)ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVOffsetY").get_value(1);
         }

         this.func_187457_a(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + xOffset, stack, p_187457_7_ + yOffset);
         this.injection = true;
      }

   }

   @Redirect(
      method = {"renderArmFirstPerson"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V",
   ordinal = 0
)
   )
   public void translateHook(float x, float y, float z) {
      GlStateManager.func_179109_b(x + (ListedHack.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active() ? (float)ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainX").get_value(1) : 0.0F), y + (ListedHack.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active() ? (float)ListedHack.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainX").get_value(1) : 0.0F), z);
   }
}
