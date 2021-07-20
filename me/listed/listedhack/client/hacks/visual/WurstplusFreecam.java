package me.listed.listedhack.client.hacks.visual;

import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventMove;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMathUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;

public class WurstplusFreecam extends WurstplusHack {
   double x;
   double y;
   double z;
   float pitch;
   float yaw;
   EntityOtherPlayerMP soul;
   Entity riding_entity;
   boolean is_riding;
   WurstplusSetting speed_h = this.create("Horizontal Speed", "Horizontal Speed", 3, 1, 4);
   WurstplusSetting speed_v = this.create("Vertical Speed", "Vertical Speed", 3, 1, 4);
   @EventHandler
   Listener<WurstplusEventMove> listener_move = new Listener((event) -> {
      mc.field_71439_g.field_70145_X = true;
   }, new Predicate[0]);
   @EventHandler
   Listener<PlayerSPPushOutOfBlocksEvent> listener_push = new Listener((event) -> {
      event.setCanceled(true);
   }, new Predicate[0]);
   @EventHandler
   Listener<WurstplusEventPacket.SendPacket> listener_packet = new Listener((event) -> {
      if (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput) {
         event.cancel();
      }

   }, new Predicate[0]);

   public WurstplusFreecam() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Freecam";
      this.tag = "Freecam";
      this.description = "spectator mode";
   }

   public void enable() {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         this.is_riding = mc.field_71439_g.func_184187_bx() != null;
         if (mc.field_71439_g.func_184187_bx() == null) {
            this.x = mc.field_71439_g.field_70165_t;
            this.y = mc.field_71439_g.field_70163_u;
            this.z = mc.field_71439_g.field_70161_v;
         } else {
            this.riding_entity = mc.field_71439_g.func_184187_bx();
            mc.field_71439_g.func_184210_p();
         }

         this.pitch = mc.field_71439_g.field_70125_A;
         this.yaw = mc.field_71439_g.field_70177_z;
         this.soul = new EntityOtherPlayerMP(mc.field_71441_e, mc.func_110432_I().func_148256_e());
         this.soul.func_82149_j(mc.field_71439_g);
         this.soul.field_70759_as = mc.field_71439_g.field_70759_as;
         mc.field_71441_e.func_73027_a(-100, this.soul);
         mc.field_71439_g.field_70145_X = true;
      }

   }

   public void disable() {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         mc.field_71439_g.func_70080_a(this.x, this.y, this.z, this.yaw, this.pitch);
         mc.field_71441_e.func_73028_b(-100);
         this.soul = null;
         this.x = 0.0D;
         this.y = 0.0D;
         this.z = 0.0D;
         this.yaw = 0.0F;
         this.pitch = 0.0F;
         mc.field_71439_g.field_70159_w = mc.field_71439_g.field_70181_x = mc.field_71439_g.field_70179_y = 0.0D;
         if (this.is_riding) {
            mc.field_71439_g.func_184205_a(this.riding_entity, true);
         }
      }

   }

   public void update() {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         mc.field_71439_g.field_70145_X = true;
         mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         mc.field_71439_g.field_71155_g = 5000.0F;
         mc.field_71439_g.field_70747_aH = 0.5F;
         double[] static_mov = WurstplusMathUtil.movement_speed((double)(this.speed_h.get_value(1) / 2));
         if (mc.field_71439_g.field_71158_b.field_78902_a == 0.0F && mc.field_71439_g.field_71158_b.field_192832_b == 0.0F) {
            mc.field_71439_g.field_70159_w = 0.0D;
            mc.field_71439_g.field_70179_y = 0.0D;
         } else {
            mc.field_71439_g.field_70159_w = static_mov[0];
            mc.field_71439_g.field_70179_y = static_mov[1];
         }

         mc.field_71439_g.func_70031_b(false);
         EntityPlayerSP var10000;
         if (mc.field_71474_y.field_74314_A.func_151470_d()) {
            var10000 = mc.field_71439_g;
            var10000.field_70181_x += (double)this.speed_v.get_value(1);
         }

         if (mc.field_71474_y.field_74311_E.func_151470_d()) {
            var10000 = mc.field_71439_g;
            var10000.field_70181_x -= (double)this.speed_v.get_value(1);
         }
      }

   }
}
