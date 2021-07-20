package me.listed.listedhack.client.hacks.visual;

import java.awt.Color;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

public class WurstplusHighlight extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "HighlightMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
   WurstplusSetting rgb = this.create("RGB Effect", "HighlightRGBEffect", true);
   WurstplusSetting r = this.create("R", "HighlightR", 255, 0, 255);
   WurstplusSetting g = this.create("G", "HighlightG", 255, 0, 255);
   WurstplusSetting b = this.create("B", "HighlightB", 255, 0, 255);
   WurstplusSetting a = this.create("A", "HighlightA", 100, 0, 255);
   WurstplusSetting l_a = this.create("Outline A", "HighlightLineA", 255, 0, 255);
   int color_r;
   int color_g;
   int color_b;
   boolean outline = false;
   boolean solid = false;

   public WurstplusHighlight() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Block Highlight";
      this.tag = "BlockHighlight";
      this.description = "see what block ur targeting";
   }

   public void disable() {
      this.outline = false;
      this.solid = false;
   }

   public void render(WurstplusEventRender event) {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
         int color_rgb = Color.HSBtoRGB(tick_color[0], 1.0F, 1.0F);
         if (this.rgb.get_value(true)) {
            this.color_r = color_rgb >> 16 & 255;
            this.color_g = color_rgb >> 8 & 255;
            this.color_b = color_rgb & 255;
            this.r.set_value(this.color_r);
            this.g.set_value(this.color_g);
            this.b.set_value(this.color_b);
         } else {
            this.color_r = this.r.get_value(1);
            this.color_g = this.g.get_value(2);
            this.color_b = this.b.get_value(3);
         }

         if (this.mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
         }

         if (this.mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
         }

         if (this.mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
         }

         RayTraceResult result = mc.field_71476_x;
         if (result != null && result.field_72313_a == Type.BLOCK) {
            BlockPos block_pos = result.func_178782_a();
            if (this.solid) {
               RenderHelp.prepare("quads");
               RenderHelp.draw_cube(block_pos, this.color_r, this.color_g, this.color_b, this.a.get_value(1), "all");
               RenderHelp.release();
            }

            if (this.outline) {
               RenderHelp.prepare("lines");
               RenderHelp.draw_cube_line(block_pos, this.color_r, this.color_g, this.color_b, this.l_a.get_value(1), "all");
               RenderHelp.release();
            }
         }
      }

   }
}
