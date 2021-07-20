package me.listed.listedhack.client.hacks.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusEnemyUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import org.lwjgl.input.Mouse;

public class WurstplusMiddleClickEnemies extends WurstplusHack {
   private boolean clicked = false;
   public static ChatFormatting red;
   public static ChatFormatting green;
   public static ChatFormatting aqua;
   public static ChatFormatting bold;
   public static ChatFormatting reset;

   public WurstplusMiddleClickEnemies() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Middleclick Enemies";
      this.tag = "MiddleclickEnemies";
      this.description = "enemies ppl";
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
               WurstplusEnemyUtil.Enemy f;
               if (WurstplusEnemyUtil.isEnemy(player.func_70005_c_())) {
                  f = (WurstplusEnemyUtil.Enemy)WurstplusEnemyUtil.enemies.stream().filter((enemy) -> {
                     return enemy.getUsername().equalsIgnoreCase(player.func_70005_c_());
                  }).findFirst().get();
                  WurstplusEnemyUtil.enemies.remove(f);
                  WurstplusMessageUtil.send_client_message("Player " + aqua + player.func_70005_c_() + reset + " is now not your Enemy");
               } else {
                  f = WurstplusEnemyUtil.get_enemy_object(player.func_70005_c_());
                  WurstplusEnemyUtil.enemies.add(f);
                  WurstplusMessageUtil.send_client_message("Player " + red + player.func_70005_c_() + reset + " is now your Enemy");
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
