package me.listed.listedhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.util.WurstplusTimeUtil;
import net.minecraft.util.math.MathHelper;

public class WurstplusUser extends WurstplusPinnable {
   private int scaled_width;
   private int scaled_height;
   private int scale_factor;

   public WurstplusUser() {
      super("User", "User", 1.0F, 0, 0);
   }

   public void render() {
      this.updateResolution();
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      int time = WurstplusTimeUtil.get_hour();
      String line;
      if (time >= 0 && time < 12) {
         line = "Morning, " + ChatFormatting.GOLD + ChatFormatting.BOLD + this.mc.field_71439_g.func_70005_c_() + ChatFormatting.RESET + " you smell good today :)";
      } else if (time >= 12 && time < 16) {
         line = "Afternoon, " + ChatFormatting.GOLD + ChatFormatting.BOLD + this.mc.field_71439_g.func_70005_c_() + ChatFormatting.RESET + " you're looking good today :)";
      } else if (time >= 16 && time < 24) {
         line = "Evening, " + ChatFormatting.GOLD + ChatFormatting.BOLD + this.mc.field_71439_g.func_70005_c_() + ChatFormatting.RESET + " you smell good today :)";
      } else {
         line = "Welcome, " + ChatFormatting.GOLD + ChatFormatting.BOLD + this.mc.field_71439_g.func_70005_c_() + ChatFormatting.RESET + " you're looking fine today :)";
      }

      this.mc.field_71466_p.func_175063_a(line, (float)this.scaled_width / 2.0F - (float)this.mc.field_71466_p.func_78256_a(line) / 2.0F, 20.0F, (new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a)).hex());
      this.set_width(this.get(line, "width") + 2);
      this.set_height(this.get(line, "height") + 2);
   }

   public void updateResolution() {
      this.scaled_width = this.mc.field_71443_c;
      this.scaled_height = this.mc.field_71440_d;
      this.scale_factor = 1;
      boolean flag = this.mc.func_152349_b();
      int i = this.mc.field_71474_y.field_74335_Z;
      if (i == 0) {
         i = 1000;
      }

      while(this.scale_factor < i && this.scaled_width / (this.scale_factor + 1) >= 320 && this.scaled_height / (this.scale_factor + 1) >= 240) {
         ++this.scale_factor;
      }

      if (flag && this.scale_factor % 2 != 0 && this.scale_factor != 1) {
         --this.scale_factor;
      }

      double scaledWidthD = (double)this.scaled_width / (double)this.scale_factor;
      double scaledHeightD = (double)this.scaled_height / (double)this.scale_factor;
      this.scaled_width = MathHelper.func_76143_f(scaledWidthD);
      this.scaled_height = MathHelper.func_76143_f(scaledHeightD);
   }
}
