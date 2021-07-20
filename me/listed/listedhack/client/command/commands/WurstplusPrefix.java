package me.listed.listedhack.client.command.commands;

import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.manager.WurstplusCommandManager;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusPrefix extends WurstplusCommand {
   public WurstplusPrefix() {
      super("prefix", "Change prefix.");
   }

   public boolean get_message(String[] message) {
      String prefix = "null";
      if (message.length > 1) {
         prefix = message[1];
      }

      if (message.length > 2) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "prefix <character>");
         return true;
      } else if (prefix.equals("null")) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "prefix <character>");
         return true;
      } else {
         WurstplusCommandManager.set_prefix(prefix);
         WurstplusMessageUtil.send_client_message("The new prefix is " + prefix);
         return true;
      }
   }
}
