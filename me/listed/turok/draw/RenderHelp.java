package me.listed.turok.draw;

import java.util.Arrays;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderHelp extends Tessellator {
   public static RenderHelp INSTANCE = new RenderHelp();

   public RenderHelp() {
      super(2097152);
   }

   public static void prepare(String mode_requested) {
      int mode = 0;
      if (mode_requested.equalsIgnoreCase("quads")) {
         mode = 7;
      } else if (mode_requested.equalsIgnoreCase("lines")) {
         mode = 1;
      }

      prepare_gl();
      begin(mode);
   }

   public static void prepare_gl() {
      GL11.glBlendFunc(770, 771);
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_187441_d(1.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179141_d();
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
   }

   public static void begin(int mode) {
      INSTANCE.func_178180_c().func_181668_a(mode, DefaultVertexFormats.field_181706_f);
   }

   public static void release() {
      render();
      release_gl();
   }

   public static void render() {
      INSTANCE.func_78381_a();
   }

   public static void release_gl() {
      GlStateManager.func_179089_o();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179147_l();
      GlStateManager.func_179126_j();
   }

   public static void draw_cube(BlockPos blockPos, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube(blockPos, r, g, b, a, sides);
   }

   public static void draw_cube(float x, float y, float z, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube(INSTANCE.func_178180_c(), x, y, z, 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static void draw_cube(BlockPos blockPos, int r, int g, int b, int a, String sides) {
      draw_cube(INSTANCE.func_178180_c(), (float)blockPos.func_177958_n(), (float)blockPos.func_177956_o(), (float)blockPos.func_177952_p(), 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static void draw_cube_line(BlockPos blockPos, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube_line(blockPos, r, g, b, a, sides);
   }

   public static void draw_cube_line(float x, float y, float z, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube_line(INSTANCE.func_178180_c(), x, y, z, 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static void draw_cube_line(BlockPos blockPos, int r, int g, int b, int a, String sides) {
      draw_cube_line(INSTANCE.func_178180_c(), (float)blockPos.func_177958_n(), (float)blockPos.func_177956_o(), (float)blockPos.func_177952_p(), 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static BufferBuilder get_buffer_build() {
      return INSTANCE.func_178180_c();
   }

   public static void draw_cube(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, String sides) {
      if (Arrays.asList(sides.split("-")).contains("down") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("up") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("north") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

   }

   public static void draw_cube_line(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, String sides) {
      if (Arrays.asList(sides.split("-")).contains("downwest") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("upwest") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("downeast") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("upeast") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("downnorth") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("upnorth") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("downsouth") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("upsouth") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("nortwest") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("norteast") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("southweast") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

      if (Arrays.asList(sides.split("-")).contains("southeast") || sides.equalsIgnoreCase("all")) {
         buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
         buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
      }

   }
}
