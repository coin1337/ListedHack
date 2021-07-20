package me.listed.listedhack.client.guiscreen.hud;

import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class WurstplusArmorPreview extends WurstplusPinnable {
   private final RenderItem itemRender;

   public WurstplusArmorPreview() {
      super("Armor Preview", "ArmorPreview", 1.0F, 0, 0);
      this.itemRender = this.mc.func_175599_af();
   }

   public void render() {
      if (this.mc.field_71439_g != null && this.is_on_gui()) {
         this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
      }

      GlStateManager.func_179098_w();
      ScaledResolution resolution = new ScaledResolution(this.mc);
      int i = resolution.func_78326_a() / 2;
      int y = resolution.func_78328_b() - 55 - (this.mc.field_71439_g.func_70090_H() ? 10 : 0);
      int iteration = 0;
      Iterator var5 = this.mc.field_71439_g.field_71071_by.field_70460_b.iterator();

      while(var5.hasNext()) {
         ItemStack is = (ItemStack)var5.next();
         ++iteration;
         if (!is.func_190926_b()) {
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.func_179126_j();
            this.itemRender.field_77023_b = 200.0F;
            this.itemRender.func_180450_b(is, x, y);
            this.itemRender.func_180453_a(this.mc.field_71466_p, is, x, y, "");
            this.itemRender.field_77023_b = 0.0F;
            GlStateManager.func_179098_w();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            String s = is.func_190916_E() > 1 ? is.func_190916_E() + "" : "";
            this.mc.field_71466_p.func_175063_a(s, (float)(x + 19 - 2 - this.mc.field_71466_p.func_78256_a(s)), (float)(y + 9), 16777215);
            float green = ((float)is.func_77958_k() - (float)is.func_77952_i()) / (float)is.func_77958_k();
            float red = 1.0F - green;
            int dmg = 100 - (int)(red * 100.0F);
            if (dmg >= 100) {
               this.mc.field_71466_p.func_175063_a(dmg + "", (float)(x + 8 - this.mc.field_71466_p.func_78256_a(dmg + "") / 2), (float)(y - 11), toHex((int)(red * 255.0F), (int)(green * 255.0F), 0));
            } else if (dmg <= 0) {
               this.mc.field_71466_p.func_175063_a("0%", (float)(x + 8 - this.mc.field_71466_p.func_78256_a("0") / 2), (float)(y - 11), toHex((int)(red * 255.0F), (int)(green * 255.0F), 0));
            } else {
               this.mc.field_71466_p.func_175063_a(dmg + "%", (float)(x + 8 - this.mc.field_71466_p.func_78256_a(dmg + "") / 2), (float)(y - 11), toHex((int)(red * 255.0F), (int)(green * 255.0F), 0));
            }
         }
      }

      GlStateManager.func_179126_j();
      GlStateManager.func_179140_f();
      this.set_width(50);
      this.set_height(25);
   }

   public static int toHex(int r, int g, int b) {
      return -16777216 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
   }
}
