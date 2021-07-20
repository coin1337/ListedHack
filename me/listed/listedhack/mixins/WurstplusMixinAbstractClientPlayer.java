package me.listed.listedhack.mixins;

import javax.annotation.Nullable;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.util.WurstplusCapeUtil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractClientPlayer.class})
public abstract class WurstplusMixinAbstractClientPlayer {
   @Shadow
   @Nullable
   protected abstract NetworkPlayerInfo func_175155_b();

   @Inject(
      method = {"getLocationCape"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getLocationCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
      if (ListedHack.get_hack_manager().get_module_with_tag("Capes").is_active()) {
         NetworkPlayerInfo info = this.func_175155_b();

         assert info != null;

         if (!WurstplusCapeUtil.is_uuid_valid(info.func_178845_a().getId())) {
            return;
         }

         ResourceLocation r;
         if (ListedHack.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("Listed")) {
            r = new ResourceLocation("custom/otter.png");
         } else {
            r = new ResourceLocation("custom/ocean.png");
         }

         callbackInfoReturnable.setReturnValue(r);
      }

   }
}
