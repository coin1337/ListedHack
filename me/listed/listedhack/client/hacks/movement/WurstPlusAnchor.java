package me.listed.listedhack.client.hacks.movement;

import java.util.ArrayList;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventMotionUpdate;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstPlusAnchor extends WurstplusHack {
   WurstplusSetting Pitch = this.create("Pitch", "AnchorPitch", 60, 0, 90);
   WurstplusSetting Pull = this.create("Pull", "AnchorPull", true);
   private final ArrayList<BlockPos> holes = new ArrayList();
   int holeblocks;
   public static boolean AnchorING;
   private Vec3d Center;
   @EventHandler
   private Listener<WurstplusEventMotionUpdate> OnClientTick;

   public WurstPlusAnchor() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.Center = Vec3d.field_186680_a;
      this.OnClientTick = new Listener((event) -> {
         if (mc.field_71439_g.field_70125_A >= (float)this.Pitch.get_value(60)) {
            if (!this.isBlockHole(this.getPlayerPos().func_177979_c(1)) && !this.isBlockHole(this.getPlayerPos().func_177979_c(2)) && !this.isBlockHole(this.getPlayerPos().func_177979_c(3)) && !this.isBlockHole(this.getPlayerPos().func_177979_c(4))) {
               AnchorING = false;
            } else {
               AnchorING = true;
               if (!this.Pull.get_value(true)) {
                  mc.field_71439_g.field_70159_w = 0.0D;
                  mc.field_71439_g.field_70179_y = 0.0D;
               } else {
                  this.Center = this.GetCenter(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
                  double XDiff = Math.abs(this.Center.field_72450_a - mc.field_71439_g.field_70165_t);
                  double ZDiff = Math.abs(this.Center.field_72449_c - mc.field_71439_g.field_70161_v);
                  if (XDiff <= 0.1D && ZDiff <= 0.1D) {
                     this.Center = Vec3d.field_186680_a;
                  } else {
                     double MotionX = this.Center.field_72450_a - mc.field_71439_g.field_70165_t;
                     double MotionZ = this.Center.field_72449_c - mc.field_71439_g.field_70161_v;
                     mc.field_71439_g.field_70159_w = MotionX / 2.0D;
                     mc.field_71439_g.field_70179_y = MotionZ / 2.0D;
                  }
               }
            }
         }

      }, new Predicate[0]);
      this.name = "Anchor";
      this.tag = "WurstPlusAnchor";
      this.description = "Stops all movement if player is above a hole";
   }

   public boolean isBlockHole(BlockPos blockpos) {
      this.holeblocks = 0;
      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 3, 0)).func_177230_c() == Blocks.field_150350_a) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 2, 0)).func_177230_c() == Blocks.field_150350_a) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 1, 0)).func_177230_c() == Blocks.field_150350_a) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, 0)).func_177230_c() == Blocks.field_150350_a) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockpos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockpos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h) {
         ++this.holeblocks;
      }

      if (mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockpos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h) {
         ++this.holeblocks;
      }

      return this.holeblocks >= 9;
   }

   public Vec3d GetCenter(double posX, double posY, double posZ) {
      double x = Math.floor(posX) + 0.5D;
      double y = Math.floor(posY);
      double z = Math.floor(posZ) + 0.5D;
      return new Vec3d(x, y, z);
   }

   public void onDisable() {
      AnchorING = false;
      this.holeblocks = 0;
   }

   public BlockPos getPlayerPos() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }
}
