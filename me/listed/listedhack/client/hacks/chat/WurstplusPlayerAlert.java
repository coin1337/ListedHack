package me.listed.listedhack.client.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusPlayerAlert extends WurstplusHack {
   private List<String> people;

   public WurstplusPlayerAlert() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Player Alert";
      this.tag = "PlayerAlert";
      this.description = "bc using ur eyes is overrated";
   }

   public void enable() {
      this.people = new ArrayList();
   }

   public void update() {
      if (!(mc.field_71441_e == null | mc.field_71439_g == null)) {
         List<String> peoplenew = new ArrayList();
         List<EntityPlayer> playerEntities = mc.field_71441_e.field_73010_i;
         Iterator var3 = playerEntities.iterator();

         while(var3.hasNext()) {
            Entity e = (Entity)var3.next();
            if (!e.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
               peoplenew.add(e.func_70005_c_());
            }
         }

         if (peoplenew.size() > 0) {
            var3 = peoplenew.iterator();

            while(var3.hasNext()) {
               String name = (String)var3.next();
               if (!this.people.contains(name)) {
                  if (WurstplusFriendUtil.isFriend(name)) {
                     WurstplusMessageUtil.send_client_message("There is " + ChatFormatting.RESET + ChatFormatting.AQUA + name + ChatFormatting.RESET + " :D");
                  } else {
                     WurstplusMessageUtil.send_client_message("There is " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + ". Yuk");
                  }

                  this.people.add(name);
               }
            }
         }

      }
   }
}
