package me.zero.alpine.fork.bus;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;

public class EventManager implements EventBus {
   private final Map<Listenable, List<Listener>> SUBSCRIPTION_CACHE = new ConcurrentHashMap();
   private final Map<Class<?>, List<Listener>> SUBSCRIPTION_MAP = new ConcurrentHashMap();

   public void subscribe(Listenable listenable) {
      List<Listener> listeners = (List)this.SUBSCRIPTION_CACHE.computeIfAbsent(listenable, (o) -> {
         return (List)Arrays.stream(o.getClass().getDeclaredFields()).filter(EventManager::isValidField).map((field) -> {
            return asListener(o, field);
         }).filter(Objects::nonNull).collect(Collectors.toList());
      });
      listeners.forEach(this::subscribe);
   }

   public void subscribe(Listener listener) {
      List<Listener> listeners = (List)this.SUBSCRIPTION_MAP.computeIfAbsent(listener.getTarget(), (target) -> {
         return new CopyOnWriteArrayList();
      });

      int index;
      for(index = 0; index < listeners.size() && listener.getPriority() <= ((Listener)listeners.get(index)).getPriority(); ++index) {
      }

      listeners.add(index, listener);
   }

   public void unsubscribe(Listenable listenable) {
      List<Listener> objectListeners = (List)this.SUBSCRIPTION_CACHE.get(listenable);
      if (objectListeners != null) {
         this.SUBSCRIPTION_MAP.values().forEach((listeners) -> {
            listeners.removeIf(objectListeners::contains);
         });
      }
   }

   public void unsubscribe(Listener listener) {
      ((List)this.SUBSCRIPTION_MAP.get(listener.getTarget())).removeIf((l) -> {
         return l.equals(listener);
      });
   }

   public void post(Object event) {
      List<Listener> listeners = (List)this.SUBSCRIPTION_MAP.get(event.getClass());
      if (listeners != null) {
         listeners.forEach((listener) -> {
            listener.invoke(event);
         });
      }

   }

   private static boolean isValidField(Field field) {
      return field.isAnnotationPresent(EventHandler.class) && Listener.class.isAssignableFrom(field.getType());
   }

   private static Listener asListener(Listenable listenable, Field field) {
      try {
         boolean accessible = field.isAccessible();
         field.setAccessible(true);
         Listener listener = (Listener)field.get(listenable);
         field.setAccessible(accessible);
         return listener == null ? null : listener;
      } catch (IllegalAccessException var4) {
         return null;
      }
   }
}
