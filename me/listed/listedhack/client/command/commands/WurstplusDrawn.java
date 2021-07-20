package me.listed.listedhack.client.command.commands;

import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusDrawnUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusDrawn extends WurstplusCommand {
   public WurstplusDrawn() {
      super("drawn", "Hide elements of the array list");
   }

   public boolean get_message(String[] message) {
      if (message.length == 1) {
         WurstplusMessageUtil.send_client_error_message("module name needed");
         return true;
      } else if (message.length == 2) {
         if (this.is_module(message[1])) {
            WurstplusDrawnUtil.add_remove_item(message[1]);
            ListedHack.get_config_manager().save_settings();
         } else {
            WurstplusMessageUtil.send_client_error_message("cannot find module by name: " + message[1]);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean is_module(String s) {
      List<WurstplusHack> modules = ListedHack.get_hack_manager().get_array_hacks();
      Iterator var3 = modules.iterator();

      WurstplusHack module;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         module = (WurstplusHack)var3.next();
      } while(!module.get_tag().equalsIgnoreCase(s));

      return true;
   }
}
