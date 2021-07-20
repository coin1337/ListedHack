package me.listed.listedhack.client.manager;

import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.command.WurstplusCommand;
import me.listed.listedhack.client.command.WurstplusCommands;
import me.listed.listedhack.client.event.WurstplusEventBus;
import me.listed.listedhack.client.event.events.WurstplusEventGameOverlay;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class WurstplusEventManager {
   private final Minecraft mc = Minecraft.func_71410_x();

   @SubscribeEvent
   public void onUpdate(LivingUpdateEvent event) {
      if (!event.isCanceled()) {
         ;
      }
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.mc.field_71439_g != null) {
         ListedHack.get_hack_manager().update();
      }
   }

   @SubscribeEvent
   public void onWorldRender(RenderWorldLastEvent event) {
      if (!event.isCanceled()) {
         ListedHack.get_hack_manager().render(event);
      }
   }

   @SubscribeEvent
   public void onRender(Post event) {
      if (!event.isCanceled()) {
         WurstplusEventBus.EVENT_BUS.post(new WurstplusEventGameOverlay(event.getPartialTicks(), new ScaledResolution(this.mc)));
         ElementType target = ElementType.EXPERIENCE;
         if (!this.mc.field_71439_g.func_184812_l_() && this.mc.field_71439_g.func_184187_bx() instanceof AbstractHorse) {
            target = ElementType.HEALTHMOUNT;
         }

         if (event.getType() == target) {
            ListedHack.get_hack_manager().render();
            if (!ListedHack.get_hack_manager().get_module_with_tag("ClickGUI").is_active()) {
               ListedHack.get_hud_manager().render();
            }

            GL11.glPushMatrix();
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GlStateManager.func_179147_l();
            GL11.glPopMatrix();
            RenderHelp.release_gl();
         }

      }
   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL,
      receiveCanceled = true
   )
   public void onKeyInput(KeyInputEvent event) {
      if (Keyboard.getEventKeyState()) {
         ListedHack.get_hack_manager().bind(Keyboard.getEventKey());
      }

   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL
   )
   public void onChat(ClientChatEvent event) {
      String message = event.getMessage();
      String[] message_args = WurstplusCommandManager.command_list.get_message(event.getMessage());
      boolean true_command = false;
      if (message_args.length > 0) {
         event.setCanceled(true);
         this.mc.field_71456_v.func_146158_b().func_146239_a(event.getMessage());
         Iterator var5 = WurstplusCommands.get_pure_command_list().iterator();

         while(var5.hasNext()) {
            WurstplusCommand command = (WurstplusCommand)var5.next();

            try {
               if (WurstplusCommandManager.command_list.get_message(event.getMessage())[0].equalsIgnoreCase(command.get_name())) {
                  true_command = command.get_message(WurstplusCommandManager.command_list.get_message(event.getMessage()));
               }
            } catch (Exception var8) {
            }
         }

         if (!true_command && WurstplusCommandManager.command_list.has_prefix(event.getMessage())) {
            WurstplusMessageUtil.send_client_message("try " + WurstplusCommandManager.get_prefix() + "help list for a list of commands");
            true_command = false;
         }
      }

   }

   @SubscribeEvent
   public void onInputUpdate(InputUpdateEvent event) {
      WurstplusEventBus.EVENT_BUS.post(event);
   }
}
