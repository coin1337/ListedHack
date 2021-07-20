package me.listed.listedhack.client.hacks.visual;

import java.awt.Color;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WurstplusTracers extends WurstplusHack {
   WurstplusSetting friends = this.create("Friends", "TracerFriends", false);
   WurstplusSetting range = this.create("Range", "TracerRange", 50, 0, 250);
   WurstplusSetting width = this.create("Width", "TracerWidth", 1.0D, 0.0D, 5.0D);
   WurstplusSetting offset = this.create("Offset", "TracerOffset", 0.0D, -4.0D, 4.0D);

   public WurstplusTracers() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Tracers";
      this.tag = "Tracers";
      this.description = "DRAWS LINES";
   }

   public void render(WurstplusEventRender event) {
      if (mc.field_71441_e != null) {
         GlStateManager.func_179094_E();
         float[][] colour = new float[1][1];
         mc.field_71441_e.field_72996_f.forEach((entity) -> {
            if (entity instanceof EntityPlayer && entity != mc.field_71439_g) {
               EntityPlayer player = (EntityPlayer)entity;
               if (!(mc.field_71439_g.func_70032_d(player) > (float)this.range.get_value(1))) {
                  if (!WurstplusFriendUtil.isFriend(player.func_70005_c_()) || this.friends.get_value(true)) {
                     colour[0] = this.getColorByDistance(player);
                     this.drawLineToEntity(player, colour[0][0], colour[0][1], colour[0][2], colour[0][3]);
                  }
               }
            }
         });
         GlStateManager.func_179121_F();
      }
   }

   public double interpolate(double now, double then) {
      return then + (now - then) * (double)mc.func_184121_ak();
   }

   public double[] interpolate(Entity entity) {
      double posX = this.interpolate(entity.field_70165_t, entity.field_70142_S) - mc.func_175598_ae().field_78725_b;
      double posY = this.interpolate(entity.field_70163_u, entity.field_70137_T) - mc.func_175598_ae().field_78726_c;
      double posZ = this.interpolate(entity.field_70161_v, entity.field_70136_U) - mc.func_175598_ae().field_78723_d;
      return new double[]{posX, posY, posZ};
   }

   public void drawLineToEntity(Entity e, float red, float green, float blue, float opacity) {
      double[] xyz = this.interpolate(e);
      this.drawLine(xyz[0], xyz[1], xyz[2], (double)e.field_70131_O, red, green, blue, opacity);
   }

   public void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
      Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians((double)mc.field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians((double)mc.field_71439_g.field_70177_z)));
      this.drawLineFromPosToPos(eyes.field_72450_a, eyes.field_72448_b + (double)mc.field_71439_g.func_70047_e() + (double)((float)this.offset.get_value(1)), eyes.field_72449_c, posx, posy, posz, up, red, green, blue, opacity);
   }

   public void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth((float)this.width.get_value(1));
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, opacity);
      GlStateManager.func_179140_f();
      GL11.glLoadIdentity();
      mc.field_71460_t.func_78467_g(mc.func_184121_ak());
      GL11.glBegin(1);
      GL11.glVertex3d(posx, posy, posz);
      GL11.glVertex3d(posx2, posy2, posz2);
      GL11.glVertex3d(posx2, posy2, posz2);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      GlStateManager.func_179145_e();
   }

   public float[] getColorByDistance(Entity entity) {
      if (entity instanceof EntityPlayer && WurstplusFriendUtil.isFriend(entity.func_70005_c_())) {
         return new float[]{0.0F, 0.5F, 1.0F, 1.0F};
      } else {
         Color col = new Color(Color.HSBtoRGB((float)(Math.max(0.0D, Math.min(mc.field_71439_g.func_70068_e(entity), 2500.0D) / 2500.0D) / 3.0D), 1.0F, 0.8F) | -16777216);
         return new float[]{(float)col.getRed() / 255.0F, (float)col.getGreen() / 255.0F, (float)col.getBlue() / 255.0F, 1.0F};
      }
   }
}
