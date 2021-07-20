package me.listed.listedhack.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusRotationUtil {
   private static final Minecraft mc = Minecraft.func_71410_x();
   private static float yaw;
   private static float pitch;

   public static void updateRotations() {
      yaw = mc.field_71439_g.field_70177_z;
      pitch = mc.field_71439_g.field_70125_A;
   }

   public static void restoreRotations() {
      mc.field_71439_g.field_70177_z = yaw;
      mc.field_71439_g.field_70759_as = yaw;
      mc.field_71439_g.field_70125_A = pitch;
   }

   public static void setPlayerRotations(float yaw, float pitch) {
      mc.field_71439_g.field_70177_z = yaw;
      mc.field_71439_g.field_70759_as = yaw;
      mc.field_71439_g.field_70125_A = pitch;
   }

   public void setPlayerYaw(float yaw) {
      mc.field_71439_g.field_70177_z = yaw;
      mc.field_71439_g.field_70759_as = yaw;
   }

   public void lookAtPos(BlockPos pos) {
      float[] angle = WurstplusMathUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d((double)((float)pos.func_177958_n() + 0.5F), (double)((float)pos.func_177956_o() + 0.5F), (double)((float)pos.func_177952_p() + 0.5F)));
      setPlayerRotations(angle[0], angle[1]);
   }

   public void lookAtVec3d(Vec3d vec3d) {
      float[] angle = WurstplusMathUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c));
      setPlayerRotations(angle[0], angle[1]);
   }

   public void lookAtVec3d(double x, double y, double z) {
      Vec3d vec3d = new Vec3d(x, y, z);
      this.lookAtVec3d(vec3d);
   }

   public void lookAtEntity(Entity entity) {
      float[] angle = WurstplusMathUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), entity.func_174824_e(mc.func_184121_ak()));
      setPlayerRotations(angle[0], angle[1]);
   }

   public void setPlayerPitch(float pitch) {
      mc.field_71439_g.field_70125_A = pitch;
   }

   public float getYaw() {
      return yaw;
   }

   public void setYaw(float yaw) {
      WurstplusRotationUtil.yaw = yaw;
   }

   public float getPitch() {
      return pitch;
   }

   public void setPitch(float pitch) {
      WurstplusRotationUtil.pitch = pitch;
   }

   public int getDirection4D() {
      return this.getDirection4D();
   }

   public String getDirection4D(boolean northRed) {
      return this.getDirection4D(northRed);
   }
}
