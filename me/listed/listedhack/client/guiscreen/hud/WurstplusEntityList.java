package me.listed.listedhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;

public class WurstplusEntityList extends WurstplusPinnable {
   public WurstplusEntityList() {
      super("Entity List", "EntityList", 1.0F, 0, 0);
   }

   public void render() {
      int counter = 12;
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      List<Entity> entity_list = new ArrayList(this.mc.field_71441_e.field_72996_f);
      if (entity_list.size() > 1) {
         Map<String, Integer> entity_counts = (Map)entity_list.stream().filter(Objects::nonNull).filter((ex) -> {
            return !(ex instanceof EntityPlayer);
         }).collect(Collectors.groupingBy(WurstplusEntityList::get_entity_name, Collectors.reducing(0, (ent) -> {
            return ent instanceof EntityItem ? ((EntityItem)ent).func_92059_d().func_190916_E() : 1;
         }, Integer::sum)));

         for(Iterator var8 = entity_counts.entrySet().iterator(); var8.hasNext(); counter += 12) {
            Entry<String, Integer> entity = (Entry)var8.next();
            String e = (String)entity.getKey() + " " + ChatFormatting.DARK_GRAY + "x" + entity.getValue();
            this.create_line(e, this.docking(1, e), 1 * counter, nl_r, nl_g, nl_b, nl_a);
         }

         this.set_width(this.get("aaaaaaaaaaaaaaa", "width") + 2);
         this.set_height(this.get("aaaaaaaaaaaaaaa", "height") * 5);
      }
   }

   private static String get_entity_name(@Nonnull Entity entity) {
      if (entity instanceof EntityItem) {
         return ((EntityItem)entity).func_92059_d().func_77973_b().func_77653_i(((EntityItem)entity).func_92059_d());
      } else if (entity instanceof EntityWitherSkull) {
         return "Wither skull";
      } else if (entity instanceof EntityEnderCrystal) {
         return "End crystal";
      } else if (entity instanceof EntityEnderPearl) {
         return "Thrown ender pearl";
      } else if (entity instanceof EntityMinecart) {
         return "Minecart";
      } else if (entity instanceof EntityItemFrame) {
         return "Item frame";
      } else if (entity instanceof EntityEgg) {
         return "Thrown egg";
      } else {
         return entity instanceof EntitySnowball ? "Thrown snowball" : entity.func_70005_c_();
      }
   }
}
