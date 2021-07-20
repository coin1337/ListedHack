package me.listed.listedhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.util.WurstplusOnlineFriends;
import net.minecraft.entity.Entity;

public class WurstplusFriendList extends WurstplusPinnable {
   int passes;
   public static ChatFormatting bold;

   public WurstplusFriendList() {
      super("Friends", "Friends", 1.0F, 0, 0);
   }

   public void render() {
      int nl_r = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      int nl_g = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      int nl_b = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      int nl_a = ListedHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
      String line1 = bold + "the_fellas: ";
      this.passes = 0;
      this.create_line(line1, this.docking(1, line1), 2, nl_r, nl_g, nl_b, nl_a);
      if (!WurstplusOnlineFriends.getFriends().isEmpty()) {
         Iterator var6 = WurstplusOnlineFriends.getFriends().iterator();

         while(var6.hasNext()) {
            Entity e = (Entity)var6.next();
            ++this.passes;
            this.create_line(e.func_70005_c_(), this.docking(1, e.func_70005_c_()), this.get(line1, "height") * this.passes, nl_r, nl_g, nl_b, nl_a);
         }
      }

      this.set_width(this.get(line1, "width") + 2);
      this.set_height(this.get(line1, "height") + 2);
   }

   static {
      bold = ChatFormatting.BOLD;
   }
}
