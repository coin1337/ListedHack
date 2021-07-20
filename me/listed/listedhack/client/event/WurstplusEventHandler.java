package me.listed.listedhack.client.event;

import java.util.Arrays;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class WurstplusEventHandler implements Listenable {
   public static WurstplusEventHandler INSTANCE;
   static final float[] ticks = new float[20];
   private long last_update_tick;
   private int next_index = 0;
   @EventHandler
   private Listener<WurstplusEventPacket.ReceivePacket> receive_event_packet = new Listener((event) -> {
      if (event.get_packet() instanceof SPacketTimeUpdate) {
         INSTANCE.update_time();
      }

   }, new Predicate[0]);

   public WurstplusEventHandler() {
      WurstplusEventBus.EVENT_BUS.subscribe((Listenable)this);
      this.reset_tick();
   }

   public float get_tick_rate() {
      float num_ticks = 0.0F;
      float sum_ticks = 0.0F;
      float[] var3 = ticks;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         float tick = var3[var5];
         if (tick > 0.0F) {
            sum_ticks += tick;
            ++num_ticks;
         }
      }

      return MathHelper.func_76131_a(sum_ticks / num_ticks, 0.0F, 20.0F);
   }

   public void reset_tick() {
      this.next_index = 0;
      this.last_update_tick = -1L;
      Arrays.fill(ticks, 0.0F);
   }

   public void update_time() {
      if (this.last_update_tick != -1L) {
         float time = (float)(System.currentTimeMillis() - this.last_update_tick) / 1000.0F;
         ticks[this.next_index % ticks.length] = MathHelper.func_76131_a(20.0F / time, 0.0F, 20.0F);
         ++this.next_index;
      }

      this.last_update_tick = System.currentTimeMillis();
   }
}
