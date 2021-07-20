package me.listed.listedhack.client.hacks.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusAutoSus extends WurstplusHack {
   WurstplusSetting delay = this.create("Delay", "autosusdelay", 20, 0, 100);
   List<String> chants = new ArrayList();
   Random r = new Random();
   int tick_delay;

   public WurstplusAutoSus() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Auto Sus";
      this.tag = "AutoSus";
      this.description = "sus";
   }

   protected void enable() {
      this.tick_delay = 0;
      this.chants.add("I just faked tasks ඞ");
      this.chants.add("I just vented ඞ");
      this.chants.add("I just killed a crewmate ඞ");
      this.chants.add("I just posted childporn ඞ");
      this.chants.add("I just sabotaged ඞ");
   }

   public void update() {
      ++this.tick_delay;
      if (this.tick_delay >= this.delay.get_value(1) * 10) {
         String s = (String)this.chants.get(this.r.nextInt(this.chants.size()));
         String name = this.get_random_name();
         if (!name.equals(mc.field_71439_g.func_70005_c_())) {
            mc.field_71439_g.func_71165_d(s.replace("<player>", name));
            this.tick_delay = 0;
         }
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
