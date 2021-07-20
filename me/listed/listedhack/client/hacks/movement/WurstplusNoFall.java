package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.network.play.client.CPacketPlayer;

public class WurstplusNoFall extends WurstplusHack {
   public WurstplusNoFall() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "NoFall";
      this.tag = "NoFall";
      this.description = "prevents fall damage";
      this.toggle_message = false;
   }

   protected void enable() {
      if (mc.field_71439_g != null) {
         mc.field_71439_g.field_70143_R = 0.0F;
      }

   }

   protected void disable() {
      if (mc.field_71439_g != null) {
         mc.field_71439_g.field_70143_R = 0.0F;
      }

   }

   public void update() {
      if (mc.field_71439_g.field_70143_R != 0.0F) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer(true));
      }

   }
}
