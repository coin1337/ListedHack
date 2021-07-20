package me.listed.listedhack.mixins;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.EventGetBlockReachDistance;
import me.listed.listedhack.client.event.events.WurstplusEventBlock;
import me.listed.listedhack.client.event.events.WurstplusEventDamageBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerControllerMP.class})
public class WurstplusMixinPlayerControllerMP {
   @Redirect(
      method = {"onPlayerDamageBlock"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"
)
   )
   private float onPlayerDamageBlockSpeed(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
      return state.func_185903_a(player, world, pos) * (ListedHack.get_event_handler().get_tick_rate() / 20.0F);
   }

   @Inject(
      method = {"onPlayerDamageBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> info) {
      WurstplusEventDamageBlock event_packet = new WurstplusEventDamageBlock(posBlock, directionFacing);
      WurstplusEventBus.EVENT_BUS.post(event_packet);
      if (event_packet.isCancelled()) {
         info.setReturnValue(false);
         info.cancel();
      }

      WurstplusEventBlock event = new WurstplusEventBlock(4, posBlock, directionFacing);
      WurstplusEventBus.EVENT_BUS.post(event);
   }

   @Inject(
      method = {"clickBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void clickBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
      WurstplusEventBlock event = new WurstplusEventBlock(3, pos, face);
      WurstplusEventBus.EVENT_BUS.post(event);
   }

   @Inject(
      method = {"getBlockReachDistance"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getBlockReachDistance(CallbackInfoReturnable<Float> callback) {
      EventGetBlockReachDistance l_Event = new EventGetBlockReachDistance();
      WurstplusEventBus.EVENT_BUS.post(l_Event);
      if (l_Event.BlockReachDistance > 0.0F) {
         callback.setReturnValue(l_Event.BlockReachDistance);
         callback.cancel();
      }

   }
}
