package me.listed.listedhack.client.command.commands;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusAlert extends WurstplusCommand {
   public WurstplusAlert() {
      super("alert", "if the module should spam chat or not");
   }

   public boolean get_message(String[] message) {
      String module = "null";
      String state = "null";
      if (message.length > 1) {
         module = message[1];
      }

      if (message.length > 2) {
         state = message[2];
      }

      if (message.length > 3) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleName> <True/On/False/Off>");
         return true;
      } else if (module.equals("null")) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleName> <True/On/False/Off>");
         return true;
      } else if (state.equals("null")) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleName> <True/On/False/Off>");
         return true;
      } else {
         module = module.toLowerCase();
         state = state.toLowerCase();
         WurstplusHack module_requested = ListedHack.get_hack_manager().get_module_with_tag(module);
         if (module_requested == null) {
            WurstplusMessageUtil.send_client_error_message("This module does not exist.");
            return true;
         } else {
            boolean value = true;
            if (!state.equals("true") && !state.equals("on")) {
               if (!state.equals("false") && !state.equals("off")) {
                  WurstplusMessageUtil.send_client_error_message("This value does not exist. <True/On/False/Off>");
                  return true;
               }

               value = false;
            } else {
               value = true;
            }

            module_requested.set_if_can_send_message_toggle(value);
            WurstplusMessageUtil.send_client_message("The actual value of " + module_requested.get_name() + " is " + Boolean.toString(module_requested.can_send_message_when_toggle()) + ".");
            return true;
         }
      }
   }
}
