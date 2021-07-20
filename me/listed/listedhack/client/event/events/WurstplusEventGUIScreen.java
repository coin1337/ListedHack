package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.gui.GuiScreen;

public class WurstplusEventGUIScreen extends WurstplusEventCancellable {
   private final GuiScreen guiscreen;

   public WurstplusEventGUIScreen(GuiScreen screen) {
      this.guiscreen = screen;
   }

   public GuiScreen get_guiscreen() {
      return this.guiscreen;
   }
}
