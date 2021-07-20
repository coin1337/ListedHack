package me.listed.listedhack.client.hacks.combat;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class WurstplusKillAura extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "KillAuraMode", "A32k", this.combobox(new String[]{"A32k", "Normal"}));
   WurstplusSetting player = this.create("Player", "KillAuraPlayer", true);
   WurstplusSetting hostile = this.create("Hostile", "KillAuraHostile", false);
   WurstplusSetting sword = this.create("Sword", "KillAuraSword", true);
   WurstplusSetting sync_tps = this.create("Sync TPS", "KillAuraSyncTps", true);
   WurstplusSetting range = this.create("Range", "KillAuraRange", 5.0D, 0.5D, 6.0D);
   WurstplusSetting delay = this.create("Delay", "KillAuraDelay", 2, 0, 10);
   boolean start_verify = true;
   EnumHand actual_hand;
   double tick;

   public WurstplusKillAura() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.actual_hand = EnumHand.MAIN_HAND;
      this.tick = 0.0D;
      this.name = "Kill Aura";
      this.tag = "KillAura";
      this.description = "To able hit enemies in a range.";
   }

   protected void enable() {
      this.tick = 0.0D;
   }

   public void update() {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         ++this.tick;
         if (mc.field_71439_g.field_70128_L | mc.field_71439_g.func_110143_aJ() <= 0.0F) {
            return;
         }

         Entity entity;
         if (this.mode.in("Normal")) {
            if (!(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && this.sword.get_value(true)) {
               this.start_verify = false;
            } else if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && this.sword.get_value(true)) {
               this.start_verify = true;
            } else if (!this.sword.get_value(true)) {
               this.start_verify = true;
            }

            entity = this.find_entity();
            if (entity != null && this.start_verify) {
               float tick_to_hit = 20.0F - ListedHack.get_event_handler().get_tick_rate();
               boolean is_possible_attack = mc.field_71439_g.func_184825_o(this.sync_tps.get_value(true) ? -tick_to_hit : 0.0F) >= 1.0F;
               if (is_possible_attack) {
                  this.attack_entity(entity);
               }
            }
         } else {
            if (!(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
               return;
            }

            if (this.tick < (double)this.delay.get_value(1)) {
               return;
            }

            this.tick = 0.0D;
            entity = this.find_entity();
            if (entity != null) {
               this.attack_entity(entity);
            }
         }
      }

   }

   public void attack_entity(Entity entity) {
      if (this.mode.in("A32k")) {
         int newSlot = -1;

         for(int i = 0; i < 9; ++i) {
            ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && this.checkSharpness(stack)) {
               newSlot = i;
               break;
            }
         }

         if (newSlot != -1) {
            mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
         }
      }

      ItemStack off_hand_item = mc.field_71439_g.func_184592_cb();
      if (off_hand_item.func_77973_b() == Items.field_185159_cQ) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
      }

      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketUseEntity(entity));
      mc.field_71439_g.func_184609_a(this.actual_hand);
      mc.field_71439_g.func_184821_cY();
   }

   public Entity find_entity() {
      Entity entity_requested = null;
      Iterator var2 = ((List)mc.field_71441_e.field_73010_i.stream().filter((entityPlayer) -> {
         return !WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_());
      }).collect(Collectors.toList())).iterator();

      while(var2.hasNext()) {
         Entity player = (Entity)var2.next();
         if (player != null && this.is_compatible(player) && (double)mc.field_71439_g.func_70032_d(player) <= this.range.get_value(1.0D)) {
            entity_requested = player;
         }
      }

      return entity_requested;
   }

   public boolean is_compatible(Entity entity) {
      if (this.player.get_value(true) && entity instanceof EntityPlayer && entity != mc.field_71439_g && !entity.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
         return true;
      } else if (this.hostile.get_value(true) && entity instanceof IMob) {
         return true;
      } else {
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase entity_living_base = (EntityLivingBase)entity;
            if (entity_living_base.func_110143_aJ() <= 0.0F) {
               return false;
            }
         }

         return false;
      }
   }

   private boolean checkSharpness(ItemStack stack) {
      if (stack.func_77978_p() == null) {
         return false;
      } else {
         NBTTagList enchants = (NBTTagList)stack.func_77978_p().func_74781_a("ench");
         if (enchants == null) {
            return false;
         } else {
            for(int i = 0; i < enchants.func_74745_c(); ++i) {
               NBTTagCompound enchant = enchants.func_150305_b(i);
               if (enchant.func_74762_e("id") == 16) {
                  int lvl = enchant.func_74762_e("lvl");
                  if (lvl > 5) {
                     return true;
                  }
                  break;
               }
            }

            return false;
         }
      }
   }
}
