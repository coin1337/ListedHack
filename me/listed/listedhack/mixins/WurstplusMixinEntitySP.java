package me.listed.listedhack.mixins;

import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.EventPlayerPushOutOfBlocks;
import me.listed.listedhack.client.event.events.EventPlayerSendChatMessage;
import me.listed.listedhack.client.event.events.WurstplusEventMotionUpdate;
import me.listed.listedhack.client.event.events.WurstplusEventMove;
import me.listed.listedhack.client.event.events.WurstplusEventSwing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
public class WurstplusMixinEntitySP extends WurstplusMixinEntity {
   @Inject(
      method = {"move"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void move(MoverType type, double x, double y, double z, CallbackInfo info) {
      WurstplusEventMove event = new WurstplusEventMove(type, x, y, z);
      WurstplusEventBus.EVENT_BUS.post(event);
      if (event.isCancelled()) {
         super.func_70091_d(type, event.get_x(), event.get_y(), event.get_z());
         info.cancel();
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void OnPreUpdateWalkingPlayer(CallbackInfo p_Info) {
      WurstplusEventMotionUpdate l_Event = new WurstplusEventMotionUpdate(0);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void OnPostUpdateWalkingPlayer(CallbackInfo p_Info) {
      WurstplusEventMotionUpdate l_Event = new WurstplusEventMotionUpdate(1);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }

   @Inject(
      method = {"pushOutOfBlocks(DDD)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> callbackInfo) {
      EventPlayerPushOutOfBlocks l_Event = new EventPlayerPushOutOfBlocks(x, y, z);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         callbackInfo.setReturnValue(false);
      }

   }

   @Inject(
      method = {"swingArm"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void swingArm(EnumHand p_Hand, CallbackInfo p_Info) {
      WurstplusEventSwing l_Event = new WurstplusEventSwing(p_Hand);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }

   @Inject(
      method = {"sendChatMessage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void swingArm(String p_Message, CallbackInfo p_Info) {
      EventPlayerSendChatMessage l_Event = new EventPlayerSendChatMessage(p_Message);
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.isCancelled()) {
         p_Info.cancel();
      }

   }
}
