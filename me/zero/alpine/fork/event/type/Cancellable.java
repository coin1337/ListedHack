package me.zero.alpine.fork.event.type;

public class Cancellable implements ICancellable {
   private boolean cancelled;

   public void cancel() {
      this.cancelled = true;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }
}
