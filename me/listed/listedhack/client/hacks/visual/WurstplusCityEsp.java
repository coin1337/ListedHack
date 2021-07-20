package me.listed.listedhack.client.hacks.visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WurstplusCityEsp extends WurstplusHack {
   WurstplusSetting endcrystal_mode = this.create("EndCrystal", "CityEndCrystal", false);
   WurstplusSetting mode = this.create("Mode", "CityMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
   WurstplusSetting off_set = this.create("Height", "CityOffSetSide", 0.2D, 0.0D, 1.0D);
   WurstplusSetting range = this.create("Range", "CityRange", 6, 1, 12);
   WurstplusSetting r = this.create("R", "CityR", 0, 0, 255);
   WurstplusSetting g = this.create("G", "CityG", 255, 0, 255);
   WurstplusSetting b = this.create("B", "CityB", 0, 0, 255);
   WurstplusSetting a = this.create("A", "CityA", 50, 0, 255);
   List<BlockPos> blocks = new ArrayList();
   boolean outline = false;
   boolean solid = false;

   public WurstplusCityEsp() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "CityESP";
      this.tag = "City ESP";
      this.description = "jumpy isnt gonna be happy about this";
   }

   public void update() {
      this.blocks.clear();
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(var1.hasNext()) {
         EntityPlayer player = (EntityPlayer)var1.next();
         if (!(mc.field_71439_g.func_70032_d(player) > (float)this.range.get_value(1)) && mc.field_71439_g != player) {
            BlockPos p = WurstplusEntityUtil.is_cityable(player, this.endcrystal_mode.get_value(true));
            if (p != null) {
               this.blocks.add(p);
            }
         }
      }

   }

   public void render(WurstplusEventRender event) {
      float off_set_h = (float)this.off_set.get_value(1.0D);
      Iterator var3 = this.blocks.iterator();

      while(var3.hasNext()) {
         BlockPos pos = (BlockPos)var3.next();
         if (this.mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
         }

         if (this.mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
         }

         if (this.mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
         }

         if (this.solid) {
            RenderHelp.prepare("quads");
            RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), 1.0F, off_set_h, 1.0F, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
         }

         if (this.outline) {
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), 1.0F, off_set_h, 1.0F, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
         }
      }

   }
}
