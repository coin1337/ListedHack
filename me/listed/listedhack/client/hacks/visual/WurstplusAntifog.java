package me.listed.listedhack.client.hacks.visual;

import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventSetupFog;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

public class WurstplusAntifog extends WurstplusHack {
   @EventHandler
   private Listener<WurstplusEventSetupFog> setup_fog = new Listener((event) -> {
      event.cancel();
      mc.field_71460_t.func_191514_d(false);
      GlStateManager.func_187432_a(0.0F, -1.0F, 0.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179104_a(1028, 4608);
   }, new Predicate[0]);

   public WurstplusAntifog() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Anti Fog";
      this.tag = "AntiFog";
      this.description = "see even more";
   }
}
