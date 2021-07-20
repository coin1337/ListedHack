package me.zero.alpine.fork.bus;

import java.util.Arrays;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;

public interface EventBus {
   void subscribe(Listenable var1);

   void subscribe(Listener var1);

   default void subscribeAll(Listenable... listenables) {
      Arrays.stream(listenables).forEach(this::subscribe);
   }

   default void subscribeAll(Iterable<Listenable> listenables) {
      listenables.forEach(this::subscribe);
   }

   default void subscribeAll(Listener... listeners) {
      Arrays.stream(listeners).forEach(this::subscribe);
   }

   void unsubscribe(Listenable var1);

   void unsubscribe(Listener var1);

   default void unsubscribeAll(Listenable... listenables) {
      Arrays.stream(listenables).forEach(this::unsubscribe);
   }

   default void unsubscribeAll(Iterable<Listenable> listenables) {
      listenables.forEach(this::unsubscribe);
   }

   default void unsubscribeAll(Listener... listeners) {
      Arrays.stream(listeners).forEach(this::unsubscribe);
   }

   void post(Object var1);
}
