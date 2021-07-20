package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.util.WurstplusTextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WurstplusLogo extends WurstplusPinnable {
   ResourceLocation r = new ResourceLocation("custom/logo.png");

   public WurstplusLogo() {
      super("Logo", "Logo", 1.0F, 0, 0);
   }

   public void render() {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)this.get_x(), (float)this.get_y(), 0.0F);
      WurstplusTextureHelper.drawTexture(this.r, (float)this.get_x(), (float)this.get_y(), 100.0F, 25.0F);
      GL11.glPopMatrix();
      this.set_width(100);
      this.set_height(25);
   }
}
