package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusScaffold extends WurstplusHack {
   public WurstplusScaffold() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Scaffold";
      this.tag = "Scaffold";
      this.description = "places block";
   }

   public void update() {
      if (!this.nullCheck()) {
         Vec3d vec = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
         BlockPos blockPos = (new BlockPos(vec)).func_177977_b();
         mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, blockPos, EnumFacing.UP, new Vec3d(0.0D, 0.0D, 0.0D), EnumHand.MAIN_HAND);
      }
   }
}
