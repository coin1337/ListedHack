package me.listed.listedhack.client.hacks.movement;

import java.util.function.Predicate;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.events.WurstplusEventPlayerTravel;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMathUtil;
import me.listed.listedhack.client.util.WurstplusTimer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;

public final class WurstplusElytraFly extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "ElytraFlyMode", "Creative", this.combobox(new String[]{"Creative", "Packet", "Control", "None"}));
   WurstplusSetting speed = this.create("Speed", "ElytraFlySpeed", 1.8200000524520874D, 0.0D, 10.0D);
   WurstplusSetting DownSpeed = this.create("DownSpeed", "ElytraFlyDownSpeed", 1.8200000524520874D, 0.0D, 10.0D);
   WurstplusSetting GlideSpeed = this.create("GlideSpeed", "ElytraFlyGlideSpeed", 1.0D, 0.0D, 10.0D);
   WurstplusSetting UpSpeed = this.create("UpSpeed", "ElytraFlyUpSpeed", 2.0D, 0.0D, 10.0D);
   WurstplusSetting Accelerate = this.create("Accelerate", "ElytraFlyAccelerate", true);
   WurstplusSetting vAccelerationTimer = this.create("Acceleration Timer", "ElytraFlyTimer", 1000, 0, 10000);
   WurstplusSetting RotationPitch = this.create("RotationPitch", "ElytraPitch", 0.0D, -90.0D, 90.0D);
   WurstplusSetting InstantFly = this.create("InstantFly", "ElytraFlyInstant", true);
   WurstplusSetting EquipElytra = this.create("EquipElytra", "ElytraFlyEquip", false);
   WurstplusSetting use_timer = this.create("Use Timer", "UseTimer", false);
   WurstplusSetting timer_speed = this.create("Timer Speed", "TimerSpeed", 0.01D, 0.0D, 4.0D);
   private WurstplusTimer AccelerationTimer = new WurstplusTimer();
   private WurstplusTimer AccelerationResetTimer = new WurstplusTimer();
   private WurstplusTimer InstantFlyTimer = new WurstplusTimer();
   private boolean has_elytra;
   private int ElytraSlot = -1;
   @EventHandler
   private Listener<WurstplusEventPlayerTravel> OnTravel = new Listener((p_Event) -> {
      if (mc.field_71439_g != null) {
         if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_185160_cR) {
            if (!mc.field_71439_g.func_184613_cA()) {
               if (!mc.field_71439_g.field_70122_E && this.InstantFly.get_value(true)) {
                  if (!this.InstantFlyTimer.passed(1000L)) {
                     return;
                  }

                  this.InstantFlyTimer.reset();
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_FALL_FLYING));
               }

            } else {
               if (this.mode.in("Packet")) {
                  this.HandleNormalModeElytra(p_Event);
               }

               if (this.mode.in("Creative")) {
                  this.HandleImmediateModeElytra(p_Event);
               }

               if (this.mode.in("Control")) {
                  this.HandleControlMode(p_Event);
               }

            }
         }
      }
   }, new Predicate[0]);

   public WurstplusElytraFly() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "ElytraFly";
      this.tag = "ElytraFly";
      this.description = "Elytra flight";
   }

   public void update() {
      ListedHack.get_hack_manager().get_module_with_tag("NoFall").set_active(false);
      if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_185160_cR) {
         this.has_elytra = true;
      } else {
         this.has_elytra = false;
      }

      if (this.use_timer.get_value(true) && !mc.field_71439_g.func_184613_cA() && mc.field_71439_g.func_110143_aJ() > 0.0F && this.has_elytra) {
         mc.field_71428_T.field_194149_e = 50.0F / ((float)this.timer_speed.get_value(1) == 0.0F ? 0.1F : (float)this.timer_speed.get_value(1));
      } else {
         mc.field_71428_T.field_194149_e = 50.0F;
      }

   }

   protected void enable() {
      this.ElytraSlot = -1;
      if (this.EquipElytra.get_value(true) && mc.field_71439_g != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() != Items.field_185160_cR) {
         for(int l_I = 0; l_I < 44; ++l_I) {
            ItemStack l_Stack = mc.field_71439_g.field_71071_by.func_70301_a(l_I);
            if (!l_Stack.func_190926_b() && l_Stack.func_77973_b() == Items.field_185160_cR) {
               ItemElytra l_Elytra = (ItemElytra)l_Stack.func_77973_b();
               this.ElytraSlot = l_I;
               break;
            }
         }

         if (this.ElytraSlot != -1) {
            boolean l_HasArmorAtChest = mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() != Items.field_190931_a;
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, this.ElytraSlot, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 6, 0, ClickType.PICKUP, mc.field_71439_g);
            if (l_HasArmorAtChest) {
               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, this.ElytraSlot, 0, ClickType.PICKUP, mc.field_71439_g);
            }
         }
      }

   }

   protected void disable() {
      mc.field_71428_T.field_194149_e = 50.0F;
      if (mc.field_71439_g != null) {
         if (this.ElytraSlot != -1) {
            boolean l_HasItem = !mc.field_71439_g.field_71071_by.func_70301_a(this.ElytraSlot).func_190926_b() || mc.field_71439_g.field_71071_by.func_70301_a(this.ElytraSlot).func_77973_b() != Items.field_190931_a;
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 6, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, this.ElytraSlot, 0, ClickType.PICKUP, mc.field_71439_g);
            if (l_HasItem) {
               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 6, 0, ClickType.PICKUP, mc.field_71439_g);
            }
         }

      }
   }

   public void HandleNormalModeElytra(WurstplusEventPlayerTravel p_Travel) {
      double l_YHeight = mc.field_71439_g.field_70163_u;
      boolean l_IsMoveKeyDown = mc.field_71439_g.field_71158_b.field_192832_b > 0.0F || mc.field_71439_g.field_71158_b.field_78902_a > 0.0F;
      if (mc.field_71439_g.field_71158_b.field_78901_c) {
         p_Travel.cancel();
         this.Accelerate();
      } else {
         if (!l_IsMoveKeyDown) {
            this.AccelerationTimer.resetTimeSkipTo((long)(-this.vAccelerationTimer.get_value(1)));
         } else if (mc.field_71439_g.field_70125_A <= (float)this.RotationPitch.get_value(1)) {
            if (this.Accelerate.get_value(true) && this.AccelerationTimer.passed((long)this.vAccelerationTimer.get_value(1))) {
               this.Accelerate();
               return;
            }

            return;
         }

         p_Travel.cancel();
         this.Accelerate();
      }
   }

   public void HandleImmediateModeElytra(WurstplusEventPlayerTravel p_Travel) {
      if (mc.field_71439_g.field_71158_b.field_78901_c) {
         double l_MotionSq = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
         if (!(l_MotionSq > 1.0D)) {
            double[] dir = WurstplusMathUtil.directionSpeedNoForward((double)this.speed.get_value(1));
            mc.field_71439_g.field_70159_w = dir[0];
            mc.field_71439_g.field_70181_x = (double)(-((float)this.GlideSpeed.get_value(1) / 10000.0F));
            mc.field_71439_g.field_70179_y = dir[1];
            p_Travel.cancel();
         }
      } else {
         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         p_Travel.cancel();
         double[] dir = WurstplusMathUtil.directionSpeed((double)this.speed.get_value(1));
         if (mc.field_71439_g.field_71158_b.field_78902_a != 0.0F || mc.field_71439_g.field_71158_b.field_192832_b != 0.0F) {
            mc.field_71439_g.field_70159_w = dir[0];
            mc.field_71439_g.field_70181_x = (double)(-((float)this.GlideSpeed.get_value(1) / 10000.0F));
            mc.field_71439_g.field_70179_y = dir[1];
         }

         if (mc.field_71439_g.field_71158_b.field_78899_d) {
            mc.field_71439_g.field_70181_x = (double)(-this.DownSpeed.get_value(1));
         }

         mc.field_71439_g.field_184618_aE = 0.0F;
         mc.field_71439_g.field_70721_aZ = 0.0F;
         mc.field_71439_g.field_184619_aG = 0.0F;
      }
   }

   public void Accelerate() {
      if (this.AccelerationResetTimer.passed((long)this.vAccelerationTimer.get_value(1))) {
         this.AccelerationResetTimer.reset();
         this.AccelerationTimer.reset();
      }

      float l_Speed = (float)this.speed.get_value(1);
      double[] dir = WurstplusMathUtil.directionSpeed((double)l_Speed);
      mc.field_71439_g.field_70181_x = (double)(-((float)this.GlideSpeed.get_value(1) / 10000.0F));
      if (mc.field_71439_g.field_71158_b.field_78902_a == 0.0F && mc.field_71439_g.field_71158_b.field_192832_b == 0.0F) {
         mc.field_71439_g.field_70159_w = 0.0D;
         mc.field_71439_g.field_70179_y = 0.0D;
      } else {
         mc.field_71439_g.field_70159_w = dir[0];
         mc.field_71439_g.field_70179_y = dir[1];
      }

      if (mc.field_71439_g.field_71158_b.field_78899_d) {
         mc.field_71439_g.field_70181_x = (double)(-this.DownSpeed.get_value(1));
      }

      mc.field_71439_g.field_184618_aE = 0.0F;
      mc.field_71439_g.field_70721_aZ = 0.0F;
      mc.field_71439_g.field_184619_aG = 0.0F;
   }

   private void HandleControlMode(WurstplusEventPlayerTravel p_Event) {
      double[] dir = WurstplusMathUtil.directionSpeed((double)this.speed.get_value(1));
      if (mc.field_71439_g.field_71158_b.field_78902_a == 0.0F && mc.field_71439_g.field_71158_b.field_192832_b == 0.0F) {
         mc.field_71439_g.field_70159_w = 0.0D;
         mc.field_71439_g.field_70179_y = 0.0D;
      } else {
         mc.field_71439_g.field_70159_w = dir[0];
         mc.field_71439_g.field_70179_y = dir[1];
         EntityPlayerSP var10000 = mc.field_71439_g;
         var10000.field_70159_w -= mc.field_71439_g.field_70159_w * (double)(Math.abs(mc.field_71439_g.field_70125_A) + 90.0F) / 90.0D - mc.field_71439_g.field_70159_w;
         var10000 = mc.field_71439_g;
         var10000.field_70179_y -= mc.field_71439_g.field_70179_y * (double)(Math.abs(mc.field_71439_g.field_70125_A) + 90.0F) / 90.0D - mc.field_71439_g.field_70179_y;
      }

      mc.field_71439_g.field_70181_x = -WurstplusMathUtil.degToRad((double)mc.field_71439_g.field_70125_A) * (double)mc.field_71439_g.field_71158_b.field_192832_b;
      mc.field_71439_g.field_184618_aE = 0.0F;
      mc.field_71439_g.field_70721_aZ = 0.0F;
      mc.field_71439_g.field_184619_aG = 0.0F;
      p_Event.cancel();
   }
}
