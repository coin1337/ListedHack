package me.listed.listedhack.client.hacks.movement;

import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.BurrowUtil;
import me.listed.listedhack.client.util.InventoryUtil;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class WurstplusInstantBurrow extends WurstplusHack {
   private BlockPos originalPos;
   private int oldSlot = -1;
   private int itemSwitch = 0;
   WurstplusSetting rotate = this.create("Rotate", "Rotate", false);
   WurstplusSetting packet = this.create("Packet", "Packet", true);
   WurstplusSetting offset = this.create("Offset", "Offset", 2.0D, -20.0D, 20.0D);

   public WurstplusInstantBurrow() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Burrow";
      this.tag = "Burrow";
      this.description = "rubberbands into a block";
   }

   public void enable() {
      if (!this.nullCheck()) {
         this.originalPos = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
         if (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c().equals(Blocks.field_150343_Z) || this.intersectsWithEntity(this.originalPos)) {
            this.toggle();
         }
      }
   }

   public void update() {
      if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1) {
         this.toggle();
      } else {
         ListedHack.get_hack_manager().get_module_with_tag("NoPush").set_active(true);
         int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         InventoryUtil.switchToSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class));
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.41999998688698D, mc.field_71439_g.field_70161_v, true));
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.7531999805211997D, mc.field_71439_g.field_70161_v, true));
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.00133597911214D, mc.field_71439_g.field_70161_v, true));
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.16610926093821D, mc.field_71439_g.field_70161_v, true));
         BurrowUtil.placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.get_value(true), this.packet.get_value(true), false);
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)this.offset.get_value(1), mc.field_71439_g.field_70161_v, false));
         InventoryUtil.switchToSlot(oldSlot);
         this.toggle();
      }
   }

   private boolean intersectsWithEntity(BlockPos pos) {
      Iterator var2 = mc.field_71441_e.field_72996_f.iterator();

      Entity entity;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         entity = (Entity)var2.next();
      } while(entity.equals(mc.field_71439_g) || entity instanceof EntityItem || !(new AxisAlignedBB(pos)).func_72326_a(entity.func_174813_aQ()));

      return true;
   }
}
