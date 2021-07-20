package me.listed.listedhack.client.hacks.combat;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusCrystalUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class WurstplusAntiCrystal extends WurstplusHack {
   int index;
   WurstplusSetting switch_mode = this.create("Mode", "Mode", "Normal", this.combobox(new String[]{"Normal", "Ghost", "None"}));
   WurstplusSetting max_self_damage = this.create("Max Self Damage", "MaxSelfDamage", 6, 0, 20);
   WurstplusSetting required_health = this.create("Required Health", "RequiredHealth", 12.0D, 1.0D, 36.0D);
   WurstplusSetting delay = this.create("Delay", "Delay", 2, 1, 20);
   WurstplusSetting range = this.create("Range", "Range", 4, 0, 10);
   WurstplusSetting rotate = this.create("Rotate", "Rotate", true);

   public WurstplusAntiCrystal() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Anti Crystal";
      this.tag = "AntiCrystal";
      this.description = "Places a pressure plate below crystals to remove crystal damage";
      this.index = 0;
   }

   public void update() {
      if (this.index > 2000) {
         this.index = 0;
      }

      if (this.find_in_hotbar() == -1) {
         WurstplusMessageUtil.send_client_message("No mats");
         this.set_disable();
      }

      if (!(mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() > (float)this.required_health.get_value(1))) {
         Iterator var1 = this.getExclusions().iterator();

         while(var1.hasNext()) {
            EntityEnderCrystal cry = (EntityEnderCrystal)var1.next();
            if (this.index % this.delay.get_value(1) == 0) {
               if (this.switch_mode.in("Normal")) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.find_in_hotbar();
               } else if (this.switch_mode.in("Ghost")) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketHeldItemChange(this.find_in_hotbar()));
               }

               if (mc.field_71439_g.field_71071_by.field_70461_c == this.find_in_hotbar()) {
                  WurstplusBlockInteractHelper.placeBlock(cry.func_180425_c(), this.rotate.get_value(true));
                  return;
               }
            }
         }

      }
   }

   public ArrayList<EntityEnderCrystal> getCrystals() {
      ArrayList<EntityEnderCrystal> crystals = new ArrayList();
      Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

      while(var2.hasNext()) {
         Entity ent = (Entity)var2.next();
         if (ent instanceof EntityEnderCrystal) {
            crystals.add((EntityEnderCrystal)ent);
         }
      }

      return crystals;
   }

   public ArrayList<EntityEnderCrystal> getInRange() {
      ArrayList<EntityEnderCrystal> inRange = new ArrayList();
      Iterator var2 = this.getCrystals().iterator();

      while(var2.hasNext()) {
         EntityEnderCrystal crystal = (EntityEnderCrystal)var2.next();
         if (mc.field_71439_g.func_70032_d(crystal) <= (float)this.range.get_value(1)) {
            inRange.add(crystal);
         }
      }

      return inRange;
   }

   public ArrayList<EntityEnderCrystal> getExclusions() {
      ArrayList<EntityEnderCrystal> returnOutput = new ArrayList();
      Iterator var2 = this.getInRange().iterator();

      while(var2.hasNext()) {
         EntityEnderCrystal crystal = (EntityEnderCrystal)var2.next();
         if (mc.field_71441_e.func_180495_p(crystal.func_180425_c()).func_177230_c() != Blocks.field_150452_aw && mc.field_71441_e.func_180495_p(crystal.func_180425_c()).func_177230_c() != Blocks.field_150456_au && mc.field_71441_e.func_180495_p(crystal.func_180425_c()).func_177230_c() != Blocks.field_150445_bS && mc.field_71441_e.func_180495_p(crystal.func_180425_c()).func_177230_c() != Blocks.field_150443_bT) {
            double self_damage = (double)WurstplusCrystalUtil.calculateDamage(crystal, mc.field_71439_g);
            if (!(self_damage < (double)this.max_self_damage.get_value(1))) {
               returnOutput.add(crystal);
            }
         }
      }

      return returnOutput;
   }

   private int find_in_hotbar() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block instanceof BlockPressurePlate) {
               return i;
            }
         }
      }

      return -1;
   }
}
