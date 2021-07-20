package me.listed.listedhack.client.hacks.visual;

import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;

public class WurstplusAlwaysNight extends WurstplusHack {
   @EventHandler
   private Listener<WurstplusEventRender> on_render = new Listener((event) -> {
      if (mc.field_71441_e != null) {
         mc.field_71441_e.func_72877_b(18000L);
      }
   }, new Predicate[0]);

   public WurstplusAlwaysNight() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Always Night";
      this.tag = "AlwaysNight";
      this.description = "see even less";
   }

   public void update() {
      if (mc.field_71441_e != null) {
         mc.field_71441_e.func_72877_b(18000L);
      }
   }
}
