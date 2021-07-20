package me.listed.listedhack.client.hacks.chat;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusEzMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class WurstplusAutoEz extends WurstplusHack {
   int delay_count = 0;
   WurstplusSetting custom = this.create("Custom", "EzCustom", false);
   private static final ConcurrentHashMap targeted_players = new ConcurrentHashMap();
   @EventHandler
   private Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener((event) -> {
      if (mc.field_71439_g != null) {
         if (event.get_packet() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.get_packet();
            if (cPacketUseEntity.func_149565_c().equals(Action.ATTACK)) {
               Entity target_entity = cPacketUseEntity.func_149564_a(mc.field_71441_e);
               if (target_entity instanceof EntityPlayer) {
                  add_target(target_entity.func_70005_c_());
               }
            }
         }

      }
   }, new Predicate[0]);
   @EventHandler
   private Listener<LivingDeathEvent> living_death_listener = new Listener((event) -> {
      if (mc.field_71439_g != null) {
         EntityLivingBase e = event.getEntityLiving();
         if (e != null) {
            if (e instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)e;
               if (player.func_110143_aJ() <= 0.0F && targeted_players.containsKey(player.func_70005_c_())) {
                  this.announce(player.func_70005_c_());
               }
            }

         }
      }
   }, new Predicate[0]);

   public WurstplusAutoEz() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Auto EZ";
      this.tag = "AutoEz";
      this.description = "Ez'd by ListedHack";
   }

   public void update() {
      Iterator var1 = mc.field_71441_e.func_72910_y().iterator();

      while(var1.hasNext()) {
         Entity entity = (Entity)var1.next();
         if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (player.func_110143_aJ() <= 0.0F && targeted_players.containsKey(player.func_70005_c_())) {
               this.announce(player.func_70005_c_());
            }
         }
      }

      targeted_players.forEach((name, timeout) -> {
         if ((Integer)timeout <= 0) {
            targeted_players.remove(name);
         } else {
            targeted_players.put(name, (Integer)timeout - 1);
         }

      });
      ++this.delay_count;
   }

   public void announce(String name) {
      if (this.delay_count >= 150) {
         this.delay_count = 0;
         targeted_players.remove(name);
         String message = "";
         if (this.custom.get_value(true)) {
            message = message + WurstplusEzMessageUtil.get_message().replace("[", "").replace("]", "");
         } else {
            message = message + "Ez'd by ListedHack";
         }

         mc.field_71439_g.field_71174_a.func_147297_a(new CPacketChatMessage(message));
      }
   }

   public static void add_target(String name) {
      if (!Objects.equals(name, mc.field_71439_g.func_70005_c_())) {
         targeted_players.put(name, 20);
      }

   }
}
