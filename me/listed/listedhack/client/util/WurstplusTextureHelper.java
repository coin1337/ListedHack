package me.listed.listedhack.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WurstplusTextureHelper {
   static final Minecraft mc = Minecraft.func_71410_x();

   public static void drawTexture(ResourceLocation resourceLocation, float x, float y, float width, float height) {
      GL11.glPushMatrix();
      float size = width / 2.0F;
      GL11.glEnable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      bindTexture(resourceLocation);
      GL11.glBegin(7);
      GL11.glTexCoord2d((double)(0.0F / size), (double)(0.0F / size));
      GL11.glVertex2d((double)x, (double)y);
      GL11.glTexCoord2d((double)(0.0F / size), (double)((0.0F + size) / size));
      GL11.glVertex2d((double)x, (double)(y + height));
      GL11.glTexCoord2d((double)((0.0F + size) / size), (double)((0.0F + size) / size));
      GL11.glVertex2d((double)(x + width), (double)(y + height));
      GL11.glTexCoord2d((double)((0.0F + size) / size), (double)(0.0F / size));
      GL11.glVertex2d((double)(x + width), (double)y);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      mc.func_175599_af().func_180450_b(new ItemStack(Items.field_151161_ac), 999999, 999999);
   }

   public static void bindTexture(ResourceLocation resourceLocation) {
      try {
         ITextureObject texture = mc.func_110434_K().func_110581_b(resourceLocation);
         GL11.glBindTexture(3553, texture.func_110552_b());
      } catch (Exception var2) {
      }

   }
}
