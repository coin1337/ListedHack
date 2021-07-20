package me.zero.alpine.fork.bus.type;

import java.util.ArrayList;
import java.util.List;
import me.zero.alpine.fork.bus.EventBus;
import me.zero.alpine.fork.bus.EventManager;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;

public class AttachableEventManager extends EventManager implements AttachableEventBus {
   private final List<EventBus> attached = new ArrayList();

   public void subscribe(Listenable listenable) {
      super.subscribe(listenable);
      if (!this.attached.isEmpty()) {
         this.attached.forEach((bus) -> {
            bus.subscribe(listenable);
         });
      }

   }

   public void subscribe(Listener listener) {
      super.subscribe(listener);
      if (!this.attached.isEmpty()) {
         this.attached.forEach((bus) -> {
            bus.subscribe(listener);
         });
      }

   }

   public void unsubscribe(Listenable listenable) {
      super.unsubscribe(listenable);
      if (!this.attached.isEmpty()) {
         this.attached.forEach((bus) -> {
            bus.unsubscribe(listenable);
         });
      }

   }

   public void unsubscribe(Listener listener) {
      super.unsubscribe(listener);
      if (!this.attached.isEmpty()) {
         this.attached.forEach((bus) -> {
            bus.unsubscribe(listener);
         });
      }

   }

   public void post(Object event) {
      super.post(event);
      if (!this.attached.isEmpty()) {
         this.attached.forEach((bus) -> {
            bus.post(event);
         });
      }

   }

   public void attach(EventBus bus) {
      if (!this.attached.contains(bus)) {
         this.attached.add(bus);
      }

   }

   public void detach(EventBus bus) {
      this.attached.remove(bus);
   }
}
