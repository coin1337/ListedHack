package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.util.EnumHand;

public class WurstplusEventSwing extends WurstplusEventCancellable {
   public EnumHand hand;

   public WurstplusEventSwing(EnumHand hand) {
      this.hand = hand;
   }
}
