package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;

public class WurstplusAutoLog extends WurstplusHack {
   WurstplusSetting health = this.create("Health", "LogHealth", 7.0D, 0.0D, 36.0D);

   public WurstplusAutoLog() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Auto Log";
      this.tag = "AutoLog";
      this.description = "automatically logs";
   }

   public void update() {
      if (!this.nullCheck()) {
         if (mc.field_71439_g.func_110143_aJ() <= (float)this.health.get_value(1)) {
            mc.field_71441_e.func_72882_A();
            mc.func_71403_a((WorldClient)null);
            mc.func_147108_a(new GuiMainMenu());
         }

      }
   }
}
