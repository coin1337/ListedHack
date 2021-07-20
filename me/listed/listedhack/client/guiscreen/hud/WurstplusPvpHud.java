package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WurstplusPvpHud extends WurstplusPinnable {
   public WurstplusPvpHud() {
      super("PVP Hud", "pvphud", 1.0F, 0, 0);
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      String totem = "Totems: " + this.get_totems();
      String trap = "Trap: " + this.trap_enabled();
      String aura = "Aura: " + this.aura_enabled();
      String surround = "Surround: " + this.surround_enabled();
      String holefill = "Holefill: " + this.holefill_enabled();
      String socks = "Socks: " + this.socks_enabled();
      String selftrap = "Self Trap: " + this.selftrap_enabled();
      this.create_line(totem, this.docking(1, totem), 2, nl_r, nl_g, nl_b, nl_a);
      this.create_line(aura, this.docking(1, aura), 13, nl_r, nl_g, nl_b, nl_a);
      this.create_line(trap, this.docking(1, trap), 24, nl_r, nl_g, nl_b, nl_a);
      this.create_line(surround, this.docking(1, surround), 34, nl_r, nl_g, nl_b, nl_a);
      this.create_line(holefill, this.docking(1, holefill), 45, nl_r, nl_g, nl_b, nl_a);
      this.create_line(socks, this.docking(1, socks), 56, nl_r, nl_g, nl_b, nl_a);
      this.create_line(selftrap, this.docking(1, selftrap), 67, nl_r, nl_g, nl_b, nl_a);
      this.set_width(this.get(surround, "width") + 2);
      this.set_height(this.get(surround, "height") * 5);
   }

   public String selftrap_enabled() {
      try {
         return ListedHack.get_hack_manager().get_module_with_tag("SelfTrap").is_active() ? "§a 1" : "§4 0";
      } catch (Exception var2) {
         return "0";
      }
   }

   public String trap_enabled() {
      try {
         return ListedHack.get_hack_manager().get_module_with_tag("Trap").is_active() ? "§a 1" : "§4 0";
      } catch (Exception var2) {
         return "0";
      }
   }

   public String aura_enabled() {
      try {
         return ListedHack.get_hack_manager().get_module_with_tag("AutoCrystal").is_active() ? "§a 1" : "§4 0";
      } catch (Exception var2) {
         return "0";
      }
   }

   public String socks_enabled() {
      try {
         return ListedHack.get_hack_manager().get_module_with_tag("Socks").is_active() ? "§a 1" : "§4 0";
      } catch (Exception var2) {
         return "0";
      }
   }

   public String surround_enabled() {
      try {
         return ListedHack.get_hack_manager().get_module_with_tag("Surround").is_active() ? "§a 1" : "§4 0";
      } catch (Exception var2) {
         return "0";
      }
   }

   public String holefill_enabled() {
      try {
         return ListedHack.get_hack_manager().get_module_with_tag("HoleFill").is_active() ? "§a 1" : "§4 0";
      } catch (Exception var2) {
         return "0";
      }
   }

   public String get_totems() {
      try {
         int totems = this.offhand() + this.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter((itemStack) -> {
            return itemStack.func_77973_b() == Items.field_190929_cY;
         }).mapToInt(ItemStack::func_190916_E).sum();
         return totems > 1 ? "§a " + totems : "§4 " + totems;
      } catch (Exception var2) {
         return "0";
      }
   }

   public int offhand() {
      return this.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY ? 1 : 0;
   }
}
