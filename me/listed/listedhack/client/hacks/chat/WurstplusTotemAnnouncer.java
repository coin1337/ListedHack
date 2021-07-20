package me.listed.listedhack.client.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

public class WurstplusTotemAnnouncer extends WurstplusHack {
   public static final HashMap<String, Integer> totem_pop_counter = new HashMap();
   public static ChatFormatting red;
   public static ChatFormatting green;
   public static ChatFormatting gold;
   public static ChatFormatting grey;
   public static ChatFormatting bold;
   public static ChatFormatting reset;
   public static ChatFormatting aqua;
   public static ChatFormatting blue;
   @EventHandler
   private final Listener<WurstplusEventPacket.ReceivePacket> packet_event = new Listener((event) -> {
      if (event.get_packet() instanceof SPacketEntityStatus) {
         SPacketEntityStatus packet = (SPacketEntityStatus)event.get_packet();
         if (packet.func_149160_c() == 35) {
            Entity entity = packet.func_149161_a(mc.field_71441_e);
            int count = 1;
            if (totem_pop_counter.containsKey(entity.func_70005_c_())) {
               count = (Integer)totem_pop_counter.get(entity.func_70005_c_());
               HashMap var10000 = totem_pop_counter;
               String var10001 = entity.func_70005_c_();
               ++count;
               var10000.put(var10001, count);
            } else {
               totem_pop_counter.put(entity.func_70005_c_(), count);
            }

            if (entity == mc.field_71439_g) {
               return;
            }

            if (WurstplusFriendUtil.isFriend(entity.func_70005_c_())) {
               WurstplusMessageUtil.send_client_message(blue + "" + grey + "" + reset + "" + bold + aqua + entity.func_70005_c_() + reset + " has popped " + bold + grey + count + reset + " totems");
            } else {
               WurstplusMessageUtil.send_client_message(blue + "" + grey + "" + reset + "" + bold + red + entity.func_70005_c_() + reset + " has popped " + bold + grey + count + reset + " totems");
            }
         }
      }

   }, new Predicate[0]);

   public WurstplusTotemAnnouncer() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Totem Announcer";
      this.tag = "Totem Announcer";
      this.description = "dude idk wurst+ is just outdated";
   }

   public void update() {
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(true) {
         EntityPlayer player;
         do {
            do {
               if (!var1.hasNext()) {
                  return;
               }

               player = (EntityPlayer)var1.next();
            } while(!totem_pop_counter.containsKey(player.func_70005_c_()));
         } while(!player.field_70128_L && !(player.func_110143_aJ() <= 0.0F));

         int count = (Integer)totem_pop_counter.get(player.func_70005_c_());
         totem_pop_counter.remove(player.func_70005_c_());
         if (player != mc.field_71439_g) {
            if (WurstplusFriendUtil.isFriend(player.func_70005_c_())) {
               WurstplusMessageUtil.send_client_message(blue + "" + grey + "" + reset + "" + bold + aqua + player.func_70005_c_() + reset + " has died after popping " + bold + grey + count + reset + " totems");
            } else {
               WurstplusMessageUtil.send_client_message(blue + "" + grey + "" + reset + "" + bold + red + player.func_70005_c_() + reset + " has died after popping " + bold + grey + count + reset + " totems");
            }
         }
      }
   }

   static {
      red = ChatFormatting.RED;
      green = ChatFormatting.GREEN;
      gold = ChatFormatting.GOLD;
      grey = ChatFormatting.GRAY;
      bold = ChatFormatting.BOLD;
      reset = ChatFormatting.RESET;
      aqua = ChatFormatting.AQUA;
      blue = ChatFormatting.BLUE;
   }
}
