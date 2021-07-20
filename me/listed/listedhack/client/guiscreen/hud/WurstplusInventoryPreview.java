package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class WurstplusInventoryPreview extends WurstplusPinnable {
   public WurstplusInventoryPreview() {
      super("Inventory Preview", "InventoryPreview", 1.0F, 0, 0);
   }

   public void render() {
      if (this.mc.field_71439_g != null) {
         GlStateManager.func_179094_E();
         RenderHelper.func_74520_c();
         this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 60);
         this.set_width(144);
         this.set_height(48);

         for(int i = 0; i < 27; ++i) {
            ItemStack item_stack = (ItemStack)this.mc.field_71439_g.field_71071_by.field_70462_a.get(i + 9);
            int item_position_x = this.get_x() + i % 9 * 16;
            int item_position_y = this.get_y() + i / 9 * 16;
            this.mc.func_175599_af().func_180450_b(item_stack, item_position_x, item_position_y);
            this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, item_stack, item_position_x, item_position_y, (String)null);
         }

         this.mc.func_175599_af().field_77023_b = -5.0F;
         RenderHelper.func_74518_a();
         GlStateManager.func_179121_F();
      }

   }
}
