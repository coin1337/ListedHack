package me.listed.listedhack.mixins;

import java.util.List;
import me.listed.listedhack.ListedHack;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({GuiNewChat.class})
public class MixininfiniteChat extends Gui {
   @Redirect(
      method = {"setChatLine"},
      at = @At(
   value = "INVOKE",
   target = "Ljava/util/List;size()I",
   ordinal = 0
)
   )
   public int drawnChatLinesSize(List<ChatLine> list) {
      return ListedHack.get_hack_manager().get_module_with_tag("InfiniteChat").is_active() ? -2147483647 : list.size();
   }

   @Redirect(
      method = {"setChatLine"},
      at = @At(
   value = "INVOKE",
   target = "Ljava/util/List;size()I",
   ordinal = 2
)
   )
   public int chatLinesSize(List<ChatLine> list) {
      return ListedHack.get_hack_manager().get_module_with_tag("InfiniteChat").is_active() ? -2147483647 : list.size();
   }
}
