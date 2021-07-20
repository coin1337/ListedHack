package me.listed.listedhack.client.hacks.visual;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusCrystalUtil;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WurstplusFuckedDetector extends WurstplusHack {
   WurstplusSetting draw_own = this.create("Draw Own", "FuckedDrawOwn", false);
   WurstplusSetting draw_friends = this.create("Draw Friends", "FuckedDrawFriends", false);
   WurstplusSetting render_mode = this.create("Render Mode", "FuckedRenderMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
   WurstplusSetting r = this.create("R", "FuckedR", 255, 0, 255);
   WurstplusSetting g = this.create("G", "FuckedG", 255, 0, 255);
   WurstplusSetting b = this.create("B", "FuckedB", 255, 0, 255);
   WurstplusSetting a = this.create("A", "FuckedA", 100, 0, 255);
   private boolean solid;
   private boolean outline;
   public Set<BlockPos> fucked_players = new HashSet();

   public WurstplusFuckedDetector() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Detector";
      this.tag = "Detector";
      this.description = "see if people are hecked";
   }

   protected void enable() {
      this.fucked_players.clear();
   }

   public void update() {
      if (mc.field_71441_e != null) {
         this.set_fucked_players();
      }
   }

   public void set_fucked_players() {
      this.fucked_players.clear();
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(true) {
         EntityPlayer player;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var1.hasNext()) {
                           return;
                        }

                        player = (EntityPlayer)var1.next();
                     } while(!WurstplusEntityUtil.isLiving(player));
                  } while(player.func_110143_aJ() <= 0.0F);
               } while(!this.is_fucked(player));
            } while(WurstplusFriendUtil.isFriend(player.func_70005_c_()) && !this.draw_friends.get_value(true));
         } while(player == mc.field_71439_g && !this.draw_own.get_value(true));

         this.fucked_players.add(new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v));
      }
   }

   public boolean is_fucked(EntityPlayer player) {
      BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u - 1.0D, player.field_70161_v);
      if (!WurstplusCrystalUtil.canPlaceCrystal(pos.func_177968_d()) && (!WurstplusCrystalUtil.canPlaceCrystal(pos.func_177968_d().func_177968_d()) || mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 1)).func_177230_c() != Blocks.field_150350_a)) {
         if (WurstplusCrystalUtil.canPlaceCrystal(pos.func_177974_f()) || WurstplusCrystalUtil.canPlaceCrystal(pos.func_177974_f().func_177974_f()) && mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 1, 0)).func_177230_c() == Blocks.field_150350_a) {
            return true;
         } else if (!WurstplusCrystalUtil.canPlaceCrystal(pos.func_177976_e()) && (!WurstplusCrystalUtil.canPlaceCrystal(pos.func_177976_e().func_177976_e()) || mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 1, 0)).func_177230_c() != Blocks.field_150350_a)) {
            return WurstplusCrystalUtil.canPlaceCrystal(pos.func_177978_c()) || WurstplusCrystalUtil.canPlaceCrystal(pos.func_177978_c().func_177978_c()) && mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, -1)).func_177230_c() == Blocks.field_150350_a;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public void render(WurstplusEventRender event) {
      if (this.render_mode.in("Pretty")) {
         this.outline = true;
         this.solid = true;
      }

      if (this.render_mode.in("Solid")) {
         this.outline = false;
         this.solid = true;
      }

      if (this.render_mode.in("Outline")) {
         this.outline = true;
         this.solid = false;
      }

      Iterator var2 = this.fucked_players.iterator();

      while(var2.hasNext()) {
         BlockPos render_block = (BlockPos)var2.next();
         if (render_block == null) {
            return;
         }

         if (this.solid) {
            RenderHelp.prepare("quads");
            RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), 1.0F, 1.0F, 1.0F, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
         }

         if (this.outline) {
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)render_block.func_177958_n(), (float)render_block.func_177956_o(), (float)render_block.func_177952_p(), 1.0F, 1.0F, 1.0F, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
         }
      }

   }
}
