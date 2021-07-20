package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class WurstplusEventBlock extends WurstplusEventCancellable {
   public BlockPos pos;
   public EnumFacing facing;
   private int stage;

   public WurstplusEventBlock(int stage, BlockPos pos, EnumFacing facing) {
      this.pos = pos;
      this.facing = facing;
      this.stage = stage;
   }

   public void set_stage(int stage) {
      this.stage = stage;
   }

   public int get_stage() {
      return this.stage;
   }
}
