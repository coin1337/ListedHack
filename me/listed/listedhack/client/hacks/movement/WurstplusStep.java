package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusPlayerUtil;
import net.minecraft.network.play.client.CPacketPlayer.Position;

public class WurstplusStep extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "Mode", "Vanilla", this.combobox(new String[]{"Vanilla", "Spider", "Teleport"}));
   WurstplusSetting useTimer = this.create("Use Timer", "useTimer", true);
   WurstplusSetting timerTicks = this.create("Timer Speed", "TimerSpeed", 0.5D, 0.1D, 2.0D);
   WurstplusSetting entityStep = this.create("Entity Step", "entityStep", false);
   WurstplusSetting height = this.create("Height", "step height", 2.0D, 0.0D, 5.0D);
   WurstplusSetting sneakPause = this.create("Pause when Sneaking", "sneakPause", false);
   WurstplusSetting waterPause = this.create("Pause in Liquid", "waterPause", true);
   WurstplusSetting disable = this.create("Disable", "Disable", "Never", this.combobox(new String[]{"Never", "Completion", "Unsafe"}));
   double[] forwardStep;
   double originalHeight;

   public WurstplusStep() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "Step";
      this.tag = "Step";
      this.description = "Move up block big";
   }

   public void enable() {
      if (!this.nullCheck()) {
         this.originalHeight = mc.field_71439_g.field_70163_u;
         if (this.mode.in("Vanilla")) {
            mc.field_71439_g.field_70138_W = (float)this.height.get_value(1);
         }

      }
   }

   public void disable() {
      mc.field_71439_g.field_70138_W = 0.5F;
   }

   public void update() {
      if (!this.nullCheck()) {
         if (this.mode.in("Vanilla")) {
            mc.field_71439_g.field_70138_W = 0.5F;
         }

         if (mc.field_71439_g.field_70123_F) {
            if (!mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.field_71158_b.field_78901_c) {
               if (!mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab() || !this.waterPause.get_value(true)) {
                  if (this.useTimer.get_value(true)) {
                     mc.field_71428_T.field_194149_e = 50.0F / (float)this.timerTicks.get_value(1);
                  }

                  if (this.entityStep.get_value(true) && mc.field_71439_g.func_184218_aH()) {
                     mc.field_71439_g.func_184187_bx().field_70138_W = (float)this.height.get_value(1);
                  }

                  if (!mc.field_71439_g.func_70093_af() || !this.sneakPause.get_value(true)) {
                     this.forwardStep = WurstplusPlayerUtil.motion(0.1F);
                     if (this.getStepHeight().equals(WurstplusStep.StepHeight.Unsafe)) {
                        if (this.disable.in("Unsafe")) {
                           this.toggle();
                        }

                     } else {
                        if (this.mode.in("Teleport")) {
                           this.stepTeleport();
                        }

                        if (this.mode.in("Spider")) {
                           this.stepSpider();
                        }

                        if (this.mode.in("Vanilla")) {
                           this.stepVanilla();
                        }

                        if (mc.field_71439_g.field_70163_u > this.originalHeight + this.getStepHeight().height && this.disable.in("Completion")) {
                           this.toggle();
                        }

                     }
                  }
               }
            }
         }
      }
   }

   public void stepTeleport() {
      this.updateStepPackets(this.getStepHeight().stepArray);
      mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + this.getStepHeight().height, mc.field_71439_g.field_70161_v);
   }

   public void stepSpider() {
      this.updateStepPackets(this.getStepHeight().stepArray);
      mc.field_71439_g.field_70181_x = 0.2D;
      mc.field_71439_g.field_70143_R = 0.0F;
   }

   public void stepVanilla() {
      mc.field_71439_g.field_70138_W = (float)this.height.get_value(1);
   }

   public void updateStepPackets(double[] stepArray) {
      double[] var2 = stepArray;
      int var3 = stepArray.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         double v = var2[var4];
         mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + v, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
      }

   }

   public WurstplusStep.StepHeight getStepHeight() {
      if (mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 1.0D, this.forwardStep[1])).isEmpty() && !mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 0.6D, this.forwardStep[1])).isEmpty()) {
         return WurstplusStep.StepHeight.One;
      } else if (mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 1.6D, this.forwardStep[1])).isEmpty() && !mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 1.4D, this.forwardStep[1])).isEmpty()) {
         return WurstplusStep.StepHeight.OneHalf;
      } else if (mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 2.1D, this.forwardStep[1])).isEmpty() && !mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 1.9D, this.forwardStep[1])).isEmpty()) {
         return WurstplusStep.StepHeight.Two;
      } else {
         return mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 2.6D, this.forwardStep[1])).isEmpty() && !mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(this.forwardStep[0], 2.4D, this.forwardStep[1])).isEmpty() ? WurstplusStep.StepHeight.TwoHalf : WurstplusStep.StepHeight.Unsafe;
      }
   }

   public static enum StepHeight {
      One(1.0D, new double[]{0.42D, 0.753D}),
      OneHalf(1.5D, new double[]{0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D}),
      Two(2.0D, new double[]{0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D}),
      TwoHalf(2.5D, new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D}),
      Unsafe(3.0D, new double[]{0.0D});

      double[] stepArray;
      double height;

      private StepHeight(double height, double[] stepArray) {
         this.height = height;
         this.stepArray = stepArray;
      }
   }
}
