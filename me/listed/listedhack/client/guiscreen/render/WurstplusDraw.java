package me.listed.listedhack.client.guiscreen.render;

import java.awt.Color;
import java.util.Arrays;
import me.listed.turok.Turok;
import me.listed.turok.draw.RenderHelp;
import me.listed.turok.task.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class WurstplusDraw {
   private static FontRenderer font_renderer;
   private static FontRenderer custom_font;
   private float size;

   public WurstplusDraw(float size) {
      this.size = size;
   }

   public static void draw_rect(int x, int y, int w, int h, int r, int g, int b, int a) {
      Gui.func_73734_a(x, y, w, h, (new WurstplusDraw.TravisColor(r, g, b, a)).hex());
   }

   public static void draw_rect(int x, int y, int w, int h, int r, int g, int b, int a, int size, String type) {
      if (Arrays.asList(type.split("-")).contains("up")) {
         draw_rect(x, y, x + w, y + size, r, g, b, a);
      }

      if (Arrays.asList(type.split("-")).contains("down")) {
         draw_rect(x, y + h - size, x + w, y + h, r, g, b, a);
      }

      if (Arrays.asList(type.split("-")).contains("left")) {
         draw_rect(x, y, x + size, y + h, r, g, b, a);
      }

      if (Arrays.asList(type.split("-")).contains("right")) {
         draw_rect(x + w - size, y, x + w, y + h, r, g, b, a);
      }

   }

   public static void draw_rect(Rect rect, int r, int g, int b, int a) {
      Gui.func_73734_a(rect.get_x(), rect.get_y(), rect.get_screen_width(), rect.get_screen_height(), (new WurstplusDraw.TravisColor(r, g, b, a)).hex());
   }

   public static void draw_string(String string, int x, int y, int r, int g, int b, int a) {
      font_renderer.func_175063_a(string, (float)x, (float)y, (new WurstplusDraw.TravisColor(r, g, b, a)).hex());
   }

   public void draw_string_gl(String string, int x, int y, int r, int g, int b) {
      Turok resize_gl = new Turok("Resize");
      resize_gl.resize(x, y, this.size);
      font_renderer.func_78276_b(string, x, y, (new WurstplusDraw.TravisColor(r, g, b)).hex());
      resize_gl.resize(x, y, this.size, "end");
      GL11.glPushMatrix();
      GL11.glEnable(3553);
      GL11.glEnable(3042);
      GlStateManager.func_179147_l();
      GL11.glPopMatrix();
      RenderHelp.release_gl();
   }

   public int get_string_height() {
      FontRenderer fontRenderer = font_renderer;
      return (int)((float)fontRenderer.field_78288_b * this.size);
   }

   public int get_string_width(String string) {
      FontRenderer fontRenderer = font_renderer;
      return (int)((float)fontRenderer.func_78256_a(string) * this.size);
   }

   static {
      font_renderer = Minecraft.func_71410_x().field_71466_p;
      custom_font = Minecraft.func_71410_x().field_71466_p;
   }

   public static class TravisColor extends Color {
      public TravisColor(int r, int g, int b, int a) {
         super(r, g, b, a);
      }

      public TravisColor(int r, int g, int b) {
         super(r, g, b);
      }

      public int hex() {
         return this.getRGB();
      }
   }
}
