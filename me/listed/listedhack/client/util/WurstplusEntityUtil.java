package me.listed.listedhack.client.util;

import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusEntityUtil {
   public static final Minecraft mc = Minecraft.func_71410_x();

   public static void attackEntity(Entity entity, boolean packet, WurstplusSetting setting) {
      if (packet) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(entity));
      } else {
         mc.field_71442_b.func_78764_a(mc.field_71439_g, entity);
      }

      if (setting.in("Mainhand") || setting.in("Both")) {
         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
      }

      if (setting.in("Offhand") || setting.in("Both")) {
         mc.field_71439_g.func_184609_a(EnumHand.OFF_HAND);
      }

   }

   public static void attackEntity(Entity entity) {
      mc.field_71442_b.func_78764_a(mc.field_71439_g, entity);
      mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
   }

   public static double[] calculateLookAt(double px, double py, double pz, Entity me) {
      double dirx = me.field_70165_t - px;
      double diry = me.field_70163_u - py;
      double dirz = me.field_70161_v - pz;
      double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
      dirx /= len;
      diry /= len;
      dirz /= len;
      double pitch = Math.asin(diry);
      double yaw = Math.atan2(dirz, dirx);
      pitch = pitch * 180.0D / 3.141592653589793D;
      yaw = yaw * 180.0D / 3.141592653589793D;
      yaw += 90.0D;
      return new double[]{yaw, pitch};
   }

   public static EntityPlayer findClosestTarget(double rangeMax, EntityPlayer aimTarget) {
      rangeMax *= rangeMax;
      List<EntityPlayer> playerList = mc.field_71441_e.field_73010_i;
      EntityPlayer closestTarget = null;
      Iterator var5 = playerList.iterator();

      while(true) {
         while(true) {
            EntityPlayer entityPlayer;
            do {
               if (!var5.hasNext()) {
                  return closestTarget;
               }

               entityPlayer = (EntityPlayer)var5.next();
            } while(basicChecksEntity(entityPlayer));

            if (aimTarget == null && mc.field_71439_g.func_70068_e(entityPlayer) <= rangeMax) {
               closestTarget = entityPlayer;
            } else if (aimTarget != null && mc.field_71439_g.func_70068_e(entityPlayer) <= rangeMax && mc.field_71439_g.func_70068_e(entityPlayer) < mc.field_71439_g.func_70068_e(aimTarget)) {
               closestTarget = entityPlayer;
            }
         }
      }
   }

   public static boolean basicChecksEntity(Entity pl) {
      return pl.func_70005_c_().equals(mc.field_71439_g.func_70005_c_()) || WurstplusFriendUtil.isFriend(pl.func_70005_c_()) || pl.field_70128_L;
   }

   public static double getDirection() {
      float rotationYaw = mc.field_71439_g.field_70177_z;
      if (mc.field_71439_g.field_191988_bg < 0.0F) {
         rotationYaw += 180.0F;
      }

      float forward = 1.0F;
      if (mc.field_71439_g.field_191988_bg < 0.0F) {
         forward = -0.5F;
      } else if (mc.field_71439_g.field_191988_bg > 0.0F) {
         forward = 0.5F;
      }

      if (mc.field_71439_g.field_70702_br > 0.0F) {
         rotationYaw -= 90.0F * forward;
      }

      if (mc.field_71439_g.field_70702_br < 0.0F) {
         rotationYaw += 90.0F * forward;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static boolean isLiving(Entity e) {
      return e instanceof EntityLivingBase;
   }

   public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
      return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, 0.0D * y, (entity.field_70161_v - entity.field_70136_U) * z);
   }

   public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
      return getInterpolatedAmount(entity, ticks, ticks, ticks);
   }

   public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
      return (new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U)).func_178787_e(getInterpolatedAmount(entity, (double)ticks));
   }

   public static Vec3d getInterpolatedRenderPos(Entity entity, float ticks) {
      return getInterpolatedPos(entity, ticks).func_178786_a(mc.func_175598_ae().field_78725_b, mc.func_175598_ae().field_78726_c, mc.func_175598_ae().field_78723_d);
   }

   public static BlockPos is_cityable(EntityPlayer player, boolean end_crystal) {
      BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v);
      if (mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150343_Z) {
         if (end_crystal) {
            return pos.func_177978_c();
         }

         if (mc.field_71441_e.func_180495_p(pos.func_177978_c().func_177978_c()).func_177230_c() == Blocks.field_150350_a) {
            return pos.func_177978_c();
         }
      }

      if (mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() == Blocks.field_150343_Z) {
         if (end_crystal) {
            return pos.func_177974_f();
         }

         if (mc.field_71441_e.func_180495_p(pos.func_177974_f().func_177974_f()).func_177230_c() == Blocks.field_150350_a) {
            return pos.func_177974_f();
         }
      }

      if (mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() == Blocks.field_150343_Z) {
         if (end_crystal) {
            return pos.func_177968_d();
         }

         if (mc.field_71441_e.func_180495_p(pos.func_177968_d().func_177968_d()).func_177230_c() == Blocks.field_150350_a) {
            return pos.func_177968_d();
         }
      }

      if (mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150343_Z) {
         if (end_crystal) {
            return pos.func_177976_e();
         }

         if (mc.field_71441_e.func_180495_p(pos.func_177976_e().func_177976_e()).func_177230_c() == Blocks.field_150350_a) {
            return pos.func_177976_e();
         }
      }

      return null;
   }

   public static BlockPos getPosition(Entity pl) {
      return new BlockPos(Math.floor(pl.field_70165_t), Math.floor(pl.field_70163_u), Math.floor(pl.field_70161_v));
   }
}
