package me.listed.listedhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;

public class WurstplusFriend extends WurstplusCommand {
   public static ChatFormatting red;
   public static ChatFormatting green;
   public static ChatFormatting aqua;
   public static ChatFormatting bold;
   public static ChatFormatting reset;

   public WurstplusFriend() {
      super("friend", "To add friends");
   }

   public boolean get_message(String[] message) {
      if (message.length == 1) {
         WurstplusMessageUtil.send_client_message("Add - add friend");
         WurstplusMessageUtil.send_client_message("Del - delete friend");
         WurstplusMessageUtil.send_client_message("List - list friends");
         return true;
      } else if (message.length == 2) {
         if (!message[1].equalsIgnoreCase("list")) {
            if (WurstplusFriendUtil.isFriend(message[1])) {
               WurstplusMessageUtil.send_client_message("Player " + aqua + message[1] + reset + " is friended");
               return true;
            } else {
               WurstplusMessageUtil.send_client_error_message("Player " + red + message[1] + reset + " is not friended");
               return true;
            }
         } else {
            if (WurstplusFriendUtil.friends.isEmpty()) {
               WurstplusMessageUtil.send_client_message("You have " + red + bold + "no" + reset + " friends");
            } else {
               Iterator var4 = WurstplusFriendUtil.friends.iterator();

               while(var4.hasNext()) {
                  WurstplusFriendUtil.Friend friend = (WurstplusFriendUtil.Friend)var4.next();
                  WurstplusMessageUtil.send_client_message("" + aqua + friend.getUsername());
               }
            }

            return true;
         }
      } else {
         if (message.length >= 3) {
            WurstplusFriendUtil.Friend f;
            if (message[1].equalsIgnoreCase("add")) {
               if (WurstplusFriendUtil.isFriend(message[2])) {
                  WurstplusMessageUtil.send_client_message("Player " + aqua + message[2] + reset + " is already friended");
                  return true;
               }

               f = WurstplusFriendUtil.get_friend_object(message[2]);
               if (f == null) {
                  WurstplusMessageUtil.send_client_error_message("Cannot find " + red + bold + "UUID" + reset + "that player");
                  return true;
               }

               WurstplusFriendUtil.friends.add(f);
               WurstplusMessageUtil.send_client_message("Player " + aqua + message[2] + reset + " has been friended");
               return true;
            }

            if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
               if (!WurstplusFriendUtil.isFriend(message[2])) {
                  WurstplusMessageUtil.send_client_message("Player " + red + message[2] + reset + " is already not friended");
                  return true;
               } else {
                  f = (WurstplusFriendUtil.Friend)WurstplusFriendUtil.friends.stream().filter((friendx) -> {
                     return friendx.getUsername().equalsIgnoreCase(message[2]);
                  }).findFirst().get();
                  WurstplusFriendUtil.friends.remove(f);
                  WurstplusMessageUtil.send_client_message("Player " + red + message[2] + reset + " has been unfriended");
                  return true;
               }
            }
         }

         return true;
      }
   }

   static {
      red = ChatFormatting.RED;
      green = ChatFormatting.GREEN;
      aqua = ChatFormatting.AQUA;
      bold = ChatFormatting.BOLD;
      reset = ChatFormatting.RESET;
   }
}
