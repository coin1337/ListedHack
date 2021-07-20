package me.listed.listedhack.client.hacks.combat;

import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusBreakUtil;
import me.listed.listedhack.client.util.WurstplusEntityUtil;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WurstplusAutoMine extends WurstplusHack {
   WurstplusSetting end_crystal = this.create("End Crystal", "MineEndCrystal", false);
   WurstplusSetting range = this.create("Range", "MineRange", 4, 0, 6);

   public WurstplusAutoMine() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Auto Mine";
      this.tag = "AutoMine";
      this.description = "jumpy is now never going to use the client again";
   }

   protected void enable() {
      BlockPos target_block = null;
      Iterator var2 = mc.field_71441_e.field_73010_i.iterator();

      while(var2.hasNext()) {
         EntityPlayer player = (EntityPlayer)var2.next();
         if (!(mc.field_71439_g.func_70032_d(player) > (float)this.range.get_value(1))) {
            BlockPos p = WurstplusEntityUtil.is_cityable(player, this.end_crystal.get_value(true));
            if (p != null) {
               target_block = p;
            }
         }
      }

      if (target_block == null) {
         WurstplusMessageUtil.send_client_message("cannot find block");
         this.disable();
      }

      WurstplusBreakUtil.set_current_block(target_block);
   }

   protected void disable() {
      WurstplusBreakUtil.set_current_block((BlockPos)null);
   }
}
