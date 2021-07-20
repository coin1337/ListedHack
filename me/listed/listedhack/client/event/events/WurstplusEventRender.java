package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

public class WurstplusEventRender extends WurstplusEventCancellable {
   private final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
   private final Tessellator tessellator;
   private final Vec3d render_pos;

   public WurstplusEventRender(Tessellator tessellator, Vec3d pos) {
      this.tessellator = tessellator;
      this.render_pos = pos;
   }

   public Tessellator get_tessellator() {
      return this.tessellator;
   }

   public Vec3d get_render_pos() {
      return this.render_pos;
   }

   public BufferBuilder get_buffer_build() {
      return this.tessellator.func_178180_c();
   }

   public void set_translation(Vec3d pos) {
      this.get_buffer_build().func_178969_c(-pos.field_72450_a, -pos.field_72448_b, -pos.field_72449_c);
   }

   public void reset_translation() {
      this.set_translation(this.render_pos);
   }

   public double get_screen_width() {
      return this.res.func_78327_c();
   }

   public double get_screen_height() {
      return this.res.func_78324_d();
   }
}
