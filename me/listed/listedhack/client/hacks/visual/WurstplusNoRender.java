package me.listed.listedhack.client.hacks.visual;

import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityWitherSkull;

public class WurstplusNoRender extends WurstplusHack {
   WurstplusSetting items = this.create("Items", "Items", false);
   WurstplusSetting withers = this.create("Withers", "Withers", true);
   WurstplusSetting wither_skulls = this.create("Wither Skulls", "WitherSkulls", true);
   WurstplusSetting sand = this.create("Sand", "Sand", true);
   WurstplusSetting firework_rocket = this.create("Firework Rocket", "FireworksRockets", true);

   public WurstplusNoRender() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "NoRender";
      this.tag = "NoRender";
      this.description = "Doesnt render certain shit";
   }

   public void update() {
      Iterator var1;
      Entity e;
      if (this.items.get_value(true)) {
         var1 = mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            e = (Entity)var1.next();
            if (e instanceof EntityItem) {
               mc.field_71441_e.func_72900_e(e);
            }
         }
      }

      if (this.withers.get_value(true)) {
         var1 = mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            e = (Entity)var1.next();
            if (e instanceof EntityWither) {
               mc.field_71441_e.func_72900_e(e);
            }
         }
      }

      if (this.firework_rocket.get_value(true)) {
         var1 = mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            e = (Entity)var1.next();
            if (e instanceof EntityFireworkRocket) {
               mc.field_71441_e.func_72900_e(e);
            }
         }
      }

      if (this.wither_skulls.get_value(true)) {
         var1 = mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            e = (Entity)var1.next();
            if (e instanceof EntityWitherSkull) {
               mc.field_71441_e.func_72900_e(e);
            }
         }
      }

      if (this.sand.get_value(true)) {
         var1 = mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            e = (Entity)var1.next();
            if (e instanceof EntityFallingBlock) {
               mc.field_71441_e.func_72900_e(e);
            }
         }
      }

   }
}
