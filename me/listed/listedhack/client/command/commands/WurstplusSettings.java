package me.listed.listedhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusSettings extends WurstplusCommand {
   public WurstplusSettings() {
      super("settings", "To save/load settings.");
   }

   public boolean get_message(String[] message) {
      String msg = "null";
      if (message.length > 1) {
         msg = message[1];
      }

      if (msg.equals("null")) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "settings <save/load>");
         return true;
      } else {
         ChatFormatting c = ChatFormatting.GRAY;
         if (msg.equalsIgnoreCase("save")) {
            ListedHack.get_config_manager().save_settings();
            WurstplusMessageUtil.send_client_message(ChatFormatting.GREEN + "Successfully " + c + "saved!");
         } else {
            if (!msg.equalsIgnoreCase("load")) {
               WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "settings <save/load>");
               return true;
            }

            ListedHack.get_config_manager().load_settings();
            WurstplusMessageUtil.send_client_message(ChatFormatting.GREEN + "Successfully " + c + "loaded!");
         }

         return true;
      }
   }
}
