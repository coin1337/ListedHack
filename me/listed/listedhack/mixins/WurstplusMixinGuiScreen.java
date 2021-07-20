package me.listed.listedhack.mixins;

import me.listed.listedhack.ListedHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiScreen.class})
public class WurstplusMixinGuiScreen {
   RenderItem itemRender = Minecraft.func_71410_x().func_175599_af();
   FontRenderer fontRenderer;

   public WurstplusMixinGuiScreen() {
      this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
   }

   @Inject(
      method = {"drawWorldBackground"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void drawWorldBackground(int tint, CallbackInfo info) {
      if (Minecraft.func_71410_x().field_71439_g != null) {
         info.cancel();
      }

   }

   @Inject(
      method = {"renderToolTip"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo info) {
      if (ListedHack.get_hack_manager().get_module_with_tag("ShulkerPreview").is_active() && stack.func_77973_b() instanceof ItemShulkerBox) {
         NBTTagCompound tagCompound = stack.func_77978_p();
         if (tagCompound != null && tagCompound.func_150297_b("BlockEntityTag", 10)) {
            NBTTagCompound blockEntityTag = tagCompound.func_74775_l("BlockEntityTag");
            if (blockEntityTag.func_150297_b("Items", 9)) {
               info.cancel();
               NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
               ItemStackHelper.func_191283_b(blockEntityTag, nonnulllist);
               GlStateManager.func_179147_l();
               GlStateManager.func_179101_C();
               RenderHelper.func_74518_a();
               GlStateManager.func_179140_f();
               GlStateManager.func_179097_i();
               int width = Math.max(144, this.fontRenderer.func_78256_a(stack.func_82833_r()) + 3);
               int x1 = x + 12;
               int y1 = y - 12;
               int height = 57;
               this.itemRender.field_77023_b = 300.0F;
               this.drawGradientRectP(x1 - 3, y1 - 4, x1 + width + 3, y1 - 3, -267386864, -267386864);
               this.drawGradientRectP(x1 - 3, y1 + height + 3, x1 + width + 3, y1 + height + 4, -267386864, -267386864);
               this.drawGradientRectP(x1 - 3, y1 - 3, x1 + width + 3, y1 + height + 3, -267386864, -267386864);
               this.drawGradientRectP(x1 - 4, y1 - 3, x1 - 3, y1 + height + 3, -267386864, -267386864);
               this.drawGradientRectP(x1 + width + 3, y1 - 3, x1 + width + 4, y1 + height + 3, -267386864, -267386864);
               this.drawGradientRectP(x1 - 3, y1 - 3 + 1, x1 - 3 + 1, y1 + height + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(x1 + width + 2, y1 - 3 + 1, x1 + width + 3, y1 + height + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(x1 - 3, y1 - 3, x1 + width + 3, y1 - 3 + 1, 1347420415, 1347420415);
               this.drawGradientRectP(x1 - 3, y1 + height + 2, x1 + width + 3, y1 + height + 3, 1344798847, 1344798847);
               this.fontRenderer.func_78276_b(stack.func_82833_r(), x + 12, y - 12, 16777215);
               GlStateManager.func_179147_l();
               GlStateManager.func_179141_d();
               GlStateManager.func_179098_w();
               GlStateManager.func_179145_e();
               GlStateManager.func_179126_j();
               RenderHelper.func_74520_c();

               for(int i = 0; i < nonnulllist.size(); ++i) {
                  int iX = x + i % 9 * 16 + 11;
                  int iY = y + i / 9 * 16 - 11 + 8;
                  ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                  this.itemRender.func_180450_b(itemStack, iX, iY);
                  this.itemRender.func_180453_a(this.fontRenderer, itemStack, iX, iY, (String)null);
               }

               RenderHelper.func_74518_a();
               this.itemRender.field_77023_b = 0.0F;
               GlStateManager.func_179145_e();
               GlStateManager.func_179126_j();
               RenderHelper.func_74519_b();
               GlStateManager.func_179091_B();
            }
         }
      }

   }

   private void drawGradientRectP(int left, int top, int right, int bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179103_j(7425);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b((double)right, (double)top, 300.0D).func_181666_a(f1, f2, f3, f).func_181675_d();
      bufferbuilder.func_181662_b((double)left, (double)top, 300.0D).func_181666_a(f1, f2, f3, f).func_181675_d();
      bufferbuilder.func_181662_b((double)left, (double)bottom, 300.0D).func_181666_a(f5, f6, f7, f4).func_181675_d();
      bufferbuilder.func_181662_b((double)right, (double)bottom, 300.0D).func_181666_a(f5, f6, f7, f4).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }
}
