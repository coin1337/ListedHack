package me.listed.listedhack.client.hacks.movement;

import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.EventPlayerPushOutOfBlocks;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WurstplusNoPush extends WurstplusHack {
   WurstplusSetting burrow = this.create("Burrow", "Burrow", false);
   @EventHandler
   private Listener<EventPlayerPushOutOfBlocks> push_out_of_blocks = new Listener((p_Event) -> {
      if (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c().equals(Blocks.field_150343_Z) && this.burrow.get_value(true)) {
         p_Event.cancel();
      }

      if (!this.burrow.get_value(true)) {
         p_Event.cancel();
      }

   }, new Predicate[0]);

   public WurstplusNoPush() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "NoPush";
      this.tag = "NoPush";
      this.description = "prevents you getting raped by being forced to get out of a block";
      this.toggle_message = false;
   }
}
