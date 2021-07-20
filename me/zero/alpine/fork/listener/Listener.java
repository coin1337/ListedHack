package me.zero.alpine.fork.listener;

import java.util.function.Predicate;
import net.jodah.typetools.TypeResolver;

public class Listener<T> implements EventHook<T> {
   private final Class<T> target;
   private final EventHook<T> hook;
   private final Predicate<T>[] filters;
   private final int priority;

   @SafeVarargs
   public Listener(EventHook<T> hook, Predicate<T>... filters) {
      this(hook, 0, filters);
   }

   @SafeVarargs
   public Listener(EventHook<T> hook, int priority, Predicate<T>... filters) {
      this.hook = hook;
      this.priority = priority;
      this.target = TypeResolver.resolveRawArgument(EventHook.class, hook.getClass());
      this.filters = filters;
   }

   public Class<T> getTarget() {
      return this.target;
   }

   public int getPriority() {
      return this.priority;
   }

   public void invoke(T event) {
      if (this.filters.length > 0) {
         Predicate[] var2 = this.filters;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Predicate<T> filter = var2[var4];
            if (!filter.test(event)) {
               return;
            }
         }
      }

      this.hook.invoke(event);
   }
}
