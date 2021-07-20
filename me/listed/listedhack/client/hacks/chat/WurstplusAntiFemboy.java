package me.listed.listedhack.client.hacks.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusAntiFemboy extends WurstplusHack {
   WurstplusSetting delay = this.create("Delay", "AntiFemboyDelay", 10, 0, 100);
   WurstplusSetting chanter = this.create("Chanter", "AntiFemboyChanter", false);
   List<String> chants = new ArrayList();
   Random r = new Random();
   int tick_delay;

   public WurstplusAntiFemboy() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Anti Femboy";
      this.tag = "AntiFemboy";
      this.description = "FUCK FEMBOYS";
   }

   protected void enable() {
      this.tick_delay = 0;
      this.chants.add("<player> you fucking femboy kys");
      this.chants.add("FUCK FEMBOYS");
      this.chants.add("FEMBOYS SHOULD HANG THEMSELVES");
      this.chants.add("FEMBOYS KYS");
      this.chants.add("#NOMOREFEMBOYS");
      this.chants.add("#FUCKFEMBOYS");
      this.chants.add("KILL ALL FEMBOYS");
      this.chants.add("#KILLALLFEMBOYS");
      this.chants.add("IF YOU'RE A FEMBOY KYS");
      this.chants.add("<player> I HOPE YOU HANGED A FEMBOY TODAY");
      this.chants.add("#HANGALLFEMBOYS");
   }

   public void update() {
      if (this.chanter.get_value(true)) {
         ++this.tick_delay;
         if (this.tick_delay < this.delay.get_value(1) * 10) {
            return;
         }

         String s = (String)this.chants.get(this.r.nextInt(this.chants.size()));
         String name = this.get_random_name();
         if (name.equals(mc.field_71439_g.func_70005_c_())) {
            return;
         }

         mc.field_71439_g.func_71165_d(s.replace("<player>", name));
         this.tick_delay = 0;
      }

   }

   public String get_random_name() {
      List<EntityPlayer> players = mc.field_71441_e.field_73010_i;
      return ((EntityPlayer)players.get(this.r.nextInt(players.size()))).func_70005_c_();
   }

   public String random_string(String[] list) {
      return list[this.r.nextInt(list.length)];
   }
}
