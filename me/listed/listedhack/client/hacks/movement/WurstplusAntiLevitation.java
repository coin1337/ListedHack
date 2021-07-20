package me.listed.listedhack.client.hacks.movement;

import java.util.Objects;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.potion.Potion;

public class WurstplusAntiLevitation extends WurstplusHack {
   public WurstplusAntiLevitation() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Anti Levitation";
      this.tag = "AntiLevitation";
      this.description = "Avoids getting levitation";
   }

   public void update() {
      if (!this.nullCheck()) {
         if (mc.field_71439_g.func_70644_a((Potion)Objects.requireNonNull(Potion.func_180142_b("levitation")))) {
            mc.field_71439_g.func_184596_c(Potion.func_180142_b("levitation"));
         }

      }
   }
}
