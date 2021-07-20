package me.listed.listedhack.client.util;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class WurstplusHoleUtil {
   public static final Minecraft mc = Minecraft.func_71410_x();

   public static WurstplusHoleUtil.BlockSafety isBlockSafe(Block block) {
      if (block == Blocks.field_150357_h) {
         return WurstplusHoleUtil.BlockSafety.UNBREAKABLE;
      } else {
         return block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ ? WurstplusHoleUtil.BlockSafety.BREAKABLE : WurstplusHoleUtil.BlockSafety.RESISTANT;
      }
   }

   public static WurstplusHoleUtil.HoleInfo isHole(BlockPos centreBlock, boolean onlyOneWide, boolean ignoreDown) {
      WurstplusHoleUtil.HoleInfo output = new WurstplusHoleUtil.HoleInfo();
      HashMap<WurstplusHoleUtil.BlockOffset, WurstplusHoleUtil.BlockSafety> unsafeSides = getUnsafeSides(centreBlock);
      if (unsafeSides.containsKey(WurstplusHoleUtil.BlockOffset.DOWN) && unsafeSides.remove(WurstplusHoleUtil.BlockOffset.DOWN, WurstplusHoleUtil.BlockSafety.BREAKABLE) && !ignoreDown) {
         output.setSafety(WurstplusHoleUtil.BlockSafety.BREAKABLE);
         return output;
      } else {
         int size = unsafeSides.size();
         unsafeSides.entrySet().removeIf((entry) -> {
            return entry.getValue() == WurstplusHoleUtil.BlockSafety.RESISTANT;
         });
         if (unsafeSides.size() != size) {
            output.setSafety(WurstplusHoleUtil.BlockSafety.RESISTANT);
         }

         size = unsafeSides.size();
         if (size == 0) {
            output.setType(WurstplusHoleUtil.HoleType.SINGLE);
            output.setCentre(new AxisAlignedBB(centreBlock));
            return output;
         } else if (size == 1 && !onlyOneWide) {
            return isDoubleHole(output, centreBlock, (WurstplusHoleUtil.BlockOffset)unsafeSides.keySet().stream().findFirst().get());
         } else {
            output.setSafety(WurstplusHoleUtil.BlockSafety.BREAKABLE);
            return output;
         }
      }
   }

   private static WurstplusHoleUtil.HoleInfo isDoubleHole(WurstplusHoleUtil.HoleInfo info, BlockPos centreBlock, WurstplusHoleUtil.BlockOffset weakSide) {
      BlockPos unsafePos = weakSide.offset(centreBlock);
      HashMap<WurstplusHoleUtil.BlockOffset, WurstplusHoleUtil.BlockSafety> unsafeSides = getUnsafeSides(unsafePos);
      int size = unsafeSides.size();
      unsafeSides.entrySet().removeIf((entry) -> {
         return entry.getValue() == WurstplusHoleUtil.BlockSafety.RESISTANT;
      });
      if (unsafeSides.size() != size) {
         info.setSafety(WurstplusHoleUtil.BlockSafety.RESISTANT);
      }

      if (unsafeSides.containsKey(WurstplusHoleUtil.BlockOffset.DOWN)) {
         info.setType(WurstplusHoleUtil.HoleType.CUSTOM);
         unsafeSides.remove(WurstplusHoleUtil.BlockOffset.DOWN);
      }

      if (unsafeSides.size() > 1) {
         info.setType(WurstplusHoleUtil.HoleType.NONE);
         return info;
      } else {
         double minX = (double)Math.min(centreBlock.func_177958_n(), unsafePos.func_177958_n());
         double maxX = (double)(Math.max(centreBlock.func_177958_n(), unsafePos.func_177958_n()) + 1);
         double minZ = (double)Math.min(centreBlock.func_177952_p(), unsafePos.func_177952_p());
         double maxZ = (double)(Math.max(centreBlock.func_177952_p(), unsafePos.func_177952_p()) + 1);
         info.setCentre(new AxisAlignedBB(minX, (double)centreBlock.func_177956_o(), minZ, maxX, (double)(centreBlock.func_177956_o() + 1), maxZ));
         if (info.getType() != WurstplusHoleUtil.HoleType.CUSTOM) {
            info.setType(WurstplusHoleUtil.HoleType.DOUBLE);
         }

         return info;
      }
   }

   public static HashMap<WurstplusHoleUtil.BlockOffset, WurstplusHoleUtil.BlockSafety> getUnsafeSides(BlockPos pos) {
      HashMap<WurstplusHoleUtil.BlockOffset, WurstplusHoleUtil.BlockSafety> output = new HashMap();
      WurstplusHoleUtil.BlockSafety temp = isBlockSafe(mc.field_71441_e.func_180495_p(WurstplusHoleUtil.BlockOffset.DOWN.offset(pos)).func_177230_c());
      if (temp != WurstplusHoleUtil.BlockSafety.UNBREAKABLE) {
         output.put(WurstplusHoleUtil.BlockOffset.DOWN, temp);
      }

      temp = isBlockSafe(mc.field_71441_e.func_180495_p(WurstplusHoleUtil.BlockOffset.NORTH.offset(pos)).func_177230_c());
      if (temp != WurstplusHoleUtil.BlockSafety.UNBREAKABLE) {
         output.put(WurstplusHoleUtil.BlockOffset.NORTH, temp);
      }

      temp = isBlockSafe(mc.field_71441_e.func_180495_p(WurstplusHoleUtil.BlockOffset.SOUTH.offset(pos)).func_177230_c());
      if (temp != WurstplusHoleUtil.BlockSafety.UNBREAKABLE) {
         output.put(WurstplusHoleUtil.BlockOffset.SOUTH, temp);
      }

      temp = isBlockSafe(mc.field_71441_e.func_180495_p(WurstplusHoleUtil.BlockOffset.EAST.offset(pos)).func_177230_c());
      if (temp != WurstplusHoleUtil.BlockSafety.UNBREAKABLE) {
         output.put(WurstplusHoleUtil.BlockOffset.EAST, temp);
      }

      temp = isBlockSafe(mc.field_71441_e.func_180495_p(WurstplusHoleUtil.BlockOffset.WEST.offset(pos)).func_177230_c());
      if (temp != WurstplusHoleUtil.BlockSafety.UNBREAKABLE) {
         output.put(WurstplusHoleUtil.BlockOffset.WEST, temp);
      }

      return output;
   }

   public static enum BlockOffset {
      DOWN(0, -1, 0),
      UP(0, 1, 0),
      NORTH(0, 0, -1),
      EAST(1, 0, 0),
      SOUTH(0, 0, 1),
      WEST(-1, 0, 0);

      private final int x;
      private final int y;
      private final int z;

      private BlockOffset(int x, int y, int z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }

      public BlockPos offset(BlockPos pos) {
         return pos.func_177982_a(this.x, this.y, this.z);
      }

      public BlockPos forward(BlockPos pos, int scale) {
         return pos.func_177982_a(this.x * scale, 0, this.z * scale);
      }

      public BlockPos backward(BlockPos pos, int scale) {
         return pos.func_177982_a(-this.x * scale, 0, -this.z * scale);
      }

      public BlockPos left(BlockPos pos, int scale) {
         return pos.func_177982_a(this.z * scale, 0, -this.x * scale);
      }

      public BlockPos right(BlockPos pos, int scale) {
         return pos.func_177982_a(-this.z * scale, 0, this.x * scale);
      }
   }

   public static class HoleInfo {
      private WurstplusHoleUtil.HoleType type;
      private WurstplusHoleUtil.BlockSafety safety;
      private AxisAlignedBB centre;

      public HoleInfo() {
         this(WurstplusHoleUtil.BlockSafety.UNBREAKABLE, WurstplusHoleUtil.HoleType.NONE);
      }

      public HoleInfo(WurstplusHoleUtil.BlockSafety safety, WurstplusHoleUtil.HoleType type) {
         this.type = type;
         this.safety = safety;
      }

      public void setType(WurstplusHoleUtil.HoleType type) {
         this.type = type;
      }

      public void setSafety(WurstplusHoleUtil.BlockSafety safety) {
         this.safety = safety;
      }

      public void setCentre(AxisAlignedBB centre) {
         this.centre = centre;
      }

      public WurstplusHoleUtil.HoleType getType() {
         return this.type;
      }

      public WurstplusHoleUtil.BlockSafety getSafety() {
         return this.safety;
      }

      public AxisAlignedBB getCentre() {
         return this.centre;
      }
   }

   public static enum HoleType {
      SINGLE,
      DOUBLE,
      CUSTOM,
      NONE;
   }

   public static enum BlockSafety {
      UNBREAKABLE,
      RESISTANT,
      BREAKABLE;
   }
}
