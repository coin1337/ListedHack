package me.listed.listedhack.client.hacks.combat;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBlockInteractHelper;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusWebfill extends WurstplusHack {
   WurstplusSetting web_toggle = this.create("Toggle", "WebFillToggle", true);
   WurstplusSetting web_rotate = this.create("Rotate", "WebFillRotate", true);
   WurstplusSetting web_range = this.create("Range", "WebFillRange", 4, 1, 6);
   private final ArrayList<BlockPos> holes = new ArrayList();
   private boolean sneak;

   public WurstplusWebfill() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Web Fill";
      this.tag = "WebFill";
      this.description = "its like hole fill, but more annoying";
   }

   public void enable() {
      this.find_new_holes();
   }

   public void disable() {
      this.holes.clear();
   }

   public void update() {
      if (this.holes.isEmpty()) {
         if (!this.web_toggle.get_value(true)) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
         }

         this.find_new_holes();
      }

      BlockPos pos_to_fill = null;
      Iterator var2 = (new ArrayList(this.holes)).iterator();

      while(var2.hasNext()) {
         BlockPos pos = (BlockPos)var2.next();
         if (pos != null) {
            WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result == WurstplusBlockInteractHelper.ValidResult.Ok) {
               pos_to_fill = pos;
               break;
            }

            this.holes.remove(pos);
         }
      }

      int obi_slot = this.find_in_hotbar();
      if (pos_to_fill != null && obi_slot != -1) {
         int last_slot = mc.field_71439_g.field_71071_by.field_70461_c;
         mc.field_71439_g.field_71071_by.field_70461_c = obi_slot;
         mc.field_71442_b.func_78765_e();
         if (this.place_blocks(pos_to_fill)) {
            this.holes.remove(pos_to_fill);
         }

         mc.field_71439_g.field_71071_by.field_70461_c = last_slot;
      }

   }

   public void find_new_holes() {
      this.holes.clear();
      Iterator var1 = WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), (float)this.web_range.get_value(1), this.web_range.get_value(1), false, true, 0).iterator();

      while(true) {
         BlockPos pos;
         do {
            do {
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  pos = (BlockPos)var1.next();
               } while(!mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a));
            } while(!mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a));
         } while(!mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a));

         boolean possible = true;
         BlockPos[] var4 = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockPos seems_blocks = var4[var6];
            Block block = mc.field_71441_e.func_180495_p(pos.func_177971_a(seems_blocks)).func_177230_c();
            if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
               possible = false;
               break;
            }
         }

         if (possible) {
            this.holes.add(pos);
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

   private boolean place_blocks(BlockPos pos) {
      if (!mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
         return false;
      } else if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
         return false;
      } else {
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
               if (this.web_rotate.get_value(true)) {
                  WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
               }

               mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
               mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
               return true;
            }
         }

         return false;
      }
   }
}
