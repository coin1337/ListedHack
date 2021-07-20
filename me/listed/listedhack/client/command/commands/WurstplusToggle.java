package me.listed.listedhack.client.command.commands;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.manager.WurstplusCommandManager;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusToggle extends WurstplusCommand {
   public WurstplusToggle() {
      super("t", "turn on and off stuffs");
   }

   public boolean get_message(String[] message) {
      String module = "null";
      if (message.length > 1) {
         module = message[1];
      }

      if (message.length > 2) {
         WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleNameNoSpace>");
         return true;
      } else if (module.equals("null")) {
         WurstplusMessageUtil.send_client_error_message(WurstplusCommandManager.get_prefix() + "t <ModuleNameNoSpace>");
         return true;
      } else {
         WurstplusHack module_requested = ListedHack.get_module_manager().get_module_with_tag(module);
         if (module_requested != null) {
            module_requested.toggle();
            WurstplusMessageUtil.send_client_message("[" + module_requested.get_tag() + "] - Toggled to " + module_requested.is_active() + ".");
         } else {
            WurstplusMessageUtil.send_client_error_message("Module does not exist.");
         }

         return true;
      }
   }
}
