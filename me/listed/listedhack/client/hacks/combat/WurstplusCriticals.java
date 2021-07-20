package me.listed.listedhack.client.hacks.combat;

import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketUseEntity.Action;

public class WurstplusCriticals extends WurstplusHack {
   WurstplusSetting mode = this.create("Mode", "CriticalsMode", "Packet", this.combobox(new String[]{"Packet", "Jump"}));
   @EventHandler
   private Listener<WurstplusEventPacket.SendPacket> listener = new Listener((event) -> {
      if (event.get_packet() instanceof CPacketUseEntity) {
         CPacketUseEntity event_entity = (CPacketUseEntity)event.get_packet();
         if (event_entity.func_149565_c() == Action.ATTACK && mc.field_71439_g.field_70122_E) {
            if (this.mode.in("Packet")) {
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.10000000149011612D, mc.field_71439_g.field_70161_v, false));
               mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
            } else if (this.mode.in("Jump")) {
               mc.field_71439_g.func_70664_aZ();
            }
         }
      }

   }, new Predicate[0]);

   public WurstplusCriticals() {
      super(WurstplusCategory.WURSTPLUS_COMBAT);
      this.name = "Criticals";
      this.tag = "Criticals";
      this.description = "You can hit with criticals when attack.";
   }

   public String array_detail() {
      return this.mode.get_current_value();
   }
}
