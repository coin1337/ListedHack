package me.listed.listedhack.client.guiscreen.hud;

import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class WurstplusSurroundBlocks extends WurstplusPinnable {
   public WurstplusSurroundBlocks() {
      super("Surround Blocks", "SurroundBlocks", 1.0F, 0, 0);
   }

   public void render() {
      GlStateManager.func_179094_E();
      RenderHelper.func_74520_c();
      Block west = this.get_neg_x();
      Block east = this.get_pos_x();
      Block north = this.get_neg_z();
      Block south = this.get_pos_z();
      switch(WurstplusPlayerUtil.GetFacing()) {
      case North:
         west = this.get_neg_x();
         east = this.get_pos_x();
         north = this.get_neg_z();
         south = this.get_pos_z();
         break;
      case East:
         west = this.get_neg_z();
         east = this.get_pos_z();
         north = this.get_pos_x();
         south = this.get_neg_x();
         break;
      case South:
         west = this.get_pos_x();
         east = this.get_neg_x();
         north = this.get_pos_z();
         south = this.get_neg_z();
         break;
      case West:
         west = this.get_pos_z();
         east = this.get_neg_z();
         north = this.get_neg_x();
         south = this.get_pos_x();
      }

      this.mc.func_175599_af().func_180450_b(new ItemStack(west), this.get_x() - 20, this.get_y());
      this.mc.func_175599_af().func_180450_b(new ItemStack(east), this.get_x() + 20, this.get_y());
      this.mc.func_175599_af().func_180450_b(new ItemStack(north), this.get_x(), this.get_y() - 20);
      this.mc.func_175599_af().func_180450_b(new ItemStack(south), this.get_x(), this.get_y() + 20);
      RenderHelper.func_74518_a();
      GlStateManager.func_179121_F();
      this.set_width(50);
      this.set_height(25);
   }

   public Block get_neg_x() {
      BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
      Block b = this.mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c();
      return b != null ? b : null;
   }

   public Block get_pos_x() {
      BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
      Block b = this.mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c();
      return b != null ? b : null;
   }

   public Block get_pos_z() {
      BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
      Block b = this.mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c();
      return b != null ? b : null;
   }

   public Block get_neg_z() {
      BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
      Block b = this.mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c();
      return b != null ? b : null;
   }
}
