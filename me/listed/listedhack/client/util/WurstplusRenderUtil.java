package me.listed.listedhack.client.util;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class WurstplusRenderUtil {
   private static final Minecraft mc = Minecraft.func_71410_x();
   public static RenderItem itemRender;
   public static ICamera camera;

   public static void renderOne(float lineWidth) {
      checkSetupFBO();
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(lineWidth);
      GL11.glEnable(2848);
      GL11.glEnable(2960);
      GL11.glClear(1024);
      GL11.glClearStencil(15);
      GL11.glStencilFunc(512, 1, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6913);
   }

   public static void checkSetupFBO() {
      Framebuffer fbo = mc.func_147110_a();
      if (fbo.field_147624_h > -1) {
         setupFBO(fbo);
         fbo.field_147624_h = -1;
      }

   }

   public static void drawBlockOutline(AxisAlignedBB bb, Color color, float linewidth) {
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      float alpha = (float)color.getAlpha() / 255.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(linewidth);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      tessellator.func_78381_a();
      GL11.glDisable(2848);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void drawText(BlockPos pos, String text) {
      GlStateManager.func_179094_E();
      glBillboardDistanceScaled((float)pos.func_177958_n() + 0.5F, (float)pos.func_177956_o() + 0.5F, (float)pos.func_177952_p() + 0.5F, mc.field_71439_g, 1.0F);
      GlStateManager.func_179097_i();
      GlStateManager.func_179137_b(-((double)mc.field_71466_p.func_78256_a(text) / 2.0D), 0.0D, 0.0D);
      mc.field_71466_p.func_175063_a(text, 0.0F, 0.0F, -5592406);
      GlStateManager.func_179121_F();
   }

   public static void drawRect(float x, float y, float w, float h, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b((double)x, (double)h, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b((double)w, (double)h, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b((double)w, (double)y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b((double)x, (double)y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawRect(float x, float y, float w, float h, float r, float g, float b, float a) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b((double)x, (double)h, 0.0D).func_181666_a(r / 255.0F, g / 255.0F, b / 255.0F, a).func_181675_d();
      bufferbuilder.func_181662_b((double)w, (double)h, 0.0D).func_181666_a(r / 255.0F, g / 255.0F, b / 255.0F, a).func_181675_d();
      bufferbuilder.func_181662_b((double)w, (double)y, 0.0D).func_181666_a(r / 255.0F, g / 255.0F, b / 255.0F, a).func_181675_d();
      bufferbuilder.func_181662_b((double)x, (double)y, 0.0D).func_181666_a(r / 255.0F, g / 255.0F, b / 255.0F, a).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void glrendermethod() {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glEnable(2884);
      GL11.glDisable(2929);
      double viewerPosX = mc.func_175598_ae().field_78730_l;
      double viewerPosY = mc.func_175598_ae().field_78731_m;
      double viewerPosZ = mc.func_175598_ae().field_78728_n;
      GL11.glPushMatrix();
      GL11.glTranslated(-viewerPosX, -viewerPosY, -viewerPosZ);
   }

   public static void glStart(float n, float n2, float n3, float n4) {
      glrendermethod();
      GL11.glColor4f(n, n2, n3, n4);
   }

   public static void glEnd() {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      GL11.glEnable(2929);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void glBillboard(float x, float y, float z) {
      float scale = 0.02666667F;
      GlStateManager.func_179137_b((double)x - mc.func_175598_ae().field_78725_b, (double)y - mc.func_175598_ae().field_78726_c, (double)z - mc.func_175598_ae().field_78723_d);
      GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-mc.field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(mc.field_71439_g.field_70125_A, mc.field_71474_y.field_74320_O == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-0.02666667F, -0.02666667F, 0.02666667F);
   }

   public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) {
      glBillboard(x, y, z);
      int distance = (int)player.func_70011_f((double)x, (double)y, (double)z);
      float scaleDistance = (float)distance / 2.0F / (2.0F + (2.0F - scale));
      if (scaleDistance < 1.0F) {
         scaleDistance = 1.0F;
      }

      GlStateManager.func_179152_a(scaleDistance, scaleDistance, scaleDistance);
   }

   private static void GLPre(boolean depth, boolean texture, boolean clean, boolean bind, boolean override, float lineWidth) {
      if (depth) {
         GL11.glDisable(2896);
      }

      if (!texture) {
         GL11.glEnable(3042);
      }

      GL11.glLineWidth(lineWidth);
      if (clean) {
         GL11.glDisable(3553);
      }

      if (bind) {
         GL11.glDisable(2929);
      }

      if (!override) {
         GL11.glEnable(2848);
      }

      GlStateManager.func_187401_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GL11.glHint(3154, 4354);
      GlStateManager.func_179132_a(false);
   }

   public static void drawArc(float cx, float cy, float r, float start_angle, float end_angle, int num_segments) {
      GL11.glBegin(4);

      for(int i = (int)((float)num_segments / (360.0F / start_angle)) + 1; (float)i <= (float)num_segments / (360.0F / end_angle); ++i) {
         double previousangle = 6.283185307179586D * (double)(i - 1) / (double)num_segments;
         double angle = 6.283185307179586D * (double)i / (double)num_segments;
         GL11.glVertex2d((double)cx, (double)cy);
         GL11.glVertex2d((double)cx + Math.cos(angle) * (double)r, (double)cy + Math.sin(angle) * (double)r);
         GL11.glVertex2d((double)cx + Math.cos(previousangle) * (double)r, (double)cy + Math.sin(previousangle) * (double)r);
      }

      glEnd();
   }

   public static void drawArcOutline(float cx, float cy, float r, float start_angle, float end_angle, int num_segments) {
      GL11.glBegin(2);

      for(int i = (int)((float)num_segments / (360.0F / start_angle)) + 1; (float)i <= (float)num_segments / (360.0F / end_angle); ++i) {
         double angle = 6.283185307179586D * (double)i / (double)num_segments;
         GL11.glVertex2d((double)cx + Math.cos(angle) * (double)r, (double)cy + Math.sin(angle) * (double)r);
      }

      glEnd();
   }

   public static void renderTwo() {
      GL11.glStencilFunc(512, 0, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6914);
   }

   public static void renderThree() {
      GL11.glStencilFunc(514, 1, 15);
      GL11.glStencilOp(7680, 7680, 7680);
      GL11.glPolygonMode(1032, 6913);
   }

   public static void renderFour(Color color) {
      setColor(color);
      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(10754);
      GL11.glPolygonOffset(1.0F, -2000000.0F);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
   }

   public static void renderFive() {
      GL11.glPolygonOffset(1.0F, 2000000.0F);
      GL11.glDisable(10754);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(2960);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glEnable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      GL11.glPopAttrib();
   }

   public static void setColor(Color color) {
      GL11.glColor4d((double)color.getRed() / 255.0D, (double)color.getGreen() / 255.0D, (double)color.getBlue() / 255.0D, (double)color.getAlpha() / 255.0D);
   }

   private static void setupFBO(Framebuffer fbo) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.field_147624_h);
      int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
      EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.field_71443_c, mc.field_71440_d);
      EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
      EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
   }

   static {
      itemRender = mc.func_175599_af();
      camera = new Frustum();
   }
}
