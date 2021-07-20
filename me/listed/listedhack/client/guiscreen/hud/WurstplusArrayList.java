package me.listed.listedhack.client.guiscreen.hud;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusDrawnUtil;
import net.minecraft.util.math.MathHelper;

public class WurstplusArrayList extends WurstplusPinnable {
   boolean flag = true;
   private int scaled_width;
   private int scaled_height;
   private int scale_factor;

   public WurstplusArrayList() {
      super("Array List", "ArrayList", 1.0F, 0, 0);
   }

   public void render() {
      this.updateResolution();
      int position_update_y = 2;
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      List<WurstplusHack> pretty_modules = (List)ListedHack.get_hack_manager().get_array_active_hacks().stream().sorted(Comparator.comparing((modulesx) -> {
         return this.get(modulesx.array_detail() == null ? modulesx.get_tag() : modulesx.get_tag() + ListedHack.g + " [" + ListedHack.r + modulesx.array_detail() + ListedHack.g + "]" + ListedHack.r, "width");
      })).collect(Collectors.toList());
      int count = 0;
      if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R") || ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
         pretty_modules = Lists.reverse(pretty_modules);
      }

      Iterator var8 = pretty_modules.iterator();

      while(true) {
         WurstplusHack modules;
         do {
            if (!var8.hasNext()) {
               return;
            }

            modules = (WurstplusHack)var8.next();
            this.flag = true;
         } while(modules.get_category().get_tag().equals("WurstplusGUI"));

         Iterator var10 = WurstplusDrawnUtil.hidden_tags.iterator();

         while(var10.hasNext()) {
            String s = (String)var10.next();
            if (modules.get_tag().equalsIgnoreCase(s)) {
               this.flag = false;
               break;
            }

            if (!this.flag) {
               break;
            }
         }

         if (this.flag) {
            String module_name = modules.array_detail() == null ? modules.get_tag() : modules.get_tag() + ListedHack.g + " [" + ListedHack.r + modules.array_detail() + ListedHack.g + "]" + ListedHack.r;
            if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Free")) {
               this.create_line(module_name, this.docking(2, module_name), position_update_y, nl_r, nl_g, nl_b, nl_a);
               position_update_y += this.get(module_name, "height") + 2;
               if (this.get(module_name, "width") > this.get_width()) {
                  this.set_width(this.get(module_name, "width") + 2);
               }

               this.set_height(position_update_y);
            } else {
               if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R")) {
                  this.mc.field_71466_p.func_175063_a(module_name, (float)(this.scaled_width - 2 - this.mc.field_71466_p.func_78256_a(module_name)), (float)(3 + count * 10), (new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a)).hex());
                  ++count;
               }

               if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
                  this.mc.field_71466_p.func_175063_a(module_name, 2.0F, (float)(3 + count * 10), (new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a)).hex());
                  ++count;
               }

               if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom R")) {
                  this.mc.field_71466_p.func_175063_a(module_name, (float)(this.scaled_width - 2 - this.mc.field_71466_p.func_78256_a(module_name)), (float)(this.scaled_height - count * 10), (new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a)).hex());
                  ++count;
               }

               if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom L")) {
                  this.mc.field_71466_p.func_175063_a(module_name, 2.0F, (float)(this.scaled_height - count * 10), (new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a)).hex());
                  ++count;
               }
            }
         }
      }
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
