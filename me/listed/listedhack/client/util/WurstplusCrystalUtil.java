package me.listed.listedhack.client.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

public class WurstplusCrystalUtil {
   static final Minecraft mc = Minecraft.func_71410_x();

   public static List<BlockPos> possiblePlacePositions(float placeRange, boolean thirteen, boolean specialEntityCheck) {
      NonNullList<BlockPos> positions = NonNullList.func_191196_a();
      positions.addAll((Collection)getSphere(getPlayerPos(mc.field_71439_g), placeRange, (int)placeRange, false, true, 0).stream().filter((pos) -> {
         return canPlaceCrystal(pos, thirteen, specialEntityCheck);
      }).collect(Collectors.toList()));
      return positions;
   }

   public static BlockPos getPlayerPos(EntityPlayer player) {
      return new BlockPos(Math.floor(player.field_70165_t), Math.floor(player.field_70163_u), Math.floor(player.field_70161_v));
   }

   public static List<BlockPos> getSphere(BlockPos pos, float r, int h, boolean hollow, boolean sphere, int plus_y) {
      List<BlockPos> circleblocks = new ArrayList();
      int cx = pos.func_177958_n();
      int cy = pos.func_177956_o();
      int cz = pos.func_177952_p();

      for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
         for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
            for(int y = sphere ? cy - (int)r : cy; (float)y < (sphere ? (float)cy + r : (float)(cy + h)); ++y) {
               double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
               if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0F) * (r - 1.0F)))) {
                  BlockPos l = new BlockPos(x, y + plus_y, z);
                  circleblocks.add(l);
               }
            }
         }
      }

      return circleblocks;
   }

   public static boolean canPlaceCrystal(BlockPos blockPos, boolean thirteen, boolean specialEntityCheck) {
      BlockPos boost = blockPos.func_177982_a(0, 1, 0);
      BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
      BlockPos final_boost = blockPos.func_177982_a(0, 3, 0);

      try {
         if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
            return false;
         } else if (mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a || mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a && !thirteen) {
            return false;
         } else if (specialEntityCheck) {
            Iterator var6 = mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost)).iterator();

            Object entity;
            do {
               if (!var6.hasNext()) {
                  var6 = mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2)).iterator();

                  do {
                     if (!var6.hasNext()) {
                        var6 = mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(final_boost)).iterator();

                        do {
                           if (!var6.hasNext()) {
                              return true;
                           }

                           entity = var6.next();
                        } while(!(entity instanceof EntityEnderCrystal));

                        return false;
                     }

                     entity = var6.next();
                  } while(entity instanceof EntityEnderCrystal);

                  return false;
               }

               entity = var6.next();
            } while(entity instanceof EntityEnderCrystal);

            return false;
         } else {
            return mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
         }
      } catch (Exception var8) {
         return false;
      }
   }

   public static boolean canPlaceCrystal(BlockPos pos) {
      Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
      if (block == Blocks.field_150343_Z || block == Blocks.field_150357_h) {
         Block floor = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c();
         Block ceil = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c();
         if (floor == Blocks.field_150350_a && ceil == Blocks.field_150350_a && mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos.func_177982_a(0, 1, 0))).isEmpty() && mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos.func_177982_a(0, 2, 0))).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
      if (entity == mc.field_71439_g && mc.field_71439_g.field_71075_bZ.field_75098_d) {
         return 0.0F;
      } else {
         float doubleExplosionSize = 12.0F;
         double distancedsize = entity.func_70011_f(posX, posY, posZ) / 12.0D;
         Vec3d vec3d = new Vec3d(posX, posY, posZ);
         double blockDensity = 0.0D;

         try {
            blockDensity = (double)entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
         } catch (Exception var18) {
         }

         double v = (1.0D - distancedsize) * blockDensity;
         float damage = (float)((int)((v * v + v) / 2.0D * 7.0D * 12.0D + 1.0D));
         double finald = 1.0D;
         if (entity instanceof EntityLivingBase) {
            finald = (double)getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion(mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0F, false, true));
         }

         return (float)finald;
      }
   }

   public static float getBlastReduction(EntityLivingBase entity, float damageI, Explosion explosion) {
      float damage;
      if (entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         DamageSource ds = DamageSource.func_94539_a(explosion);
         damage = CombatRules.func_189427_a(damageI, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         int k = 0;

         try {
            k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
         } catch (Exception var8) {
         }

         float f = MathHelper.func_76131_a((float)k, 0.0F, 20.0F);
         damage *= 1.0F - f / 25.0F;
         if (entity.func_70644_a(MobEffects.field_76429_m)) {
            damage -= damage / 4.0F;
         }

         damage = Math.max(damage, 0.0F);
         return damage;
      } else {
         damage = CombatRules.func_189427_a(damageI, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         return damage;
      }
   }

   public static float getDamageMultiplied(float damage) {
      int diff = mc.field_71441_e.func_175659_aa().func_151525_a();
      return damage * (diff == 0 ? 0.0F : (diff == 2 ? 1.0F : (diff == 1 ? 0.5F : 1.5F)));
   }

   public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
      return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
   }
}
