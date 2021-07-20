package me.listed.listedhack.client.hacks.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import org.lwjgl.input.Mouse;

public class WurstplusMiddleClickFriends extends WurstplusHack {
   private boolean clicked = false;
   public static ChatFormatting red;
   public static ChatFormatting green;
   public static ChatFormatting aqua;
   public static ChatFormatting bold;
   public static ChatFormatting reset;

   public WurstplusMiddleClickFriends() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Middleclick Friends";
      this.tag = "MiddleclickFriends";
      this.description = "you press button and the world becomes a better place :D";
   }

   public void update() {
      if (mc.field_71462_r == null) {
         if (!Mouse.isButtonDown(2)) {
            this.clicked = false;
         } else {
            if (!this.clicked) {
               this.clicked = true;
               RayTraceResult result = mc.field_71476_x;
               if (result == null || result.field_72313_a != Type.ENTITY) {
                  return;
               }

               if (!(result.field_72308_g instanceof EntityPlayer)) {
                  return;
               }

               Entity player = result.field_72308_g;
               WurstplusFriendUtil.Friend f;
               if (WurstplusFriendUtil.isFriend(player.func_70005_c_())) {
                  f = (WurstplusFriendUtil.Friend)WurstplusFriendUtil.friends.stream().filter((friend) -> {
                     return friend.getUsername().equalsIgnoreCase(player.func_70005_c_());
                  }).findFirst().get();
                  WurstplusFriendUtil.friends.remove(f);
                  WurstplusMessageUtil.send_client_message("Player " + red + player.func_70005_c_() + reset + " has been unfriended");
               } else {
                  f = WurstplusFriendUtil.get_friend_object(player.func_70005_c_());
                  WurstplusFriendUtil.friends.add(f);
                  WurstplusMessageUtil.send_client_message("Player " + aqua + player.func_70005_c_() + reset + " has been friended");
               }
            }

         }
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
