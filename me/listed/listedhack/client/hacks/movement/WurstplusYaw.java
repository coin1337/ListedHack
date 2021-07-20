package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;

public class WurstplusYaw extends WurstplusHack {
   public WurstplusYaw() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Yaw";
      this.tag = "Yaw";
      this.description = "locks ur rotation";
   }

   public void update() {
      if (!this.nullCheck()) {
         int angle = 90;
         float yaw = mc.field_71439_g.field_70177_z;
         yaw = (float)(Math.round(yaw / (float)angle) * angle);
         mc.field_71439_g.field_70177_z = yaw;
      }
   }
}
