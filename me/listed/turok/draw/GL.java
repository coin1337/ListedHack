package me.listed.turok.draw;

import org.lwjgl.opengl.GL11;

public class GL {
   public static void color(float r, float g, float b, float a) {
      GL11.glColor4f(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
   }

   public static void start() {
      GL11.glDisable(3553);
   }

   public static void finish() {
      GL11.glDisable(3553);
      GL11.glDisable(3042);
   }

   public static void draw_rect(int x, int y, int width, int height) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glBegin(7);
      GL11.glVertex2d((double)(x + width), (double)y);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x, (double)(y + height));
      GL11.glVertex2d((double)(x + width), (double)(y + height));
      GL11.glEnd();
   }

   public static void resize(int x, int y, float size) {
      GL11.glEnable(3553);
      GL11.glEnable(3042);
      GL11.glTranslatef((float)x, (float)y, 0.0F);
      GL11.glScalef(size, size, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void resize(int x, int y, float size, String tag) {
      GL11.glScalef(1.0F / size, 1.0F / size, 1.0F);
      GL11.glTranslatef((float)(-x), (float)(-y), 0.0F);
      GL11.glDisable(3553);
   }
}
