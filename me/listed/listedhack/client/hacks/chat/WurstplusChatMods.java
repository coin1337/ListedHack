package me.listed.listedhack.client.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;

public final class WurstplusChatMods extends WurstplusHack {
   WurstplusSetting timestamps = this.create("Timestamps", "ChatModsTimeStamps", true);
   WurstplusSetting dateformat = this.create("Date Format", "ChatModsDateFormat", "24HR", this.combobox(new String[]{"24HR", "12HR"}));
   WurstplusSetting name_highlight = this.create("Name Highlight", "ChatModsNameHighlight", true);
   @EventHandler
   private Listener<WurstplusEventPacket.ReceivePacket> PacketEvent = new Listener((event) -> {
      if (event.get_packet() instanceof SPacketChat) {
         SPacketChat packet = (SPacketChat)event.get_packet();
         if (packet.func_148915_c() instanceof TextComponentString) {
            TextComponentString component = (TextComponentString)packet.func_148915_c();
            String text;
            if (this.timestamps.get_value(true)) {
               text = "";
               if (this.dateformat.in("12HR")) {
                  text = (new SimpleDateFormat("h:mm a")).format(new Date());
               }

               if (this.dateformat.in("24HR")) {
                  text = (new SimpleDateFormat("k:mm")).format(new Date());
               }

               component.field_150267_b = "§7[" + text + "]§r " + component.field_150267_b;
            }

            text = component.func_150254_d();
            if (text.contains("combat for")) {
               return;
            }

            if (this.name_highlight.get_value(true) && mc.field_71439_g != null && text.toLowerCase().contains(mc.field_71439_g.func_70005_c_().toLowerCase())) {
               text = text.replaceAll("(?i)" + mc.field_71439_g.func_70005_c_(), ChatFormatting.GOLD + mc.field_71439_g.func_70005_c_() + ChatFormatting.RESET);
            }

            event.cancel();
            WurstplusMessageUtil.client_message(text);
         }
      }

   }, new Predicate[0]);

   public WurstplusChatMods() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Chat Modifier";
      this.tag = "ChatModifier";
      this.description = "this breaks things";
   }
}
