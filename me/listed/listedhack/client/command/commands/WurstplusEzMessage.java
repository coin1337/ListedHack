package me.listed.listedhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.util.WurstplusEzMessageUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusEzMessage extends WurstplusCommand {
   public WurstplusEzMessage() {
      super("ezmessage", "Set ez mode");
   }

   public boolean get_message(String[] message) {
      if (message.length == 1) {
         WurstplusMessageUtil.send_client_error_message("message needed");
         return true;
      } else if (message.length >= 2) {
         StringBuilder ez = new StringBuilder();
         boolean flag = true;
         String[] var4 = message;
         int var5 = message.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String word = var4[var6];
            if (flag) {
               flag = false;
            } else {
               ez.append(word).append(" ");
            }
         }

         WurstplusEzMessageUtil.set_message(ez.toString());
         WurstplusMessageUtil.send_client_message("ez message changed to " + ChatFormatting.BOLD + ez.toString());
         ListedHack.get_config_manager().save_settings();
         return true;
      } else {
         return false;
      }
   }
}
