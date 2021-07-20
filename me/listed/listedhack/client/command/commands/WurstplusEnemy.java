package me.listed.listedhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.util.WurstplusEnemyUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusEnemy extends WurstplusCommand {
   public static ChatFormatting green;
   public static ChatFormatting red;
   public static ChatFormatting aqua;
   public static ChatFormatting bold;
   public static ChatFormatting reset;

   public WurstplusEnemy() {
      super("enemy", "To add enemy");
   }

   public boolean get_message(String[] message) {
      if (message.length == 1) {
         WurstplusMessageUtil.send_client_message("Add - add enemy");
         WurstplusMessageUtil.send_client_message("Del - delete enemy");
         WurstplusMessageUtil.send_client_message("List - list enemies");
         return true;
      } else if (message.length == 2) {
         if (!message[1].equalsIgnoreCase("list")) {
            if (WurstplusEnemyUtil.isEnemy(message[1])) {
               WurstplusMessageUtil.send_client_message("Player " + red + message[1] + reset + " is your Enemy");
               return true;
            } else {
               WurstplusMessageUtil.send_client_error_message("Player " + aqua + message[1] + reset + " is not your Enemy");
               return true;
            }
         } else {
            if (WurstplusEnemyUtil.enemies.isEmpty()) {
               WurstplusMessageUtil.send_client_message("You have " + aqua + "no" + reset + " enemies");
            } else {
               Iterator var4 = WurstplusEnemyUtil.enemies.iterator();

               while(var4.hasNext()) {
                  WurstplusEnemyUtil.Enemy Enemy = (WurstplusEnemyUtil.Enemy)var4.next();
                  WurstplusMessageUtil.send_client_message("" + red + Enemy.getUsername());
               }
            }

            return true;
         }
      } else {
         if (message.length >= 3) {
            WurstplusEnemyUtil.Enemy f;
            if (message[1].equalsIgnoreCase("add")) {
               if (WurstplusEnemyUtil.isEnemy(message[2])) {
                  WurstplusMessageUtil.send_client_message("Player " + red + message[2] + reset + " is already your Enemy");
                  return true;
               }

               f = WurstplusEnemyUtil.get_enemy_object(message[2]);
               if (f == null) {
                  WurstplusMessageUtil.send_client_error_message("Cannot find " + aqua + "UUID" + reset + " for that player");
                  return true;
               }

               WurstplusEnemyUtil.enemies.add(f);
               WurstplusMessageUtil.send_client_message("Player " + red + message[2] + reset + " is now your Enemy");
               return true;
            }

            if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
               if (!WurstplusEnemyUtil.isEnemy(message[2])) {
                  WurstplusMessageUtil.send_client_message("Player " + aqua + message[2] + reset + " is already not your Enemy");
                  return true;
               } else {
                  f = (WurstplusEnemyUtil.Enemy)WurstplusEnemyUtil.enemies.stream().filter((Enemyx) -> {
                     return Enemyx.getUsername().equalsIgnoreCase(message[2]);
                  }).findFirst().get();
                  WurstplusEnemyUtil.enemies.remove(f);
                  WurstplusMessageUtil.send_client_message("Player " + aqua + message[2] + reset + " is not your Enemy");
                  return true;
               }
            }
         }

         return true;
      }
   }

   static {
      green = ChatFormatting.GREEN;
      red = ChatFormatting.RED;
      aqua = ChatFormatting.AQUA;
      bold = ChatFormatting.BOLD;
      reset = ChatFormatting.RESET;
   }
}
