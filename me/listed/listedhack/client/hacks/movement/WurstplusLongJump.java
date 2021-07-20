package me.listed.listedhack.client.hacks.movement;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WurstplusLongJump extends WurstplusHack {
   float boostSpeed;
   int boostSpeedInt;
   boolean jumped;
   boolean boostable;
   WurstplusSetting bypassMode = this.create("Bypass Mode", "Bypass Mode", "Packet", this.combobox(new String[]{"Packet", "Packet 2"}));
   WurstplusSetting boostMode = this.create("Boost Mode", "boostMode", "Only ground", this.combobox(new String[]{"Only ground", "Always", "Jump Event"}));
   WurstplusSetting calcMode = this.create("Calc Mode", "calcMode", "Constant", this.combobox(new String[]{"Dissolve", "Constant"}));
   WurstplusSetting bypass = this.create("Bypass", "Bypass", true);
   WurstplusSetting boost = this.create("Boost", "Boost", 31, 1, 100);

   public WurstplusLongJump() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "LongJump";
      this.tag = "LongJump";
      this.description = "Makes you jump far";
   }

   public void update() {
      this.boostSpeedInt = this.boost.get_value(1);
      this.boostSpeed = (float)this.boostSpeedInt / 20.0F;
      if (mc.field_71439_g.field_70122_E) {
         this.jumped = false;
         mc.field_71439_g.field_70159_w = 0.0D;
         mc.field_71439_g.field_70179_y = 0.0D;
      }

      double yaw = WurstplusEntityUtil.getDirection();
      if (this.boostMode.in("Always")) {
         this.setMotion(yaw, this.boostSpeed);
      } else if (this.boostMode.in("Only ground")) {
         if (mc.field_71439_g.field_70122_E && mc.field_71439_g.field_191988_bg != 0.0F) {
            if (this.calcMode.in("Constant")) {
               this.setMotion(yaw, this.boostSpeed);
            } else {
               mc.field_71428_T.field_194149_e = 50.0F;
               mc.field_71439_g.field_70159_w = -Math.sin(yaw) * (double)((float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) * (mc.field_71439_g.field_70122_E ? this.boostSpeed : 1.0F));
               mc.field_71439_g.field_70179_y = Math.cos(yaw) * (double)((float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) * (mc.field_71439_g.field_70122_E ? this.boostSpeed : 1.0F));
            }
         } else {
            mc.field_71428_T.field_194149_e = 50.0F;
            mc.field_71439_g.field_70159_w = -Math.sin(yaw) * (double)((float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) * (mc.field_71439_g.field_70122_E ? this.boostSpeed : 1.0F));
            mc.field_71439_g.field_70179_y = Math.cos(yaw) * (double)((float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) * (mc.field_71439_g.field_70122_E ? this.boostSpeed : 1.0F));
         }
      } else {
         mc.field_71428_T.field_194149_e = 50.0F;
         mc.field_71439_g.field_70159_w = -Math.sin(yaw) * (double)((float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) * (this.boostable ? this.boostSpeed : 1.0F));
         mc.field_71439_g.field_70179_y = Math.cos(yaw) * (double)((float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) * (this.boostable ? this.boostSpeed : 1.0F));
         this.boostable = false;
      }

      if (mc.field_71439_g.field_191988_bg != 0.0F) {
         if (mc.field_71439_g.field_70122_E) {
            mc.field_71439_g.func_70664_aZ();
         }

         if (this.bypass.get_value(true)) {
            if (this.bypassMode.in("Packet")) {
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t + mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70163_u + mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70161_v + mc.field_71439_g.field_70179_y, true));
            } else {
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t + mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70163_u + mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70161_v + mc.field_71439_g.field_70179_y, true));
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t + mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70163_u + mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70161_v + mc.field_71439_g.field_70179_y, true));
            }
         }
      } else if (mc.field_71439_g.field_191988_bg == 0.0F && mc.field_71439_g.field_70702_br == 0.0F) {
         mc.field_71439_g.field_70159_w = 0.0D;
         mc.field_71439_g.field_70179_y = 0.0D;
      }

   }

   protected void disable() {
      mc.field_71439_g.field_70159_w = 0.0D;
      mc.field_71439_g.field_70179_y = 0.0D;
   }

   public void setMotion(double yaw, float sped) {
      mc.field_71439_g.field_70159_w = -Math.sin(yaw) * (double)sped;
      mc.field_71439_g.field_70179_y = Math.cos(yaw) * (double)sped;
   }

   @SubscribeEvent
   public void onJump(LivingJumpEvent event) {
      if (mc.field_71439_g != null && mc.field_71441_e != null && event.getEntity() == mc.field_71439_g && (mc.field_71439_g.field_71158_b.field_192832_b != 0.0F || mc.field_71439_g.field_71158_b.field_78902_a != 0.0F)) {
         this.jumped = true;
         this.boostable = true;
      }

   }
}
