package me.listed.listedhack.client.util;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;

public class Wrapper {
   public static final Minecraft mc = Minecraft.func_71410_x();

   @Nullable
   public static EntityPlayerSP getPlayer() {
      return mc.field_71439_g;
   }

   @Nullable
   public static WorldClient getWorld() {
      return mc.field_71441_e;
   }

   public static FontRenderer getFontRenderer() {
      return mc.field_71466_p;
   }
}
