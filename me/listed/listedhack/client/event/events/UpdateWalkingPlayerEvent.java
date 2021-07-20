package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.EventStage;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class UpdateWalkingPlayerEvent extends EventStage {
   public UpdateWalkingPlayerEvent(int stage) {
      super(stage);
   }
}
