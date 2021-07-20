package me.listed.listedhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class WurstplusEffectHud extends WurstplusPinnable {
   public WurstplusEffectHud() {
      super("Effect Hud", "effecthud", 1.0F, 0, 0);
   }

   public void render() {
      int counter = 12;
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      List<PotionEffect> effects = new ArrayList(this.mc.field_71439_g.func_70651_bq());
      Comparator<PotionEffect> comparator = (first, second) -> {
         String first_effect = get_friendly_potion_name(first) + " " + ChatFormatting.GRAY + Potion.func_188410_a(first, 1.0F);
         String second_effect = get_friendly_potion_name(second) + " " + ChatFormatting.GRAY + Potion.func_188410_a(second, 1.0F);
         float dif = (float)(this.mc.field_71466_p.func_78256_a(second_effect) - this.mc.field_71466_p.func_78256_a(first_effect));
         return dif != 0.0F ? (int)dif : second_effect.compareTo(first_effect);
      };
      effects.sort(comparator);
      Iterator var8 = effects.iterator();

      while(var8.hasNext()) {
         PotionEffect effect = (PotionEffect)var8.next();
         String e;
         if (effect.func_188419_a() == MobEffects.field_76420_g) {
            e = ChatFormatting.DARK_RED + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.func_188410_a(effect, 1.0F);
            this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
         } else if (effect.func_188419_a() == MobEffects.field_76424_c) {
            e = ChatFormatting.BLUE + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.func_188410_a(effect, 1.0F);
            this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
         } else if (effect.func_188419_a() == MobEffects.field_76437_t) {
            e = ChatFormatting.GRAY + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.func_188410_a(effect, 1.0F);
            this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
         } else if (effect.func_188419_a() == MobEffects.field_76430_j) {
            e = ChatFormatting.GREEN + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.func_188410_a(effect, 1.0F);
            this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
         } else if (ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDAllPotions").get_value(true)) {
            e = get_friendly_potion_name(effect) + " " + Potion.func_188410_a(effect, 1.0F);
            this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
         }
      }

      this.set_width(this.get("weakness", "width") + 12);
      this.set_height(this.get("weakness", "height") + 36);
   }

   public static String get_friendly_potion_name(PotionEffect potionEffect) {
      String effectName = I18n.func_135052_a(potionEffect.func_188419_a().func_76393_a(), new Object[0]);
      if (potionEffect.func_76458_c() == 1) {
         effectName = effectName + " " + I18n.func_135052_a("enchantment.level.2", new Object[0]);
      } else if (potionEffect.func_76458_c() == 2) {
         effectName = effectName + " " + I18n.func_135052_a("enchantment.level.3", new Object[0]);
      } else if (potionEffect.func_76458_c() == 3) {
         effectName = effectName + " " + I18n.func_135052_a("enchantment.level.4", new Object[0]);
      }

      return effectName;
   }

   public static String get_name_duration_string(PotionEffect potionEffect) {
      return String.format("%s (%s)", get_friendly_potion_name(potionEffect), Potion.func_188410_a(potionEffect, 1.0F));
   }
}
