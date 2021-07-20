package me.listed.listedhack.client.hacks.chat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.util.math.Vec3d;

public class WurstplusAnnouncer extends WurstplusHack {
   WurstplusSetting min_distance = this.create("Min Distance", "AnnouncerMinDist", 12, 1, 100);
   WurstplusSetting max_distance = this.create("Max Distance", "AnnouncerMaxDist", 144, 12, 1200);
   WurstplusSetting delay = this.create("Delay Seconds", "AnnouncerDelay", 4, 0, 20);
   WurstplusSetting queue_size = this.create("Queue Size", "AnnouncerQueueSize", 5, 1, 20);
   WurstplusSetting units = this.create("Units", "AnnouncerUnits", "Meters", this.combobox(new String[]{"Meters", "Feet", "Yards", "Inches"}));
   WurstplusSetting movement_string = this.create("Movement", "AnnouncerMovement", "Aha x", this.combobox(new String[]{"Aha x", "Leyta", "FUCK"}));
   WurstplusSetting suffix = this.create("Suffix", "AnnouncerSuffix", true);
   WurstplusSetting smol = this.create("Small Text", "AnnouncerSmallText", false);
   private static DecimalFormat df = new DecimalFormat();
   private static final Queue<String> message_q = new ConcurrentLinkedQueue();
   private static final Map<String, Integer> mined_blocks = new ConcurrentHashMap();
   private static final Map<String, Integer> placed_blocks = new ConcurrentHashMap();
   private static final Map<String, Integer> dropped_items = new ConcurrentHashMap();
   private static final Map<String, Integer> consumed_items = new ConcurrentHashMap();
   private boolean first_run;
   private static Vec3d thisTickPos;
   private static Vec3d lastTickPos;
   private static int delay_count;
   private static double distanceTraveled;
   private static float thisTickHealth;
   private static float lastTickHealth;
   private static float gainedHealth;
   private static float lostHealth;
   @EventHandler
   private Listener<WurstplusEventPacket.ReceivePacket> receive_listener = new Listener((event) -> {
      if (mc.field_71441_e != null) {
         if (event.get_packet() instanceof SPacketUseBed) {
            this.queue_message("I am going to bed now, goodnight");
         }

      }
   }, new Predicate[0]);
   @EventHandler
   private Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener((event) -> {
      if (mc.field_71441_e != null) {
         String name;
         if (event.get_packet() instanceof CPacketPlayerDigging) {
            CPacketPlayerDigging packet = (CPacketPlayerDigging)event.get_packet();
            if (mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_190931_a && (packet.func_180762_c().equals(Action.DROP_ITEM) || packet.func_180762_c().equals(Action.DROP_ALL_ITEMS))) {
               name = mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r();
               if (dropped_items.containsKey(name)) {
                  dropped_items.put(name, (Integer)dropped_items.get(name) + 1);
               } else {
                  dropped_items.put(name, 1);
               }
            }

            if (packet.func_180762_c().equals(Action.STOP_DESTROY_BLOCK)) {
               name = mc.field_71441_e.func_180495_p(packet.func_179715_a()).func_177230_c().func_149732_F();
               if (mined_blocks.containsKey(name)) {
                  mined_blocks.put(name, (Integer)mined_blocks.get(name) + 1);
               } else {
                  mined_blocks.put(name, 1);
               }
            }
         } else {
            if (event.get_packet() instanceof CPacketUpdateSign) {
               this.queue_message("I just updated a sign with some epic text");
            }

            if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
               ItemStack stack = mc.field_71439_g.field_71071_by.func_70448_g();
               if (stack.func_190926_b()) {
                  return;
               }

               if (stack.func_77973_b() instanceof ItemBlock) {
                  name = mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r();
                  if (placed_blocks.containsKey(name)) {
                     placed_blocks.put(name, (Integer)placed_blocks.get(name) + 1);
                  } else {
                     placed_blocks.put(name, 1);
                  }

                  return;
               }

               if (stack.func_77973_b() == Items.field_185158_cP) {
                  name = "Crystals";
                  if (placed_blocks.containsKey(name)) {
                     placed_blocks.put(name, (Integer)placed_blocks.get(name) + 1);
                  } else {
                     placed_blocks.put(name, 1);
                  }
               }
            }
         }

      }
   }, new Predicate[0]);

   public WurstplusAnnouncer() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Announcer";
      this.tag = "Announcer";
      this.description = "how to get muted 101";
   }

   public void update() {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         try {
            this.get_tick_data();
         } catch (Exception var2) {
            this.set_disable();
            return;
         }

         this.send_cycle();
      } else {
         this.set_disable();
      }
   }

   private void get_tick_data() {
      lastTickPos = thisTickPos;
      thisTickPos = mc.field_71439_g.func_174791_d();
      distanceTraveled += thisTickPos.func_72438_d(lastTickPos);
      lastTickHealth = thisTickHealth;
      thisTickHealth = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj();
      float healthDiff = thisTickHealth - lastTickHealth;
      if (healthDiff < 0.0F) {
         lostHealth += healthDiff * -1.0F;
      } else {
         gainedHealth += healthDiff;
      }

   }

   public void send_cycle() {
      ++delay_count;
      if (delay_count > this.delay.get_value(1) * 20) {
         delay_count = 0;
         this.composeGameTickData();
         this.composeEventData();
         Iterator var1 = message_q.iterator();
         if (var1.hasNext()) {
            String message = (String)var1.next();
            this.send_message(message);
            message_q.remove(message);
         }
      }

   }

   private void send_message(String s) {
      if (this.suffix.get_value(true)) {
         String i = " ඞ ";
         s = s + i + ListedHack.smoth("thanks to listedhack");
      }

      if (this.smol.get_value(true)) {
         s = ListedHack.smoth(s.toLowerCase());
      }

      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketChatMessage(s.replaceAll("§", "")));
   }

   public void queue_message(String m) {
      if (message_q.size() <= this.queue_size.get_value(1)) {
         message_q.add(m);
      }
   }

   protected void enable() {
      this.first_run = true;
      df = new DecimalFormat("#.#");
      df.setRoundingMode(RoundingMode.CEILING);
      Vec3d pos;
      lastTickPos = pos = mc.field_71439_g.func_174791_d();
      thisTickPos = pos;
      distanceTraveled = 0.0D;
      float health;
      lastTickHealth = health = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj();
      thisTickHealth = health;
      lostHealth = 0.0F;
      gainedHealth = 0.0F;
      delay_count = 0;
   }

   public static double round(double unrounded, int precision) {
      BigDecimal bd = new BigDecimal(unrounded);
      BigDecimal rounded = bd.setScale(precision, 4);
      return rounded.doubleValue();
   }

   private void composeGameTickData() {
      if (this.first_run) {
         this.first_run = false;
      } else {
         if (distanceTraveled >= 1.0D) {
            if (distanceTraveled < (double)(this.delay.get_value(1) * this.min_distance.get_value(1))) {
               return;
            }

            if (distanceTraveled > (double)(this.delay.get_value(1) * this.max_distance.get_value(1))) {
               distanceTraveled = 0.0D;
               return;
            }

            CharSequence sb = new StringBuilder();
            if (this.movement_string.in("Aha x")) {
               ((StringBuilder)sb).append("I just traveled ");
            }

            if (this.movement_string.in("FUCK")) {
               ((StringBuilder)sb).append("FUCK, I just fucking traveled ");
            }

            if (this.movement_string.in("Leyta")) {
               ((StringBuilder)sb).append("leyta bitch, I just traveled ");
            }

            if (this.units.in("Feet")) {
               ((StringBuilder)sb).append(round(distanceTraveled * 3.2808D, 2));
               if ((double)((int)distanceTraveled) == 1.0D) {
                  ((StringBuilder)sb).append(" Foot");
               } else {
                  ((StringBuilder)sb).append(" Feet");
               }
            }

            if (this.units.in("Yards")) {
               ((StringBuilder)sb).append(round(distanceTraveled * 1.0936D, 2));
               if ((double)((int)distanceTraveled) == 1.0D) {
                  ((StringBuilder)sb).append(" Yard");
               } else {
                  ((StringBuilder)sb).append(" Yards");
               }
            }

            if (this.units.in("Inches")) {
               ((StringBuilder)sb).append(round(distanceTraveled * 39.37D, 2));
               if ((double)((int)distanceTraveled) == 1.0D) {
                  ((StringBuilder)sb).append(" Inch");
               } else {
                  ((StringBuilder)sb).append(" Inches");
               }
            }

            if (this.units.in("Meters")) {
               ((StringBuilder)sb).append(round(distanceTraveled, 2));
               if ((double)((int)distanceTraveled) == 1.0D) {
                  ((StringBuilder)sb).append(" Meter");
               } else {
                  ((StringBuilder)sb).append(" Meters");
               }
            }

            this.queue_message(sb.toString());
            distanceTraveled = 0.0D;
         }

         String sb;
         if (lostHealth != 0.0F) {
            sb = "HECK! I just lost " + df.format((double)lostHealth) + " health D:";
            this.queue_message((String)sb);
            lostHealth = 0.0F;
         }

         if (gainedHealth != 0.0F) {
            sb = "#ezmode I now have " + df.format((double)gainedHealth) + " more health";
            this.queue_message((String)sb);
            gainedHealth = 0.0F;
         }

      }
   }

   private void composeEventData() {
      Iterator var1 = mined_blocks.entrySet().iterator();

      Entry kv;
      while(var1.hasNext()) {
         kv = (Entry)var1.next();
         this.queue_message("We be mining " + kv.getValue() + " " + (String)kv.getKey() + " out here");
         mined_blocks.remove(kv.getKey());
      }

      var1 = placed_blocks.entrySet().iterator();

      while(var1.hasNext()) {
         kv = (Entry)var1.next();
         this.queue_message("We be placing " + kv.getValue() + " " + (String)kv.getKey() + " out here");
         placed_blocks.remove(kv.getKey());
      }

      var1 = dropped_items.entrySet().iterator();

      while(var1.hasNext()) {
         kv = (Entry)var1.next();
         this.queue_message("I just dropped " + kv.getValue() + " " + (String)kv.getKey() + ", whoops!");
         dropped_items.remove(kv.getKey());
      }

      var1 = consumed_items.entrySet().iterator();

      while(var1.hasNext()) {
         kv = (Entry)var1.next();
         this.queue_message("NOM NOM, I just ate " + kv.getValue() + " " + (String)kv.getKey() + ", yummy");
         consumed_items.remove(kv.getKey());
      }

   }
}
