package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class WurstplusInventoryXCarryPreview extends WurstplusPinnable {
   public WurstplusInventoryXCarryPreview() {
      super("Inventory XCarry", "InventoryXPreview", 1.0F, 0, 0);
   }

   public void render() {
      if (this.mc.field_71439_g != null) {
         GlStateManager.func_179094_E();
         RenderHelper.func_74520_c();
         this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 60);
         this.set_width(32);
         this.set_height(32);

         for(int i = 1; i < 5; ++i) {
            ItemStack item_stack = ((Slot)this.mc.field_71439_g.field_71069_bz.field_75151_b.get(i)).func_75211_c();
            int item_position_x = this.get_x();
            int item_position_y = this.get_y();
            if (i == 1) {
               item_position_x += 0;
               item_position_y += 0;
            }

            if (i == 2) {
               item_position_x += 16;
               item_position_y += 0;
            }

            if (i == 3) {
               item_position_x += 0;
               item_position_y += 16;
            }

            if (i == 4) {
               item_position_x += 16;
               item_position_y += 16;
            }

            this.mc.func_175599_af().func_180450_b(item_stack, item_position_x, item_position_y);
            this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, item_stack, item_position_x, item_position_y, (String)null);
         }

         this.mc.func_175599_af().field_77023_b = -5.0F;
         RenderHelper.func_74518_a();
         GlStateManager.func_179121_F();
      }

   }
}
