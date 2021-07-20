package me.listed.listedhack.client.util;

import java.util.Random;
import java.util.regex.Pattern;

public class TextUtil {
   public static final String SECTIONSIGN = "§";
   private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + "§" + "[0-9A-FK-OR]");
   public static final String BLACK = "§0";
   public static final String DARK_BLUE = "§1";
   public static final String DARK_GREEN = "§2";
   public static final String DARK_AQUA = "§3";
   public static final String DARK_RED = "§4";
   public static final String DARK_PURPLE = "§5";
   public static final String GOLD = "§6";
   public static final String GRAY = "§7";
   public static final String DARK_GRAY = "§8";
   public static final String BLUE = "§9";
   public static final String GREEN = "§a";
   public static final String AQUA = "§b";
   public static final String RED = "§c";
   public static final String LIGHT_PURPLE = "§d";
   public static final String YELLOW = "§e";
   public static final String WHITE = "§f";
   public static final String OBFUSCATED = "§k";
   public static final String BOLD = "§l";
   public static final String STRIKE = "§m";
   public static final String UNDERLINE = "§n";
   public static final String ITALIC = "§o";
   public static final String RESET = "§r";
   public static final String RAINBOW = "§+";
   public static final String blank = " ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒";
   public static final String line1 = " ███▒█▒█▒███▒███▒███▒███";
   public static final String line2 = " █▒█▒█▒█▒█▒█▒█▒█▒█▒█▒█▒▒";
   public static final String line3 = " ███▒███▒█▒█▒███▒█▒█▒███";
   public static final String line4 = " █▒▒▒█▒█▒█▒█▒█▒█▒█▒█▒▒▒█";
   public static final String line5 = " █▒▒▒█▒█▒███▒███▒███▒███";
   public static final String pword = "  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n ███▒█▒█▒███▒███▒███▒███\n █▒█▒█▒█▒█▒█▒█▒█▒█▒█▒█▒▒\n ███▒███▒█▒█▒███▒█▒█▒███\n █▒▒▒█▒█▒█▒█▒█▒█▒█▒█▒▒▒█\n █▒▒▒█▒█▒███▒███▒███▒███\n ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒";
   public static String shrug = "¯\\_(ツ)_/¯";
   private static final Random rand = new Random();

   public static String stripColor(String input) {
      return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
   }

   public static String coloredString(String string, TextUtil.Color color) {
      String coloredString = string;
      switch(color) {
      case AQUA:
         coloredString = "§b" + string + "§r";
         break;
      case WHITE:
         coloredString = "§f" + string + "§r";
         break;
      case BLACK:
         coloredString = "§0" + string + "§r";
         break;
      case DARK_BLUE:
         coloredString = "§1" + string + "§r";
         break;
      case DARK_GREEN:
         coloredString = "§2" + string + "§r";
         break;
      case DARK_AQUA:
         coloredString = "§3" + string + "§r";
         break;
      case DARK_RED:
         coloredString = "§4" + string + "§r";
         break;
      case DARK_PURPLE:
         coloredString = "§5" + string + "§r";
         break;
      case GOLD:
         coloredString = "§6" + string + "§r";
         break;
      case DARK_GRAY:
         coloredString = "§8" + string + "§r";
         break;
      case GRAY:
         coloredString = "§7" + string + "§r";
         break;
      case BLUE:
         coloredString = "§9" + string + "§r";
         break;
      case RED:
         coloredString = "§c" + string + "§r";
         break;
      case GREEN:
         coloredString = "§a" + string + "§r";
         break;
      case LIGHT_PURPLE:
         coloredString = "§d" + string + "§r";
         break;
      case YELLOW:
         coloredString = "§e" + string + "§r";
      }

      return coloredString;
   }

   public static String cropMaxLengthMessage(String s, int i) {
      String output = "";
      if (s.length() >= 256 - i) {
         output = s.substring(0, 256 - i);
      }

      return output;
   }

   public static enum Color {
      NONE,
      WHITE,
      BLACK,
      DARK_BLUE,
      DARK_GREEN,
      DARK_AQUA,
      DARK_RED,
      DARK_PURPLE,
      GOLD,
      GRAY,
      DARK_GRAY,
      BLUE,
      GREEN,
      AQUA,
      RED,
      LIGHT_PURPLE,
      YELLOW;
   }
}
