package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.Wrapper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;

public class WurstplusNoVoid extends WurstplusHack {
   WurstplusSetting height = this.create("Height", "Height", 0, 0, 256);

   public WurstplusNoVoid() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "NoVoid";
      this.tag = "NoVoid";
      this.description = "avoids getting voided";
   }

   public void update() {
      if (Wrapper.mc.field_71441_e != null) {
         if (Wrapper.mc.field_71439_g.field_70145_X || Wrapper.mc.field_71439_g.field_70163_u > (double)this.height.get_value(1)) {
            return;
         }

         RayTraceResult trace = Wrapper.mc.field_71441_e.func_147447_a(Wrapper.mc.field_71439_g.func_174791_d(), new Vec3d(Wrapper.mc.field_71439_g.field_70165_t, 0.0D, Wrapper.mc.field_71439_g.field_70161_v), false, false, false);
         if (trace != null && trace.field_72313_a == Type.BLOCK) {
            return;
         }

         Wrapper.mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
      }

   }
}
