package me.listed.listedhack.client.hacks.other;

import me.listed.listedhack.client.event.events.PacketEvent;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WurstplusSwing extends WurstplusHack {
   WurstplusSetting offhand = this.create("Offhand", "OffhandSwing", false);

   public WurstplusSwing() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Swing";
      this.tag = "Swing";
      this.description = "swings with offhand";
   }

   public void update() {
      if (!this.nullCheck()) {
         if (!this.offhand.get_value(true)) {
            mc.field_71439_g.field_184622_au = EnumHand.OFF_HAND;
         }

      }
   }

   @SubscribeEvent
   public void Packet(PacketEvent event) {
      event.setCanceled(true);
   }
}
