package me.listed.listedhack.client.hacks;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreenAddServer;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.GuiConnecting;

public class RPCHandler {
   private static final Minecraft mc = Minecraft.func_71410_x();
   private static final DiscordRPC rpc;
   public static DiscordRichPresence presence;
   private static String details;
   private static String state;

   public static void start() {
      DiscordEventHandlers handlers = new DiscordEventHandlers();
      handlers.disconnected = (var1, var2) -> {
         System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2);
      };
      rpc.Discord_Initialize("825660054060662824", handlers, true, "");
      presence.startTimestamp = System.currentTimeMillis() / 1000L;
      presence.largeImageKey = "listedhack";
      presence.largeImageText = "ListedHack v0.1";
      rpc.Discord_UpdatePresence(presence);
      (new Thread(() -> {
         while(!Thread.currentThread().isInterrupted()) {
            try {
               rpc.Discord_RunCallbacks();
               details = "";
               state = "";
               if (mc.field_71441_e == null) {
                  details = "We gamin";
                  if (mc.field_71462_r instanceof GuiWorldSelection) {
                     state = "Selecting a world";
                  } else if (mc.field_71462_r instanceof GuiMainMenu) {
                     state = "In the main menu";
                  } else if (!(mc.field_71462_r instanceof GuiMultiplayer) && !(mc.field_71462_r instanceof GuiScreenAddServer) && !(mc.field_71462_r instanceof GuiScreenServerList)) {
                     if (mc.field_71462_r instanceof GuiScreenResourcePacks) {
                        state = "Selecting a texture pack";
                     } else if (mc.field_71462_r instanceof GuiDisconnected) {
                        state = "Disconnecting from a server";
                     } else if (mc.field_71462_r instanceof GuiConnecting) {
                        state = "Connecting to a server";
                     } else if (!(mc.field_71462_r instanceof GuiCreateFlatWorld) && !(mc.field_71462_r instanceof GuiCreateWorld)) {
                        state = "Doin yo mother";
                     } else {
                        state = "Creating a new world";
                     }
                  } else {
                     state = "Selecting a server";
                  }
               } else if (mc.field_71439_g != null) {
                  int health = Math.round(mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj());
                  int armor = Math.round((float)mc.field_71439_g.func_70658_aO());
                  state = "Player | Health " + health + " | Armor " + armor;
                  if (mc.field_71439_g.field_70128_L) {
                     state = "Player | Dead | Armor " + armor;
                  }

                  if (mc.func_71387_A()) {
                     details = "Playing singleplayer";
                  } else if (!mc.func_71387_A()) {
                     details = "Playing on " + mc.func_147104_D().field_78845_b;
                  }
               }

               presence.details = details;
               presence.state = state;
               rpc.Discord_UpdatePresence(presence);
            } catch (Exception var3) {
               var3.printStackTrace();
            }

            try {
               Thread.sleep(5000L);
            } catch (InterruptedException var2) {
               var2.printStackTrace();
            }
         }

      }, "Discord-RPC-Callback-Handler")).start();
   }

   public static void shutdown() {
      DiscordRPC.INSTANCE.Discord_ClearPresence();
      DiscordRPC.INSTANCE.Discord_Shutdown();
   }

   static {
      rpc = DiscordRPC.INSTANCE;
      presence = new DiscordRichPresence();
   }
}
