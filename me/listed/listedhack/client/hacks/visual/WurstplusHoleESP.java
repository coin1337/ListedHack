package me.listed.listedhack.client.hacks.visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusPair;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WurstplusHoleESP extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "HoleESPMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
   WurstplusSetting off_set = this.create("Height", "HoleESPOffSetSide", 0.2D, 0.0D, 1.0D);
   WurstplusSetting range = this.create("Range", "HoleESPRange", 6, 1, 12);
   WurstplusSetting hide_own = this.create("Hide Own", "HoleESPHideOwn", true);
   WurstplusSetting bedrock_view = this.create("info", "HoleESPbedrock", "Bedrock");
   WurstplusSetting bedrock_enable = this.create("Bedrock Holes", "HoleESPBedrockHoles", true);
   WurstplusSetting rb = this.create("R", "HoleESPRb", 0, 0, 255);
   WurstplusSetting gb = this.create("G", "HoleESPGb", 255, 0, 255);
   WurstplusSetting bb = this.create("B", "HoleESPBb", 0, 0, 255);
   WurstplusSetting ab = this.create("A", "HoleESPAb", 50, 0, 255);
   WurstplusSetting obsidian_view = this.create("info", "HoleESPObsidian", "Obsidian");
   WurstplusSetting obsidian_enable = this.create("Obsidian Holes", "HoleESPObsidianHoles", true);
   WurstplusSetting ro = this.create("R", "HoleESPRo", 255, 0, 255);
   WurstplusSetting go = this.create("G", "HoleESPGo", 0, 0, 255);
   WurstplusSetting bo = this.create("B", "HoleESPBo", 0, 0, 255);
   WurstplusSetting ao = this.create("A", "HoleESPAo", 50, 0, 255);
   WurstplusSetting line_a = this.create("Outline A", "HoleESPLineOutlineA", 255, 0, 255);
   ArrayList<WurstplusPair<BlockPos, Boolean>> holes = new ArrayList();
   boolean outline = false;
   boolean solid = false;
   boolean docking = false;
   int color_r_o;
   int color_g_o;
   int color_b_o;
   int color_r_b;
   int color_g_b;
   int color_b_b;
   int color_r;
   int color_g;
   int color_b;
   int color_a;
   int safe_sides;

   public WurstplusHoleESP() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "HoleESP";
      this.tag = "HoleESP";
      this.description = "lets you know where holes are";
   }

   public void update() {
      this.color_r_b = this.rb.get_value(1);
      this.color_g_b = this.gb.get_value(1);
      this.color_b_b = this.bb.get_value(1);
      this.color_r_o = this.ro.get_value(1);
      this.color_g_o = this.go.get_value(1);
      this.color_b_o = this.bo.get_value(1);
      this.holes.clear();
      if (mc.field_71439_g != null || mc.field_71441_e != null) {
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

         int colapso_range = (int)Math.ceil((double)this.range.get_value(1));
         List<BlockPos> spheres = this.sphere(this.player_as_blockpos(), (float)colapso_range, colapso_range);
         Iterator var3 = spheres.iterator();

         while(true) {
            BlockPos pos;
            do {
               do {
                  do {
                     if (!var3.hasNext()) {
                        return;
                     }

                     pos = (BlockPos)var3.next();
                  } while(!mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a));
               } while(!mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a));
            } while(!mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a));

            boolean possible = true;
            this.safe_sides = 0;
            BlockPos[] var6 = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               BlockPos seems_blocks = var6[var8];
               Block block = mc.field_71441_e.func_180495_p(pos.func_177971_a(seems_blocks)).func_177230_c();
               if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                  possible = false;
                  break;
               }

               if (block == Blocks.field_150357_h) {
                  ++this.safe_sides;
               }
            }

            if (possible) {
               if (this.safe_sides == 5) {
                  if (this.bedrock_enable.get_value(true)) {
                     this.holes.add(new WurstplusPair(pos, true));
                  }
               } else if (this.obsidian_enable.get_value(true)) {
                  this.holes.add(new WurstplusPair(pos, false));
               }
            }
         }
      }
   }

   public void render(WurstplusEventRender event) {
      float off_set_h = 0.0F;
      if (!this.holes.isEmpty()) {
         off_set_h = (float)this.off_set.get_value(1.0D);
         Iterator var3 = this.holes.iterator();

         while(true) {
            WurstplusPair hole;
            label37:
            do {
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  hole = (WurstplusPair)var3.next();
                  if ((Boolean)hole.getValue()) {
                     this.color_r = this.color_r_b;
                     this.color_g = this.color_g_b;
                     this.color_b = this.color_b_b;
                     this.color_a = this.ab.get_value(1);
                     continue label37;
                  }
               } while((Boolean)hole.getValue());

               this.color_r = this.color_r_o;
               this.color_g = this.color_g_o;
               this.color_b = this.color_b_o;
               this.color_a = this.ao.get_value(1);
            } while(this.hide_own.get_value(true) && ((BlockPos)hole.getKey()).equals(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)));

            if (this.solid) {
               RenderHelp.prepare("quads");
               RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)((BlockPos)hole.getKey()).func_177958_n(), (float)((BlockPos)hole.getKey()).func_177956_o(), (float)((BlockPos)hole.getKey()).func_177952_p(), 1.0F, off_set_h, 1.0F, this.color_r, this.color_g, this.color_b, this.color_a, "all");
               RenderHelp.release();
            }

            if (this.outline) {
               RenderHelp.prepare("lines");
               RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)((BlockPos)hole.getKey()).func_177958_n(), (float)((BlockPos)hole.getKey()).func_177956_o(), (float)((BlockPos)hole.getKey()).func_177952_p(), 1.0F, off_set_h, 1.0F, this.color_r, this.color_g, this.color_b, this.line_a.get_value(1), "all");
               RenderHelp.release();
            }
         }
      }
   }

   public List<BlockPos> sphere(BlockPos pos, float r, int h) {
      boolean hollow = false;
      boolean sphere = true;
      int plus_y = 0;
      List<BlockPos> sphere_block = new ArrayList();
      int cx = pos.func_177958_n();
      int cy = pos.func_177956_o();
      int cz = pos.func_177952_p();

      for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
         for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
            for(int y = sphere ? cy - (int)r : cy; (float)y < (sphere ? (float)cy + r : (float)(cy + h)); ++y) {
               double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
               if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0F) * (r - 1.0F)))) {
                  BlockPos spheres = new BlockPos(x, y + plus_y, z);
                  sphere_block.add(spheres);
               }
            }
         }
      }

      return sphere_block;
   }

   public BlockPos player_as_blockpos() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }
}
