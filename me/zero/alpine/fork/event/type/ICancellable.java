package me.zero.alpine.fork.event.type;

public interface ICancellable {
   void cancel();

   boolean isCancelled();
}
