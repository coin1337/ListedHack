package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.Wrapper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WurstplusJesus extends WurstplusHack {
   public WurstplusJesus() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Jesus";
      this.tag = "Jesus";
      this.description = "walk on water";
   }

   public void update() {
      if (Wrapper.mc.field_71441_e != null) {
         if (isInWater(Wrapper.mc.field_71439_g) && !Wrapper.mc.field_71439_g.func_70093_af()) {
            Wrapper.mc.field_71439_g.field_70181_x = 0.1D;
            if (Wrapper.mc.field_71439_g.func_184187_bx() != null) {
               Wrapper.mc.field_71439_g.func_184187_bx().field_70181_x = 0.2D;
            }
         }

         if (isAboveWater(Wrapper.getPlayer()) && !isInWater(Wrapper.getPlayer()) && !isAboveLand(Wrapper.getPlayer()) && !Wrapper.mc.field_71439_g.func_70093_af()) {
            Wrapper.mc.field_71439_g.field_70181_x = 0.0D;
            Wrapper.mc.field_71439_g.field_70122_E = true;
         }
      }

   }

   public static boolean isInWater(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         double y = entity.field_70163_u + 0.01D;

         for(int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); ++x) {
            for(int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ++z) {
               BlockPos pos = new BlockPos(x, (int)y, z);
               if (Wrapper.mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockLiquid) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static boolean isAboveWater(Entity entity) {
      double y = entity.field_70163_u - 0.03D;

      for(int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); ++x) {
         for(int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ++z) {
            BlockPos pos = new BlockPos(x, MathHelper.func_76128_c(y), z);
            if (Wrapper.getWorld().func_180495_p(pos).func_177230_c() instanceof BlockLiquid) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean isAboveLand(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         double y = entity.field_70163_u - 0.01D;

         for(int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); ++x) {
            for(int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ++z) {
               BlockPos pos = new BlockPos(x, MathHelper.func_76128_c(y), z);
               if (Wrapper.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_149730_j(Wrapper.mc.field_71441_e.func_180495_p(pos))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static enum Mode {
      SOLID,
      DOLPHIN;
   }
}
