package me.listed.listedhack.client.hacks.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusAutoToxic extends WurstplusHack {
   WurstplusSetting delay = this.create("Delay", "AutoToxicDelay", 10, 0, 100);
   WurstplusSetting spammer = this.create("Spammer", "AutoToxicSpammer", true);
   List<String> chants = new ArrayList();
   Random r = new Random();
   int tick_delay;

   public WurstplusAutoToxic() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Auto Toxic";
      this.tag = "AutoToxic";
      this.description = "Very toxic";
   }

   protected void enable() {
      this.tick_delay = 0;
      this.chants.add("<player> kys jew");
      this.chants.add("<player> you're so fucking bad");
      this.chants.add("<player> so fucking trash kys");
      this.chants.add("<player> SHIT BOX");
      this.chants.add("<player> kys nigga");
      this.chants.add("<player> kys pedo");
      this.chants.add("<player> kys femboy");
      this.chants.add("<player> nn sitdown");
      this.chants.add("yall so bad");
      this.chants.add("<player> kys nn");
      this.chants.add("<player> cope noname");
   }

   public void update() {
      if (this.spammer.get_value(true)) {
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
