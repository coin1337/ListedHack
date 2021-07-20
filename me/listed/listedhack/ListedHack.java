package me.listed.listedhack;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Font;
import me.listed.listedhack.client.event.WurstplusEventHandler;
import me.listed.listedhack.client.event.WurstplusEventRegister;
import me.listed.listedhack.client.guiscreen.WurstplusGUI;
import me.listed.listedhack.client.guiscreen.WurstplusHUD;
import me.listed.listedhack.client.guiscreen.render.components.past.PastGUI;
import me.listed.listedhack.client.guiscreen.render.components.past.font.CustomFontRenderer;
import me.listed.listedhack.client.hacks.RPCHandler;
import me.listed.listedhack.client.manager.WurstplusCommandManager;
import me.listed.listedhack.client.manager.WurstplusConfigManager;
import me.listed.listedhack.client.manager.WurstplusEventManager;
import me.listed.listedhack.client.manager.WurstplusHUDManager;
import me.listed.listedhack.client.manager.WurstplusModuleManager;
import me.listed.listedhack.client.manager.WurstplusSettingManager;
import me.listed.turok.Turok;
import me.listed.turok.task.TurokFont;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(
   modid = "listedhack",
   version = "0.1"
)
public class ListedHack {
   @Instance
   private static ListedHack MASTER;
   public static final String WURSTPLUS_NAME = "ListedHack";
   public static final String WURSTPLUS_VERSION = "0.1";
   public static final String WURSTPLUS_SIGN = " ";
   public static final int WURSTPLUS_KEY_GUI = 54;
   public static final int WURSTPLUS_KEY_DELETE = 211;
   public static final int WURSTPLUS_KEY_GUI_ESCAPE = 1;
   public static Logger wurstplus_register_log;
   private static WurstplusSettingManager setting_manager;
   private static WurstplusConfigManager config_manager;
   private static WurstplusModuleManager module_manager;
   private static WurstplusHUDManager hud_manager;
   public static PastGUI past_gui;
   public static CustomFontRenderer latoFont;
   public static CustomFontRenderer verdanaFont;
   public static CustomFontRenderer arialFont;
   public static WurstplusGUI click_gui;
   public static WurstplusHUD click_hud;
   public static Turok turok;
   public static ChatFormatting g;
   public static ChatFormatting r;

   @EventHandler
   public void WurstplusStarting(FMLInitializationEvent event) {
      this.init_log("ListedHack");
      WurstplusEventHandler.INSTANCE = new WurstplusEventHandler();
      send_minecraft_log("initialising managers");
      setting_manager = new WurstplusSettingManager();
      config_manager = new WurstplusConfigManager();
      module_manager = new WurstplusModuleManager();
      hud_manager = new WurstplusHUDManager();
      WurstplusEventManager event_manager = new WurstplusEventManager();
      WurstplusCommandManager command_manager = new WurstplusCommandManager();
      send_minecraft_log("done");
      send_minecraft_log("initialising guis");
      Display.setTitle("ListedHack");
      click_gui = new WurstplusGUI();
      click_hud = new WurstplusHUD();
      past_gui = new PastGUI();
      send_minecraft_log("done");
      send_minecraft_log("initialising skidded framework");
      turok = new Turok("Turok");
      send_minecraft_log("done");
      send_minecraft_log("initialising commands and events");
      WurstplusEventRegister.register_command_manager(command_manager);
      WurstplusEventRegister.register_module_manager(event_manager);
      send_minecraft_log("done");
      send_minecraft_log("loading settings");
      config_manager.load_settings();
      send_minecraft_log("Loading fonts");
      latoFont = new CustomFontRenderer(new Font("Lato", 0, 18), true, false);
      verdanaFont = new CustomFontRenderer(new Font("Verdana", 0, 18), true, false);
      arialFont = new CustomFontRenderer(new Font("Arial", 0, 18), true, false);
      if (module_manager.get_module_with_tag("ClickGUI").is_active()) {
         module_manager.get_module_with_tag("ClickGUI").set_active(false);
      }

      if (module_manager.get_module_with_tag("AutoCrash").is_active()) {
         module_manager.get_module_with_tag("AutoCrash").set_active(false);
      }

      if (module_manager.get_module_with_tag("AutoLog").is_active()) {
         module_manager.get_module_with_tag("AutoLog").set_active(false);
      }

      if (module_manager.get_module_with_tag("DiscordRPC").is_active()) {
         send_minecraft_log("Loading DiscordRPC");
         RPCHandler.start();
      }

      send_minecraft_log("client started");
      send_minecraft_log("we swaggin");
   }

   public void init_log(String name) {
      wurstplus_register_log = LogManager.getLogger(name);
      send_minecraft_log("starting ListedHack");
   }

   public static void send_minecraft_log(String log) {
      wurstplus_register_log.info(log);
   }

   public static String get_name() {
      return "ListedHack";
   }

   public static String get_version() {
      return "0.1";
   }

   public static String get_actual_user() {
      return Minecraft.func_71410_x().func_110432_I().func_111285_a();
   }

   public static WurstplusConfigManager get_config_manager() {
      return config_manager;
   }

   public static WurstplusModuleManager get_hack_manager() {
      return module_manager;
   }

   public static WurstplusSettingManager get_setting_manager() {
      return setting_manager;
   }

   public static WurstplusHUDManager get_hud_manager() {
      return hud_manager;
   }

   public static WurstplusModuleManager get_module_manager() {
      return module_manager;
   }

   public static WurstplusEventHandler get_event_handler() {
      return WurstplusEventHandler.INSTANCE;
   }

   public static String smoth(String base) {
      return TurokFont.smoth(base);
   }

   static {
      g = ChatFormatting.DARK_GRAY;
      r = ChatFormatting.RESET;
   }
}
