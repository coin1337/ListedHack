package me.listed.listedhack.mixins;

import me.listed.listedhack.ListedHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class WurstplusMixinMinecraft {
   @Shadow
   public EntityPlayerSP field_71439_g;
   @Shadow
   public PlayerControllerMP field_71442_b;
   private boolean handActive = false;
   private boolean isHittingBlock = false;

   @Inject(
      method = {"shutdown"},
      at = {@At("HEAD")}
   )
   private void shutdown(CallbackInfo info) {
      ListedHack.get_config_manager().save_settings();
   }
}
