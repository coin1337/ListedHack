package me.listed.listedhack.client.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.event.HoverEvent.Action;

public class WurstplusMessageUtil {
   public static final Minecraft mc = Minecraft.func_71410_x();
   public static ChatFormatting g;
   public static ChatFormatting b;
   public static ChatFormatting a;
   public static ChatFormatting gr;
   public static ChatFormatting bold;
   public static ChatFormatting r;
   public static ChatFormatting db;
   public static ChatFormatting dg;
   public static ChatFormatting obf;
   public static String opener;

   public static void toggle_message(WurstplusHack module) {
      if (module.is_active()) {
         if (module.get_tag().equals("AutoCrystal")) {
            client_message_simple(opener + "AutoCrystal" + ChatFormatting.BLUE + " enabled");
         } else {
            client_message_simple(opener + r + module.get_name() + ChatFormatting.BLUE + " enabled");
         }
      } else if (module.get_tag().equals("AutoCrystal")) {
         client_message_simple(opener + "AutoCrystal" + ChatFormatting.DARK_RED + " disabled");
      } else {
         client_message_simple(opener + r + module.get_name() + ChatFormatting.DARK_RED + " disabled");
      }

   }

   public static void client_message_simple(String message) {
      if (mc.field_71439_g != null) {
         ITextComponent itc = (new TextComponentString(message)).func_150255_a((new Style()).func_150209_a(new HoverEvent(Action.SHOW_TEXT, new TextComponentString("frank alachi"))));
         mc.field_71456_v.func_146158_b().func_146234_a(itc, 5936);
      }

   }

   public static void client_message(String message) {
      if (mc.field_71439_g != null) {
         mc.field_71439_g.func_145747_a(new WurstplusMessageUtil.ChatMessage(message));
      }

   }

   public static void send_client_message_simple(String message) {
      client_message(ChatFormatting.BLUE + "ListedHack" + gr + " > " + r + message);
   }

   public static void send_client_message(String message) {
      client_message(ChatFormatting.BLUE + "ListedHack" + gr + " > " + r + message);
   }

   public static void send_client_error_message(String message) {
      client_message(ChatFormatting.RED + "ListedHack" + gr + " > " + r + message);
   }

   static {
      g = ChatFormatting.GOLD;
      b = ChatFormatting.BLUE;
      a = ChatFormatting.DARK_AQUA;
      gr = ChatFormatting.GRAY;
      bold = ChatFormatting.BOLD;
      r = ChatFormatting.RESET;
      db = ChatFormatting.DARK_BLUE;
      dg = ChatFormatting.DARK_GREEN;
      obf = ChatFormatting.OBFUSCATED;
      opener = db + "[" + r + a + "ListedHack" + db + "] " + r;
   }

   public static class ChatMessage extends TextComponentBase {
      String message_input;

      public ChatMessage(String message) {
         Pattern p = Pattern.compile("&[0123456789abcdefrlosmk]");
         Matcher m = p.matcher(message);
         StringBuffer sb = new StringBuffer();

         while(m.find()) {
            String replacement = "§" + m.group().substring(1);
            m.appendReplacement(sb, replacement);
         }

         m.appendTail(sb);
         this.message_input = sb.toString();
      }

      public String func_150261_e() {
         return this.message_input;
      }

      public ITextComponent func_150259_f() {
         return new WurstplusMessageUtil.ChatMessage(this.message_input);
      }
   }
}
