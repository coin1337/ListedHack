package me.listed.listedhack.client.hacks.combat;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusBlockUtil;
import me.listed.listedhack.client.util.WurstplusMathUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSelfTrap extends WurstplusHack {
   WurstplusSetting toggle = this.create("Toggle", "SelfTrapToggle", false);
   WurstplusSetting rotate = this.create("Rotate", "SelfTrapRotate", false);
   WurstplusSetting swing = this.create("Swing", "SelfTrapSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
   private BlockPos trap_pos;

   public WurstplusSelfTrap() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Self Trap";
      this.tag = "SelfTrap";
      this.description = "oh 'eck, ive trapped me sen again";
   }

   protected void enable() {
      if (this.find_in_hotbar() == -1) {
         WurstplusMessageUtil.send_client_error_message("cannot find obby in hotbar");
         this.set_disable();
      }
   }

   public void update() {
      Vec3d pos = WurstplusMathUtil.interpolateEntity(mc.field_71439_g, mc.func_184121_ak());
      this.trap_pos = new BlockPos(pos.field_72450_a, pos.field_72448_b + 2.0D, pos.field_72449_c);
      if (this.is_trapped() && !this.toggle.get_value(true)) {
         this.toggle();
      } else {
         WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(this.trap_pos);
         if (result != WurstplusBlockInteractHelper.ValidResult.AlreadyBlockThere || mc.field_71441_e.func_180495_p(this.trap_pos).func_185904_a().func_76222_j()) {
            if (result == WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
               BlockPos[] tests = new BlockPos[]{this.trap_pos.func_177978_c(), this.trap_pos.func_177968_d(), this.trap_pos.func_177974_f(), this.trap_pos.func_177976_e(), this.trap_pos.func_177984_a(), this.trap_pos.func_177977_b().func_177976_e()};
               BlockPos[] var4 = tests;
               int var5 = tests.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  BlockPos pos_ = var4[var6];
                  WurstplusBlockInteractHelper.ValidResult result_ = WurstplusBlockInteractHelper.valid(pos_);
                  if (result_ != WurstplusBlockInteractHelper.ValidResult.NoNeighbors && result_ != WurstplusBlockInteractHelper.ValidResult.NoEntityCollision && WurstplusBlockUtil.placeBlock(pos_, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                     return;
                  }
               }

            } else {
               WurstplusBlockUtil.placeBlock(this.trap_pos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
            }
         }
      }
   }

   public boolean is_trapped() {
      if (this.trap_pos == null) {
         return false;
      } else {
         IBlockState state = mc.field_71441_e.func_180495_p(this.trap_pos);
         return state.func_177230_c() != Blocks.field_150350_a && state.func_177230_c() != Blocks.field_150355_j && state.func_177230_c() != Blocks.field_150353_l;
      }
   }

   private int find_in_hotbar() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block instanceof BlockEnderChest) {
               return i;
            }

            if (block instanceof BlockObsidian) {
               return i;
            }
         }
      }

      return -1;
   }
}
