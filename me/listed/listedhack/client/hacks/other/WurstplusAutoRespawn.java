package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.gui.GuiGameOver;

public class WurstplusAutoRespawn extends WurstplusHack {
   public WurstplusAutoRespawn() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto Respawn";
      this.tag = "AutoRespawn";
      this.description = "automatically respawns";
   }

   public void update() {
      if (mc.field_71439_g.field_70128_L && mc.field_71462_r instanceof GuiGameOver) {
         mc.field_71439_g.func_71004_bE();
      }

   }
}
