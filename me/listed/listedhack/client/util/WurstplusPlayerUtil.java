package me.listed.listedhack.client.util;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class WurstplusPlayerUtil {
   private static final Minecraft mc = Minecraft.func_71410_x();

   public static BlockPos GetLocalPlayerPosFloored() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }

   public static WurstplusPlayerUtil.FacingDirection GetFacing() {
      switch(MathHelper.func_76128_c((double)(mc.field_71439_g.field_70177_z * 8.0F / 360.0F) + 0.5D) & 7) {
      case 0:
      case 1:
         return WurstplusPlayerUtil.FacingDirection.South;
      case 2:
      case 3:
         return WurstplusPlayerUtil.FacingDirection.West;
      case 4:
      case 5:
         return WurstplusPlayerUtil.FacingDirection.North;
      case 6:
      case 7:
         return WurstplusPlayerUtil.FacingDirection.East;
      default:
         return WurstplusPlayerUtil.FacingDirection.North;
      }
   }

   public double getMoveYaw() {
      float strafe = 90.0F * mc.field_71439_g.field_70702_br;
      strafe *= (float)(mc.field_71439_g.field_191988_bg != 0.0F ? (double)mc.field_71439_g.field_191988_bg * 0.5D : 1.0D);
      float yaw = mc.field_71439_g.field_70177_z - strafe;
      yaw -= mc.field_71439_g.field_191988_bg < 0.0F ? 180.0F : 0.0F;
      return Math.toRadians((double)yaw);
   }

   public static void placeBlock(BlockPos pos) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing enumFacing = var1[var3];
         if (!Minecraft.func_71410_x().field_71441_e.func_180495_p(pos.func_177972_a(enumFacing)).func_177230_c().equals(Blocks.field_150350_a) && !isIntercepted(pos)) {
            Vec3d vec = new Vec3d((double)pos.func_177958_n() + 0.5D + (double)enumFacing.func_82601_c() * 0.5D, (double)pos.func_177956_o() + 0.5D + (double)enumFacing.func_96559_d() * 0.5D, (double)pos.func_177952_p() + 0.5D + (double)enumFacing.func_82599_e() * 0.5D);
            float[] old = new float[]{Minecraft.func_71410_x().field_71439_g.field_70177_z, Minecraft.func_71410_x().field_71439_g.field_70125_A};
            Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new Rotation((float)Math.toDegrees(Math.atan2(vec.field_72449_c - Minecraft.func_71410_x().field_71439_g.field_70161_v, vec.field_72450_a - Minecraft.func_71410_x().field_71439_g.field_70165_t)) - 90.0F, (float)(-Math.toDegrees(Math.atan2(vec.field_72448_b - (Minecraft.func_71410_x().field_71439_g.field_70163_u + (double)Minecraft.func_71410_x().field_71439_g.func_70047_e()), Math.sqrt((vec.field_72450_a - Minecraft.func_71410_x().field_71439_g.field_70165_t) * (vec.field_72450_a - Minecraft.func_71410_x().field_71439_g.field_70165_t) + (vec.field_72449_c - Minecraft.func_71410_x().field_71439_g.field_70161_v) * (vec.field_72449_c - Minecraft.func_71410_x().field_71439_g.field_70161_v))))), Minecraft.func_71410_x().field_71439_g.field_70122_E));
            Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(Minecraft.func_71410_x().field_71439_g, Action.START_SNEAKING));
            Minecraft.func_71410_x().field_71442_b.func_187099_a(Minecraft.func_71410_x().field_71439_g, Minecraft.func_71410_x().field_71441_e, pos.func_177972_a(enumFacing), enumFacing.func_176734_d(), new Vec3d(pos), EnumHand.MAIN_HAND);
            Minecraft.func_71410_x().field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(Minecraft.func_71410_x().field_71439_g, Action.STOP_SNEAKING));
            Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new Rotation(old[0], old[1], Minecraft.func_71410_x().field_71439_g.field_70122_E));
            return;
         }
      }

   }

   public double getSpeed() {
      return Math.hypot(mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70179_y);
   }

   public void setSpeed(Double speed) {
      Double yaw = this.getMoveYaw();
      mc.field_71439_g.field_70159_w = -Math.sin(yaw) * speed;
      mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
   }

   public static boolean isIntercepted(BlockPos pos) {
      Iterator var1 = Minecraft.func_71410_x().field_71441_e.field_72996_f.iterator();

      Entity entity;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         entity = (Entity)var1.next();
      } while(!(new AxisAlignedBB(pos)).func_72326_a(entity.func_174813_aQ()));

      return true;
   }

   public void addSpeed(Double speed) {
      Double yaw = this.getMoveYaw();
      EntityPlayerSP player = mc.field_71439_g;
      player.field_70159_w -= Math.sin(yaw) * speed;
      EntityPlayerSP player2 = mc.field_71439_g;
      player2.field_70179_y += Math.cos(yaw) * speed;
   }

   public void setTimer(float speed) {
      mc.field_71428_T.field_194149_e = 50.0F / speed;
   }

   public void step(float height, double[] offset, boolean flag, float speed) {
      if (flag) {
         this.setTimer(speed);
      }

      for(int i = 0; i < offset.length; ++i) {
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + offset[i], mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
      }

      mc.field_71439_g.field_70138_W = height;
   }

   public static boolean IsEating() {
      return mc.field_71439_g != null && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFood && mc.field_71439_g.func_184587_cr();
   }

   public static EntityPlayer findLookingPlayer(double rangeMax) {
      ArrayList<EntityPlayer> listPlayer = new ArrayList();
      Iterator var3 = mc.field_71441_e.field_73010_i.iterator();

      while(var3.hasNext()) {
         EntityPlayer playerSin = (EntityPlayer)var3.next();
         if (!playerSin.func_70005_c_().equals(mc.field_71439_g.func_70005_c_()) && !WurstplusFriendUtil.isFriend(playerSin.func_70005_c_()) && !playerSin.field_70128_L && (double)mc.field_71439_g.func_70032_d(playerSin) <= rangeMax) {
            listPlayer.add(playerSin);
         }
      }

      EntityPlayer target = null;
      Vec3d positionEyes = mc.field_71439_g.func_174824_e(mc.func_184121_ak());
      Vec3d rotationEyes = mc.field_71439_g.func_70676_i(mc.func_184121_ak());
      int precision = 2;

      for(int i = 0; i < (int)rangeMax; ++i) {
         for(int j = precision; j > 0; --j) {
            Iterator var9 = listPlayer.iterator();

            while(var9.hasNext()) {
               Entity targetTemp = (Entity)var9.next();
               AxisAlignedBB playerBox = targetTemp.func_174813_aQ();
               double xArray = positionEyes.field_72450_a + rotationEyes.field_72450_a * (double)i + rotationEyes.field_72450_a / (double)j;
               double yArray = positionEyes.field_72448_b + rotationEyes.field_72448_b * (double)i + rotationEyes.field_72448_b / (double)j;
               double zArray = positionEyes.field_72449_c + rotationEyes.field_72449_c * (double)i + rotationEyes.field_72449_c / (double)j;
               if (playerBox.field_72337_e >= yArray && playerBox.field_72338_b <= yArray && playerBox.field_72336_d >= xArray && playerBox.field_72340_a <= xArray && playerBox.field_72334_f >= zArray && playerBox.field_72339_c <= zArray) {
                  target = (EntityPlayer)targetTemp;
               }
            }
         }
      }

      return target;
   }

   public static double[] motion(float speed) {
      float moveForward = mc.field_71439_g.field_71158_b.field_192832_b;
      float moveStrafe = mc.field_71439_g.field_71158_b.field_78902_a;
      float rotationYaw = mc.field_71439_g.field_70177_z;
      double x = (double)(moveForward * speed) * Math.cos(Math.toRadians((double)(rotationYaw + 90.0F))) + (double)(moveStrafe * speed) * Math.sin(Math.toRadians((double)(rotationYaw + 90.0F)));
      double z = (double)(moveForward * speed) * Math.sin(Math.toRadians((double)(rotationYaw + 90.0F))) - (double)(moveStrafe * speed) * Math.cos(Math.toRadians((double)(rotationYaw + 90.0F)));
      return new double[]{x, z};
   }

   public static enum FacingDirection {
      North,
      South,
      East,
      West;
   }
}
