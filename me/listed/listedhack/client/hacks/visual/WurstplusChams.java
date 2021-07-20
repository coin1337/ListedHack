package me.listed.listedhack.client.hacks.visual;

import java.awt.Color;
import java.util.Iterator;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.event.events.WurstplusEventRenderEntityModel;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.listedhack.client.util.WurstplusRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WurstplusChams extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "ChamsMode", "Outline", this.combobox(new String[]{"Outline", "Wireframe"}));
   WurstplusSetting players = this.create("Players", "ChamsPlayers", true);
   WurstplusSetting mobs = this.create("Mobs", "ChamsMobs", true);
   WurstplusSetting self = this.create("Self", "ChamsSelf", true);
   WurstplusSetting items = this.create("Items", "ChamsItems", true);
   WurstplusSetting xporbs = this.create("Xp Orbs", "ChamsXPO", true);
   WurstplusSetting xpbottles = this.create("Xp Bottles", "ChamsBottles", true);
   WurstplusSetting pearl = this.create("Pearls", "ChamsPearls", true);
   WurstplusSetting top = this.create("Top", "ChamsTop", true);
   WurstplusSetting scale = this.create("Factor", "ChamsFactor", 0.0D, -1.0D, 1.0D);
   WurstplusSetting r = this.create("R", "ChamsR", 255, 0, 255);
   WurstplusSetting g = this.create("G", "ChamsG", 255, 0, 255);
   WurstplusSetting b = this.create("B", "ChamsB", 255, 0, 255);
   WurstplusSetting a = this.create("A", "ChamsA", 100, 0, 255);
   WurstplusSetting box_a = this.create("Box A", "ChamsABox", 100, 0, 255);
   WurstplusSetting width = this.create("Width", "ChamsWdith", 2.0D, 0.5D, 5.0D);
   WurstplusSetting rainbow_mode = this.create("Rainbow", "ChamsRainbow", false);
   WurstplusSetting sat = this.create("Satiation", "ChamsSatiation", 0.8D, 0.0D, 1.0D);
   WurstplusSetting brightness = this.create("Brightness", "ChamsBrightness", 0.8D, 0.0D, 1.0D);

   public WurstplusChams() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Chams";
      this.tag = "Chams";
      this.description = "see even less (now with epic colours)";
   }

   public void update() {
      if (this.rainbow_mode.get_value(true)) {
         this.cycle_rainbow();
      }

   }

   public void cycle_rainbow() {
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
      this.r.set_value(color_rgb_o >> 16 & 255);
      this.g.set_value(color_rgb_o >> 8 & 255);
      this.b.set_value(color_rgb_o & 255);
   }

   public void render(WurstplusEventRender event) {
      int i;
      Iterator var3;
      Entity entity;
      Vec3d interp;
      AxisAlignedBB bb;
      if (this.items.get_value(true)) {
         i = 0;
         var3 = mc.field_71441_e.field_72996_f.iterator();

         while(var3.hasNext()) {
            entity = (Entity)var3.next();
            if (entity instanceof EntityItem && mc.field_71439_g.func_70068_e(entity) < 2500.0D) {
               interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.func_184121_ak());
               bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05D - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05D - entity.field_70161_v + interp.field_72449_c);
               GlStateManager.func_179094_E();
               GlStateManager.func_179147_l();
               GlStateManager.func_179097_i();
               GlStateManager.func_179120_a(770, 771, 0, 1);
               GlStateManager.func_179090_x();
               GlStateManager.func_179132_a(false);
               GL11.glEnable(2848);
               GL11.glHint(3154, 4354);
               GL11.glLineWidth(1.0F);
               RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), (float)this.r.get_value(1) / 255.0F, (float)this.g.get_value(1) / 255.0F, (float)this.b.get_value(1) / 255.0F, (float)this.box_a.get_value(1) / 255.0F);
               GL11.glDisable(2848);
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179126_j();
               GlStateManager.func_179098_w();
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
               WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0F);
               ++i;
               if (i >= 50) {
                  break;
               }
            }
         }
      }

      if (this.xporbs.get_value(true)) {
         i = 0;
         var3 = mc.field_71441_e.field_72996_f.iterator();

         while(var3.hasNext()) {
            entity = (Entity)var3.next();
            if (entity instanceof EntityXPOrb && mc.field_71439_g.func_70068_e(entity) < 2500.0D) {
               interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.func_184121_ak());
               bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05D - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05D - entity.field_70161_v + interp.field_72449_c);
               GlStateManager.func_179094_E();
               GlStateManager.func_179147_l();
               GlStateManager.func_179097_i();
               GlStateManager.func_179120_a(770, 771, 0, 1);
               GlStateManager.func_179090_x();
               GlStateManager.func_179132_a(false);
               GL11.glEnable(2848);
               GL11.glHint(3154, 4354);
               GL11.glLineWidth(1.0F);
               RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), (float)this.r.get_value(1) / 255.0F, (float)this.g.get_value(1) / 255.0F, (float)this.b.get_value(1) / 255.0F, (float)this.box_a.get_value(1) / 255.0F);
               GL11.glDisable(2848);
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179126_j();
               GlStateManager.func_179098_w();
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
               WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0F);
               ++i;
               if (i >= 50) {
                  break;
               }
            }
         }
      }

      if (this.pearl.get_value(true)) {
         i = 0;
         var3 = mc.field_71441_e.field_72996_f.iterator();

         while(var3.hasNext()) {
            entity = (Entity)var3.next();
            if (entity instanceof EntityEnderPearl && mc.field_71439_g.func_70068_e(entity) < 2500.0D) {
               interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.func_184121_ak());
               bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05D - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05D - entity.field_70161_v + interp.field_72449_c);
               GlStateManager.func_179094_E();
               GlStateManager.func_179147_l();
               GlStateManager.func_179097_i();
               GlStateManager.func_179120_a(770, 771, 0, 1);
               GlStateManager.func_179090_x();
               GlStateManager.func_179132_a(false);
               GL11.glEnable(2848);
               GL11.glHint(3154, 4354);
               GL11.glLineWidth(1.0F);
               RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), (float)this.r.get_value(1) / 255.0F, (float)this.g.get_value(1) / 255.0F, (float)this.b.get_value(1) / 255.0F, (float)this.box_a.get_value(1) / 255.0F);
               GL11.glDisable(2848);
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179126_j();
               GlStateManager.func_179098_w();
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
               WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0F);
               ++i;
               if (i >= 50) {
                  break;
               }
            }
         }
      }

      if (this.xpbottles.get_value(true)) {
         i = 0;
         var3 = mc.field_71441_e.field_72996_f.iterator();

         while(var3.hasNext()) {
            entity = (Entity)var3.next();
            if (entity instanceof EntityExpBottle && mc.field_71439_g.func_70068_e(entity) < 2500.0D) {
               interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.func_184121_ak());
               bb = new AxisAlignedBB(entity.func_174813_aQ().field_72340_a - 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72338_b - 0.0D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72339_c - 0.05D - entity.field_70161_v + interp.field_72449_c, entity.func_174813_aQ().field_72336_d + 0.05D - entity.field_70165_t + interp.field_72450_a, entity.func_174813_aQ().field_72337_e + 0.1D - entity.field_70163_u + interp.field_72448_b, entity.func_174813_aQ().field_72334_f + 0.05D - entity.field_70161_v + interp.field_72449_c);
               GlStateManager.func_179094_E();
               GlStateManager.func_179147_l();
               GlStateManager.func_179097_i();
               GlStateManager.func_179120_a(770, 771, 0, 1);
               GlStateManager.func_179090_x();
               GlStateManager.func_179132_a(false);
               GL11.glEnable(2848);
               GL11.glHint(3154, 4354);
               GL11.glLineWidth(1.0F);
               RenderGlobal.func_189696_b(bb.func_186662_g((double)this.scale.get_value(1)), (float)this.r.get_value(1) / 255.0F, (float)this.g.get_value(1) / 255.0F, (float)this.b.get_value(1) / 255.0F, (float)this.box_a.get_value(1) / 255.0F);
               GL11.glDisable(2848);
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179126_j();
               GlStateManager.func_179098_w();
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
               WurstplusRenderUtil.drawBlockOutline(bb.func_186662_g((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0F);
               ++i;
               if (i >= 50) {
                  break;
               }
            }
         }
      }

   }

   public void on_render_model(WurstplusEventRenderEntityModel event) {
      if (event.stage == 0 && event.entity != null && (this.self.get_value(true) || !event.entity.equals(mc.field_71439_g)) && (this.players.get_value(true) || !(event.entity instanceof EntityPlayer)) && (this.mobs.get_value(true) || !(event.entity instanceof EntityMob))) {
         Color color = new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1));
         boolean fancyGraphics = mc.field_71474_y.field_74347_j;
         mc.field_71474_y.field_74347_j = false;
         float gamma = mc.field_71474_y.field_74333_Y;
         mc.field_71474_y.field_74333_Y = 10000.0F;
         if (this.top.get_value(true)) {
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
         }

         if (this.mode.in("outline")) {
            WurstplusRenderUtil.renderOne((float)this.width.get_value(1));
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            WurstplusRenderUtil.renderTwo();
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            WurstplusRenderUtil.renderThree();
            WurstplusRenderUtil.renderFour(color);
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            WurstplusRenderUtil.renderFive();
         } else {
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1028, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179131_c((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GlStateManager.func_187441_d((float)this.width.get_value(1));
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
         }

         if (!this.top.get_value(true)) {
            event.modelBase.func_78088_a(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
         }

         try {
            mc.field_71474_y.field_74347_j = fancyGraphics;
            mc.field_71474_y.field_74333_Y = gamma;
         } catch (Exception var6) {
         }

         event.cancel();
      }
   }
}
