package me.listed.listedhack.client.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WurstplusBlockInteractHelper {
   public static final List<Block> blackList;
   public static final List<Block> shulkerList;
   private static final Minecraft mc;

   public static void placeBlockScaffold(BlockPos pos, boolean rotate) {
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumFacing side = var2[var4];
         BlockPos neighbor = pos.func_177972_a(side);
         EnumFacing side2 = side.func_176734_d();
         if (canBeClicked(neighbor)) {
            Vec3d hitVec = (new Vec3d(neighbor)).func_178787_e(new Vec3d(0.5D, 0.5D, 0.5D)).func_178787_e((new Vec3d(side2.func_176730_m())).func_186678_a(0.5D));
            if (rotate) {
               faceVectorPacketInstant(hitVec);
            }

            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
            mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, side, hitVec, EnumHand.MAIN_HAND);
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            mc.field_71467_ac = 0;
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
            return;
         }
      }

   }

   public static Block getBlock(double x, double y, double z) {
      return mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
   }

   public static void placeBlock(BlockPos pos, boolean rotate) {
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumFacing side = var2[var4];
         BlockPos neighbor = pos.func_177972_a(side);
         EnumFacing side2 = side.func_176734_d();
         if (canBeClicked(neighbor)) {
            Vec3d hitVec = (new Vec3d(neighbor)).func_178787_e(new Vec3d(0.5D, 0.5D, 0.5D)).func_178787_e((new Vec3d(side2.func_176730_m())).func_186678_a(0.5D));
            if (rotate) {
               faceVectorPacketInstant(hitVec);
            }

            mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, side, hitVec, EnumHand.MAIN_HAND);
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            mc.field_71467_ac = 0;
            return;
         }
      }

   }

   public static WurstplusBlockInteractHelper.PlaceResult place(BlockPos pos, float p_Distance, boolean p_Rotate, boolean p_UseSlabRule) {
      IBlockState l_State = mc.field_71441_e.func_180495_p(pos);
      boolean l_Replaceable = l_State.func_185904_a().func_76222_j();
      boolean l_IsSlabAtBlock = l_State.func_177230_c() instanceof BlockSlab;
      if (!l_Replaceable && !l_IsSlabAtBlock) {
         return WurstplusBlockInteractHelper.PlaceResult.NotReplaceable;
      } else if (!checkForNeighbours(pos)) {
         return WurstplusBlockInteractHelper.PlaceResult.Neighbors;
      } else if (p_UseSlabRule && l_IsSlabAtBlock && !l_State.func_185917_h()) {
         return WurstplusBlockInteractHelper.PlaceResult.CantPlace;
      } else {
         Vec3d eyesPos = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
         EnumFacing[] var8 = EnumFacing.values();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            EnumFacing side = var8[var10];
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.func_176734_d();
            if (mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbor), false)) {
               Vec3d hitVec = (new Vec3d(neighbor)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(side2.func_176730_m())).func_186678_a(0.5D));
               if (eyesPos.func_72438_d(hitVec) <= (double)p_Distance) {
                  Block neighborPos = mc.field_71441_e.func_180495_p(neighbor).func_177230_c();
                  boolean activated = neighborPos.func_180639_a(mc.field_71441_e, pos, mc.field_71441_e.func_180495_p(pos), mc.field_71439_g, EnumHand.MAIN_HAND, side, 0.0F, 0.0F, 0.0F);
                  if (blackList.contains(neighborPos) || shulkerList.contains(neighborPos) || activated) {
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                  }

                  if (p_Rotate) {
                     faceVectorPacketInstant(hitVec);
                  }

                  EnumActionResult l_Result2 = mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                  if (l_Result2 != EnumActionResult.FAIL) {
                     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                     if (activated) {
                        mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                     }

                     return WurstplusBlockInteractHelper.PlaceResult.Placed;
                  }
               }
            }
         }

         return WurstplusBlockInteractHelper.PlaceResult.CantPlace;
      }
   }

   public static WurstplusBlockInteractHelper.ValidResult valid(BlockPos pos) {
      if (!mc.field_71441_e.func_72855_b(new AxisAlignedBB(pos))) {
         return WurstplusBlockInteractHelper.ValidResult.NoEntityCollision;
      } else if (!checkForNeighbours(pos)) {
         return WurstplusBlockInteractHelper.ValidResult.NoNeighbors;
      } else {
         IBlockState l_State = mc.field_71441_e.func_180495_p(pos);
         if (l_State.func_177230_c() != Blocks.field_150350_a) {
            return WurstplusBlockInteractHelper.ValidResult.AlreadyBlockThere;
         } else {
            BlockPos[] l_Blocks = new BlockPos[]{pos.func_177978_c(), pos.func_177968_d(), pos.func_177974_f(), pos.func_177976_e(), pos.func_177984_a(), pos.func_177977_b()};
            BlockPos[] var3 = l_Blocks;
            int var4 = l_Blocks.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               BlockPos l_Pos = var3[var5];
               IBlockState l_State2 = mc.field_71441_e.func_180495_p(l_Pos);
               if (l_State2.func_177230_c() != Blocks.field_150350_a) {
                  EnumFacing[] var8 = EnumFacing.values();
                  int var9 = var8.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     EnumFacing side = var8[var10];
                     BlockPos neighbor = pos.func_177972_a(side);
                     if (mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbor), false)) {
                        return WurstplusBlockInteractHelper.ValidResult.Ok;
                     }
                  }
               }
            }

            return WurstplusBlockInteractHelper.ValidResult.NoNeighbors;
         }
      }
   }

   public static float[] getLegitRotations(Vec3d vec) {
      Vec3d eyesPos = getEyesPos();
      double diffX = vec.field_72450_a - eyesPos.field_72450_a;
      double diffY = vec.field_72448_b - eyesPos.field_72448_b;
      double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
      double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
      return new float[]{mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A)};
   }

   private static Vec3d getEyesPos() {
      return new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
   }

   public static void faceVectorPacketInstant(Vec3d vec) {
      float[] rotations = getLegitRotations(vec);
      mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(rotations[0], rotations[1], mc.field_71439_g.field_70122_E));
   }

   public static boolean canBeClicked(BlockPos pos) {
      return getBlock(pos).func_176209_a(getState(pos), false);
   }

   public static Block getBlock(BlockPos pos) {
      return getState(pos).func_177230_c();
   }

   public static void faceVectorPacketInstant(Vec3d vec, Boolean roundAngles) {
      float[] rotations = getNeededRotations2(vec);
      mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(rotations[0], roundAngles ? (float)MathHelper.func_180184_b((int)rotations[1], 360) : rotations[1], mc.field_71439_g.field_70122_E));
   }

   private static float[] getNeededRotations2(Vec3d vec) {
      Vec3d eyesPos = getEyesPos();
      double diffX = vec.field_72450_a - eyesPos.field_72450_a;
      double diffY = vec.field_72448_b - eyesPos.field_72448_b;
      double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
      double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
      return new float[]{mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A)};
   }

   private static IBlockState getState(BlockPos pos) {
      return mc.field_71441_e.func_180495_p(pos);
   }

   public static boolean checkForNeighbours(BlockPos blockPos) {
      if (!hasNeighbour(blockPos)) {
         EnumFacing[] var1 = EnumFacing.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            EnumFacing side = var1[var3];
            BlockPos neighbour = blockPos.func_177972_a(side);
            if (hasNeighbour(neighbour)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static EnumFacing getPlaceableSide(BlockPos pos) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing side = var1[var3];
         BlockPos neighbour = pos.func_177972_a(side);
         if (mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbour), false)) {
            IBlockState blockState = mc.field_71441_e.func_180495_p(neighbour);
            if (!blockState.func_185904_a().func_76222_j()) {
               return side;
            }
         }
      }

      return null;
   }

   private static boolean hasNeighbour(BlockPos blockPos) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing side = var1[var3];
         BlockPos neighbour = blockPos.func_177972_a(side);
         if (!mc.field_71441_e.func_180495_p(neighbour).func_185904_a().func_76222_j()) {
            return true;
         }
      }

      return false;
   }

   public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
      ArrayList<BlockPos> circleblocks = new ArrayList();
      int cx = loc.func_177958_n();
      int cy = loc.func_177956_o();
      int cz = loc.func_177952_p();

      for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
         for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
            int y = sphere ? cy - (int)r : cy;

            while(true) {
               float f = sphere ? (float)cy + r : (float)(cy + h);
               if (!((float)y < f)) {
                  break;
               }

               double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
               if (dist < (double)(r * r) && (!hollow || !(dist < (double)((r - 1.0F) * (r - 1.0F))))) {
                  BlockPos l = new BlockPos(x, y + plus_y, z);
                  circleblocks.add(l);
               }

               ++y;
            }
         }
      }

      return circleblocks;
   }

   static {
      blackList = Arrays.asList(Blocks.field_150477_bB, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z);
      shulkerList = Arrays.asList(Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA);
      mc = Minecraft.func_71410_x();
   }

   public static enum PlaceResult {
      NotReplaceable,
      Neighbors,
      CantPlace,
      Placed;
   }

   public static enum ValidResult {
      NoEntityCollision,
      AlreadyBlockThere,
      NoNeighbors,
      Ok;
   }
}
