package me.listed.listedhack.client.command.commands;

import java.util.Iterator;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.command.WurstplusCommands;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusHelp extends WurstplusCommand {
   public WurstplusHelp() {
      super("help", "helps people");
   }

   public boolean get_message(String[] message) {
      String type = "null";
      if (message.length == 1) {
         type = "list";
      }

      if (message.length > 1) {
         type = message[1];
      }

      if (message.length > 2) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "help <List/NameCommand>");
         return true;
      } else if (type.equals("null")) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "help <List/NameCommand>");
         return true;
      } else if (!type.equalsIgnoreCase("list")) {
         WurstplusCommand command_requested = WurstplusCommands.get_command_with_name(type);
         if (command_requested == null) {
            WurstplusMessageUtil.send_client_error_message("This command does not exist.");
            return true;
         } else {
            WurstplusMessageUtil.send_client_message(command_requested.get_name() + " - " + command_requested.get_description());
            return true;
         }
      } else {
         Iterator var3 = WurstplusCommands.get_pure_command_list().iterator();

         while(var3.hasNext()) {
            WurstplusCommand commands = (WurstplusCommand)var3.next();
            WurstplusMessageUtil.send_client_message(commands.get_name());
         }

         return true;
      }
   }
}
