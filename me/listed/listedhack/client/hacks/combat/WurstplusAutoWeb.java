package me.listed.listedhack.client.hacks.combat;

import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusAutoWeb extends WurstplusHack {
   WurstplusSetting always_on = this.create("Always On", "AlwaysOn", true);
   WurstplusSetting rotate = this.create("Rotate", "AutoWebRotate", true);
   WurstplusSetting range = this.create("Enemy Range", "AutoWebRange", 4, 0, 8);
   int new_slot = -1;
   boolean sneak = false;

   public WurstplusAutoWeb() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Self Web";
      this.tag = "AutoSelfWeb";
      this.description = "places fuckin webs at ur feet";
   }

   public void enable() {
      if (mc.field_71439_g != null) {
         this.new_slot = this.find_in_hotbar();
         if (this.new_slot == -1) {
            WurstplusMessageUtil.send_client_error_message("cannot find webs in hotbar");
            this.set_active(false);
         }
      }

   }

   public void disable() {
      if (mc.field_71439_g != null && this.sneak) {
         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
         this.sneak = false;
      }

   }

   public void update() {
      if (mc.field_71439_g != null) {
         if (this.always_on.get_value(true)) {
            EntityPlayer target = this.find_closest_target();
            if (target == null) {
               return;
            }

            if (mc.field_71439_g.func_70032_d(target) < (float)this.range.get_value(1) && this.is_surround()) {
               int last_slot = mc.field_71439_g.field_71071_by.field_70461_c;
               mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
               mc.field_71442_b.func_78765_e();
               this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
               mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
            }
         } else {
            int last_slot = mc.field_71439_g.field_71071_by.field_70461_c;
            mc.field_71439_g.field_71071_by.field_70461_c = this.new_slot;
            mc.field_71442_b.func_78765_e();
            this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
            mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
            this.set_disable();
         }

      }
   }

   public EntityPlayer find_closest_target() {
      if (mc.field_71441_e.field_73010_i.isEmpty()) {
         return null;
      } else {
         EntityPlayer closestTarget = null;
         Iterator var2 = mc.field_71441_e.field_73010_i.iterator();

         while(true) {
            EntityPlayer target;
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var2.hasNext()) {
                              return closestTarget;
                           }

                           target = (EntityPlayer)var2.next();
                        } while(target == mc.field_71439_g);
                     } while(WurstplusFriendUtil.isFriend(target.func_70005_c_()));
                  } while(!WurstplusEntityUtil.isLiving(target));
               } while(target.func_110143_aJ() <= 0.0F);
            } while(closestTarget != null && mc.field_71439_g.func_70032_d(target) > mc.field_71439_g.func_70032_d(closestTarget));

            closestTarget = target;
         }
      }
   }

   private int find_in_hotbar() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (stack.func_77973_b() == Item.func_150899_d(30)) {
            return i;
         }
      }

      return -1;
   }

   private boolean is_surround() {
      BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
      return mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(player_block).func_177230_c() == Blocks.field_150350_a;
   }

   private void place_blocks(BlockPos pos) {
      if (mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
         if (WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            EnumFacing[] var2 = EnumFacing.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               EnumFacing side = var2[var4];
               BlockPos neighbor = pos.func_177972_a(side);
               EnumFacing side2 = side.func_176734_d();
               if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
                  if (WurstplusBlockInteractHelper.blackList.contains(mc.field_71441_e.func_180495_p(neighbor).func_177230_c())) {
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                     this.sneak = true;
                  }

                  Vec3d hitVec = (new Vec3d(neighbor)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(side2.func_176730_m())).func_186678_a(0.5D));
                  if (this.rotate.get_value(true)) {
                     WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
                  }

                  mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                  mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                  return;
               }
            }

         }
      }
   }
}
