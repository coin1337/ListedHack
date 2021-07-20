package me.listed.listedhack.client.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class WurstplusBreakUtil {
   private static final Minecraft mc = Minecraft.func_71410_x();
   private static BlockPos current_block = null;
   private static boolean is_mining = false;

   public static void set_current_block(BlockPos pos) {
      current_block = pos;
   }

   private static boolean is_done(IBlockState state) {
      return state.func_177230_c() == Blocks.field_150357_h || state.func_177230_c() == Blocks.field_150350_a || state.func_177230_c() instanceof BlockLiquid;
   }

   public static boolean update(float range, boolean ray_trace) {
      if (current_block == null) {
         return false;
      } else {
         IBlockState state = mc.field_71441_e.func_180495_p(current_block);
         if (!is_done(state) && !(mc.field_71439_g.func_174818_b(current_block) > (double)(range * range))) {
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            EnumFacing facing = EnumFacing.UP;
            if (ray_trace) {
               RayTraceResult r = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)current_block.func_177958_n() + 0.5D, (double)current_block.func_177956_o() - 0.5D, (double)current_block.func_177952_p() + 0.5D));
               if (r != null && r.field_178784_b != null) {
                  facing = r.field_178784_b;
               }
            }

            if (!is_mining) {
               is_mining = true;
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, current_block, facing));
            } else {
               mc.field_71442_b.func_180512_c(current_block, facing);
            }

            return true;
         } else {
            current_block = null;
            return false;
         }
      }
   }
}
