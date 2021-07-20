package me.listed.listedhack.client.guiscreen.render.components.past.font;

import me.listed.listedhack.ListedHack;
import net.minecraft.client.Minecraft;

public class FontUtil {
   protected static Minecraft mc = Minecraft.func_71410_x();

   public static void drawString(String text, float x, float y, int colour) {
      if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
         ListedHack.latoFont.drawString(text, x, y, colour);
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
         ListedHack.verdanaFont.drawString(text, x, y, colour);
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
         ListedHack.arialFont.drawString(text, x, y, colour);
      } else {
         mc.field_71466_p.func_78276_b(text, (int)x, (int)y, colour);
      }

   }

   public static void drawStringWithShadow(String text, float x, float y, int colour) {
      if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
         ListedHack.latoFont.drawStringWithShadow(text, (double)x, (double)y, colour);
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
         ListedHack.verdanaFont.drawStringWithShadow(text, (double)x, (double)y, colour);
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
         ListedHack.arialFont.drawStringWithShadow(text, (double)x, (double)y, colour);
      } else {
         mc.field_71466_p.func_175063_a(text, (float)((int)x), (float)((int)y), colour);
      }

   }

   public static void drawText(String text, float x, float y, int colour) {
      if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFontShadow").get_value(true)) {
         drawStringWithShadow(text, x, y, colour);
      } else {
         drawString(text, x, y, colour);
      }

   }

   public static int getFontHeight() {
      if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
         return ListedHack.latoFont.getHeight();
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
         return ListedHack.verdanaFont.getHeight();
      } else {
         return ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial") ? ListedHack.arialFont.getHeight() : mc.field_71466_p.field_78288_b;
      }
   }
}
