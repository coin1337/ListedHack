package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WurstplusCrystalCount extends WurstplusPinnable {
   int crystals = 0;

   public WurstplusCrystalCount() {
      super("Crystal Count", "CrystalCount", 1.0F, 0, 0);
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      if (this.mc.field_71439_g != null) {
         if (this.is_on_gui()) {
            this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
         }

         GlStateManager.func_179094_E();
         RenderHelper.func_74520_c();
         this.crystals = this.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter((stackx) -> {
            return stackx.func_77973_b() == Items.field_185158_cP;
         }).mapToInt(ItemStack::func_190916_E).sum();
         int off = 0;

         for(int i = 0; i < 45; ++i) {
            ItemStack stack = this.mc.field_71439_g.field_71071_by.func_70301_a(i);
            ItemStack off_h = this.mc.field_71439_g.func_184592_cb();
            if (off_h.func_77973_b() == Items.field_185158_cP) {
               off = off_h.func_77976_d();
            } else {
               off = 0;
            }

            if (stack.func_77973_b() == Items.field_185158_cP) {
               this.mc.func_175599_af().func_180450_b(stack, this.get_x(), this.get_y());
               this.create_line(Integer.toString(this.crystals + off), 18, 16 - this.get(Integer.toString(this.crystals + off), "height"), nl_r, nl_g, nl_b, nl_a);
            }
         }

         this.mc.func_175599_af().field_77023_b = 0.0F;
         RenderHelper.func_74518_a();
         GlStateManager.func_179121_F();
         this.set_width(16 + this.get(Integer.toString(this.crystals + off), "width") + 2);
         this.set_height(16);
      }

   }
}
