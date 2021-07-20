package me.listed.listedhack.client.hacks.chat;

import java.util.Random;
import java.util.function.Predicate;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

public class WurstplusChatSuffix extends WurstplusHack {
   WurstplusSetting ignore = this.create("Ignore", "ChatSuffixIgnore", true);
   WurstplusSetting type = this.create("Type", "ChatSuffixType", "Default", this.combobox(new String[]{"Default", "Random"}));
   boolean accept_suffix;
   boolean suffix_default;
   boolean suffix_random;
   StringBuilder suffix;
   String[] random_client_name = new String[]{" 3listedhack"};
   String[] random_client_finish = new String[]{"3"};
   @EventHandler
   private Listener<WurstplusEventPacket.SendPacket> listener = new Listener((event) -> {
      if (event.get_packet() instanceof CPacketChatMessage) {
         this.accept_suffix = true;
         boolean ignore_prefix = this.ignore.get_value(true);
         String message = ((CPacketChatMessage)event.get_packet()).func_149439_c();
         if (message.startsWith("/") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("\\") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("!") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith(":") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith(";") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith(".") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith(",") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("@") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("&") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("*") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("$") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("#") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith("(") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (message.startsWith(")") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (this.type.in("Default")) {
            this.suffix_default = true;
            this.suffix_random = false;
         }

         if (this.type.in("Random")) {
            this.suffix_default = false;
            this.suffix_random = true;
         }

         if (this.accept_suffix) {
            if (this.suffix_default) {
               message = message + " " + this.convert_base(" 3listedhack");
            }

            if (this.suffix_random) {
               StringBuilder suffix_with_randoms = new StringBuilder();
               suffix_with_randoms.append(this.convert_base(this.random_string(this.random_client_name)));
               suffix_with_randoms.append(this.convert_base(this.random_string(this.random_client_finish)));
               message = message + " " + suffix_with_randoms.toString();
            }

            if (message.length() >= 256) {
               message.substring(0, 256);
            }
         }

         ((CPacketChatMessage)event.get_packet()).field_149440_a = message;
      }
   }, new Predicate[0]);

   public WurstplusChatSuffix() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Chat Suffix";
      this.tag = "ChatSuffix";
      this.description = "show off how cool u are";
   }

   public String random_string(String[] list) {
      return list[(new Random()).nextInt(list.length)];
   }

   public String convert_base(String base) {
      return ListedHack.smoth(base);
   }

   public String array_detail() {
      return this.type.get_current_value();
   }
}
