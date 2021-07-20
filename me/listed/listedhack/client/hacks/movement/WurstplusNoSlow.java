package me.listed.listedhack.client.hacks.movement;

import java.util.function.Predicate;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;

public class WurstplusNoSlow extends WurstplusHack {
   WurstplusSetting Food = this.create("Food", "Food", true);
   WurstplusSetting Web = this.create("Web", "Web", true);
   WurstplusSetting Soulsand = this.create("Soulsand", "Soulsand", true);
   WurstplusSetting Slimeblock = this.create("Slimeblock", "Slimeblock", true);
   @EventHandler
   private final Listener<InputUpdateEvent> eventListener = new Listener((event) -> {
      if (mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH() && this.Food.get_value(true)) {
         MovementInput var10000 = event.getMovementInput();
         var10000.field_78902_a *= 5.0F;
         var10000 = event.getMovementInput();
         var10000.field_192832_b *= 5.0F;
      }

   }, new Predicate[0]);

   public WurstplusNoSlow() {
      super(WurstplusCategory.WURSTPLUS_MOVEMENT);
      this.name = "NoSlow";
      this.tag = "NoSlow";
      this.description = "no slow down";
   }
}
