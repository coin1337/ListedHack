package me.zero.alpine.fork.listener;

@FunctionalInterface
public interface EventHook<T> {
   void invoke(T var1);
}
