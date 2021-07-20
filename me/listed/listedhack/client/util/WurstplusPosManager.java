package me.listed.listedhack.client.util;

import net.minecraft.client.Minecraft;

public class WurstplusPosManager {
   private static double x;
   private static double y;
   private static double z;
   private static boolean onground;
   private static final Minecraft mc = Minecraft.func_71410_x();

   public static void updatePosition() {
      x = mc.field_71439_g.field_70165_t;
      y = mc.field_71439_g.field_70163_u;
      z = mc.field_71439_g.field_70161_v;
      onground = mc.field_71439_g.field_70122_E;
   }

   public static void restorePosition() {
      mc.field_71439_g.field_70165_t = x;
      mc.field_71439_g.field_70163_u = y;
      mc.field_71439_g.field_70161_v = z;
      mc.field_71439_g.field_70122_E = onground;
   }
}
