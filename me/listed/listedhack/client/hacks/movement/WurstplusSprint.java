package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusSprint extends WurstplusHack {
   WurstplusSetting rage = this.create("Rage", "SprintRage", true);

   public WurstplusSprint() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Sprint";
      this.tag = "Sprint";
      this.description = "ZOOOOOOOOM";
   }

   public void update() {
      if (mc.field_71439_g != null) {
         if (this.rage.get_value(true) && (mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F)) {
            mc.field_71439_g.func_70031_b(true);
         } else {
            mc.field_71439_g.func_70031_b(mc.field_71439_g.field_191988_bg > 0.0F || mc.field_71439_g.field_70702_br > 0.0F);
         }

      }
   }
}
