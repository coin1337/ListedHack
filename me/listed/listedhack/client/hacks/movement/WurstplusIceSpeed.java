package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.init.Blocks;

public class WurstplusIceSpeed extends WurstplusHack {
   public WurstplusIceSpeed() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "IceSpeed";
      this.tag = "IceSpeed";
      this.description = "makes you go faster on ice";
   }

   public void update() {
      if (!this.nullCheck()) {
         Blocks.field_150432_aD.setDefaultSlipperiness(0.4F);
         Blocks.field_150403_cj.setDefaultSlipperiness(0.4F);
         Blocks.field_185778_de.setDefaultSlipperiness(0.4F);
      }
   }

   public void disable() {
      Blocks.field_150432_aD.setDefaultSlipperiness(0.98F);
      Blocks.field_150403_cj.setDefaultSlipperiness(0.98F);
      Blocks.field_185778_de.setDefaultSlipperiness(0.98F);
   }
}
