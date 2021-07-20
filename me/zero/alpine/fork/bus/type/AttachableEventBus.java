package me.zero.alpine.fork.bus.type;

import me.zero.alpine.fork.bus.EventBus;

public interface AttachableEventBus extends EventBus {
   void attach(EventBus var1);

   void detach(EventBus var1);
}
