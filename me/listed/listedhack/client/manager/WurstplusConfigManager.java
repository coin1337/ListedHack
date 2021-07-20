package me.listed.listedhack.client.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.components.WurstplusFrame;
import me.listed.listedhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusDrawnUtil;
import me.listed.listedhack.client.util.WurstplusEnemyUtil;
import me.listed.listedhack.client.util.WurstplusEzMessageUtil;
import me.listed.listedhack.client.util.WurstplusFriendUtil;

public class WurstplusConfigManager {
   private final String MAIN_FOLDER = "ListedHack/";
   private final String CONFIGS_FOLDER = "ListedHack/configs/";
   private String ACTIVE_CONFIG_FOLDER = "ListedHack/configs/default/";
   private final String CLIENT_FILE = "client.json";
   private final String CONFIG_FILE = "config.txt";
   private final String DRAWN_FILE = "drawn.txt";
   private final String EZ_FILE = "ez.txt";
   private final String ENEMIES_FILE = "enemies.json";
   private final String FRIENDS_FILE = "friends.json";
   private final String HUD_FILE = "hud.json";
   private final String BINDS_FILE = "binds.txt";
   private final String CLIENT_DIR = "ListedHack/client.json";
   private final String CONFIG_DIR = "ListedHack/config.txt";
   private final String DRAWN_DIR = "ListedHack/drawn.txt";
   private final String EZ_DIR = "ListedHack/ez.txt";
   private final String ENEMIES_DIR = "ListedHack/enemies.json";
   private final String FRIENDS_DIR = "ListedHack/friends.json";
   private final String HUD_DIR = "ListedHack/hud.json";
   private String CURRENT_CONFIG_DIR;
   private String BINDS_DIR;
   private final Path MAIN_FOLDER_PATH;
   private final Path CONFIGS_FOLDER_PATH;
   private Path ACTIVE_CONFIG_FOLDER_PATH;
   private final Path CLIENT_PATH;
   private final Path CONFIG_PATH;
   private final Path DRAWN_PATH;
   private final Path EZ_PATH;
   private final Path ENEMIES_PATH;
   private final Path FRIENDS_PATH;
   private final Path HUD_PATH;
   private Path BINDS_PATH;
   private Path CURRENT_CONFIG_PATH;

   public WurstplusConfigManager() {
      this.CURRENT_CONFIG_DIR = "ListedHack/ListedHack/configs/" + this.ACTIVE_CONFIG_FOLDER;
      this.BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
      this.MAIN_FOLDER_PATH = Paths.get("ListedHack/");
      this.CONFIGS_FOLDER_PATH = Paths.get("ListedHack/configs/");
      this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get(this.ACTIVE_CONFIG_FOLDER);
      this.CLIENT_PATH = Paths.get("ListedHack/client.json");
      this.CONFIG_PATH = Paths.get("ListedHack/config.txt");
      this.DRAWN_PATH = Paths.get("ListedHack/drawn.txt");
      this.EZ_PATH = Paths.get("ListedHack/ez.txt");
      this.ENEMIES_PATH = Paths.get("ListedHack/enemies.json");
      this.FRIENDS_PATH = Paths.get("ListedHack/friends.json");
      this.HUD_PATH = Paths.get("ListedHack/hud.json");
      this.BINDS_PATH = Paths.get(this.BINDS_DIR);
      this.CURRENT_CONFIG_PATH = Paths.get(this.CURRENT_CONFIG_DIR);
   }

   public boolean set_active_config_folder(String folder) {
      if (folder.equals(this.ACTIVE_CONFIG_FOLDER)) {
         return false;
      } else {
         this.ACTIVE_CONFIG_FOLDER = "ListedHack/configs/" + folder;
         this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get(this.ACTIVE_CONFIG_FOLDER);
         this.CURRENT_CONFIG_DIR = "ListedHack/ListedHack/configs/" + this.ACTIVE_CONFIG_FOLDER;
         this.CURRENT_CONFIG_PATH = Paths.get(this.CURRENT_CONFIG_DIR);
         this.BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
         this.BINDS_PATH = Paths.get(this.BINDS_DIR);
         this.load_settings();
         return true;
      }
   }

   private void save_ezmessage() throws IOException {
      FileWriter writer = new FileWriter("ListedHack/ez.txt");

      try {
         writer.write(WurstplusEzMessageUtil.get_message());
      } catch (Exception var3) {
         writer.write("test message");
      }

      writer.close();
   }

   private void load_ezmessage() throws IOException {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = Files.readAllLines(this.EZ_PATH).iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         sb.append(s);
      }

      WurstplusEzMessageUtil.set_message(sb.toString());
   }

   private void save_drawn() throws IOException {
      FileWriter writer = new FileWriter("ListedHack/drawn.txt");
      Iterator var2 = WurstplusDrawnUtil.hidden_tags.iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         writer.write(s + System.lineSeparator());
      }

      writer.close();
   }

   private void load_drawn() throws IOException {
      WurstplusDrawnUtil.hidden_tags = Files.readAllLines(this.DRAWN_PATH);
   }

   private void save_friends() throws IOException {
      Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
      String json = gson.toJson(WurstplusFriendUtil.friends);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("ListedHack/friends.json"), StandardCharsets.UTF_8);
      file.write(json);
      file.close();
   }

   private void load_friends() throws IOException {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get("ListedHack/friends.json"));
      WurstplusFriendUtil.friends = (ArrayList)gson.fromJson(reader, (new TypeToken<ArrayList<WurstplusFriendUtil.Friend>>() {
      }).getType());
      reader.close();
   }

   private void save_enemies() throws IOException {
      Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
      String json = gson.toJson(WurstplusEnemyUtil.enemies);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("ListedHack/enemies.json"), StandardCharsets.UTF_8);
      file.write(json);
      file.close();
   }

   private void load_enemies() throws IOException {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get("ListedHack/enemies.json"));
      WurstplusEnemyUtil.enemies = (ArrayList)gson.fromJson(reader, (new TypeToken<ArrayList<WurstplusEnemyUtil.Enemy>>() {
      }).getType());
      reader.close();
   }

   private void save_hacks() throws IOException {
      Iterator var1 = ListedHack.get_hack_manager().get_array_hacks().iterator();

      while(var1.hasNext()) {
         WurstplusHack hack = (WurstplusHack)var1.next();
         String file_name = this.ACTIVE_CONFIG_FOLDER + hack.get_tag() + ".txt";
         Path file_path = Paths.get(file_name);
         this.delete_file(file_name);
         this.verify_file(file_path);
         File file = new File(file_name);
         BufferedWriter br = new BufferedWriter(new FileWriter(file));
         Iterator var7 = ListedHack.get_setting_manager().get_settings_with_hack(hack).iterator();

         while(var7.hasNext()) {
            WurstplusSetting setting = (WurstplusSetting)var7.next();
            String var9 = setting.get_type();
            byte var10 = -1;
            switch(var9.hashCode()) {
            case -1377687758:
               if (var9.equals("button")) {
                  var10 = 0;
               }
               break;
            case -612288131:
               if (var9.equals("combobox")) {
                  var10 = 1;
               }
               break;
            case -151809121:
               if (var9.equals("integerslider")) {
                  var10 = 4;
               }
               break;
            case 102727412:
               if (var9.equals("label")) {
                  var10 = 2;
               }
               break;
            case 1954943986:
               if (var9.equals("doubleslider")) {
                  var10 = 3;
               }
            }

            switch(var10) {
            case 0:
               br.write(setting.get_tag() + ":" + setting.get_value(true) + "\r\n");
               break;
            case 1:
               br.write(setting.get_tag() + ":" + setting.get_current_value() + "\r\n");
               break;
            case 2:
               br.write(setting.get_tag() + ":" + setting.get_value("") + "\r\n");
               break;
            case 3:
               br.write(setting.get_tag() + ":" + setting.get_value(1.0D) + "\r\n");
               break;
            case 4:
               br.write(setting.get_tag() + ":" + setting.get_value(1) + "\r\n");
            }
         }

         br.close();
      }

   }

   private void load_hacks() throws IOException {
      BufferedReader br;
      for(Iterator var1 = ListedHack.get_hack_manager().get_array_hacks().iterator(); var1.hasNext(); br.close()) {
         WurstplusHack hack = (WurstplusHack)var1.next();
         String file_name = this.ACTIVE_CONFIG_FOLDER + hack.get_tag() + ".txt";
         File file = new File(file_name);
         FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
         DataInputStream di_stream = new DataInputStream(fi_stream);
         br = new BufferedReader(new InputStreamReader(di_stream));
         ArrayList bugged_lines = new ArrayList();

         String line;
         while((line = br.readLine()) != null) {
            try {
               String colune = line.trim();
               String tag = colune.split(":")[0];
               String value = colune.split(":")[1];
               WurstplusSetting setting = ListedHack.get_setting_manager().get_setting_with_tag(hack, tag);

               try {
                  String var14 = setting.get_type();
                  byte var15 = -1;
                  switch(var14.hashCode()) {
                  case -1377687758:
                     if (var14.equals("button")) {
                        var15 = 0;
                     }
                     break;
                  case -612288131:
                     if (var14.equals("combobox")) {
                        var15 = 4;
                     }
                     break;
                  case -151809121:
                     if (var14.equals("integerslider")) {
                        var15 = 3;
                     }
                     break;
                  case 102727412:
                     if (var14.equals("label")) {
                        var15 = 1;
                     }
                     break;
                  case 1954943986:
                     if (var14.equals("doubleslider")) {
                        var15 = 2;
                     }
                  }

                  switch(var15) {
                  case 0:
                     setting.set_value(Boolean.parseBoolean(value));
                     break;
                  case 1:
                     setting.set_value(value);
                     break;
                  case 2:
                     setting.set_value(Double.parseDouble(value));
                     break;
                  case 3:
                     setting.set_value(Integer.parseInt(value));
                     break;
                  case 4:
                     setting.set_current_value(value);
                  }
               } catch (Exception var16) {
                  bugged_lines.add(colune);
                  ListedHack.send_minecraft_log("Error loading '" + value + "' to setting '" + tag + "'");
                  break;
               }
            } catch (Exception var17) {
            }
         }
      }

   }

   private void save_client() throws IOException {
      Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
      JsonParser parser = new JsonParser();
      JsonObject main_json = new JsonObject();
      JsonObject config = new JsonObject();
      JsonObject gui = new JsonObject();
      config.add("name", new JsonPrimitive(ListedHack.get_name()));
      config.add("version", new JsonPrimitive(ListedHack.get_version()));
      config.add("user", new JsonPrimitive(ListedHack.get_actual_user()));
      config.add("prefix", new JsonPrimitive(WurstplusCommandManager.get_prefix()));
      Iterator var6 = ListedHack.click_gui.get_array_frames().iterator();

      while(var6.hasNext()) {
         WurstplusFrame frames_gui = (WurstplusFrame)var6.next();
         JsonObject frame_info = new JsonObject();
         frame_info.add("name", new JsonPrimitive(frames_gui.get_name()));
         frame_info.add("tag", new JsonPrimitive(frames_gui.get_tag()));
         frame_info.add("x", new JsonPrimitive(frames_gui.get_x()));
         frame_info.add("y", new JsonPrimitive(frames_gui.get_y()));
         gui.add(frames_gui.get_tag(), frame_info);
      }

      main_json.add("configuration", config);
      main_json.add("gui", gui);
      JsonElement json_pretty = parser.parse(main_json.toString());
      String json = gson.toJson(json_pretty);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("ListedHack/client.json"), StandardCharsets.UTF_8);
      file.write(json);
      file.close();
   }

   private void load_client() throws IOException {
      InputStream stream = Files.newInputStream(this.CLIENT_PATH);
      JsonObject json_client = (new JsonParser()).parse(new InputStreamReader(stream)).getAsJsonObject();
      JsonObject json_config = json_client.get("configuration").getAsJsonObject();
      JsonObject json_gui = json_client.get("gui").getAsJsonObject();
      WurstplusCommandManager.set_prefix(json_config.get("prefix").getAsString());
      Iterator var5 = ListedHack.click_gui.get_array_frames().iterator();

      while(var5.hasNext()) {
         WurstplusFrame frames = (WurstplusFrame)var5.next();
         JsonObject frame_info = json_gui.get(frames.get_tag()).getAsJsonObject();
         WurstplusFrame frame_requested = ListedHack.click_gui.get_frame_with_tag(frame_info.get("tag").getAsString());
         frame_requested.set_x(frame_info.get("x").getAsInt());
         frame_requested.set_y(frame_info.get("y").getAsInt());
      }

      stream.close();
   }

   private void save_hud() throws IOException {
      Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
      JsonParser parser = new JsonParser();
      JsonObject main_json = new JsonObject();
      JsonObject main_frame = new JsonObject();
      JsonObject main_hud = new JsonObject();
      main_frame.add("name", new JsonPrimitive(ListedHack.click_hud.get_frame_hud().get_name()));
      main_frame.add("tag", new JsonPrimitive(ListedHack.click_hud.get_frame_hud().get_tag()));
      main_frame.add("x", new JsonPrimitive(ListedHack.click_hud.get_frame_hud().get_x()));
      main_frame.add("y", new JsonPrimitive(ListedHack.click_hud.get_frame_hud().get_y()));
      Iterator var6 = ListedHack.get_hud_manager().get_array_huds().iterator();

      while(var6.hasNext()) {
         WurstplusPinnable pinnables_hud = (WurstplusPinnable)var6.next();
         JsonObject frame_info = new JsonObject();
         frame_info.add("title", new JsonPrimitive(pinnables_hud.get_title()));
         frame_info.add("tag", new JsonPrimitive(pinnables_hud.get_tag()));
         frame_info.add("state", new JsonPrimitive(pinnables_hud.is_active()));
         frame_info.add("dock", new JsonPrimitive(pinnables_hud.get_dock()));
         frame_info.add("x", new JsonPrimitive(pinnables_hud.get_x()));
         frame_info.add("y", new JsonPrimitive(pinnables_hud.get_y()));
         main_hud.add(pinnables_hud.get_tag(), frame_info);
      }

      main_json.add("frame", main_frame);
      main_json.add("hud", main_hud);
      JsonElement pretty_json = parser.parse(main_json.toString());
      String json = gson.toJson(pretty_json);
      this.delete_file("ListedHack/hud.json");
      this.verify_file(this.HUD_PATH);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("ListedHack/hud.json"), StandardCharsets.UTF_8);
      file.write(json);
      file.close();
   }

   private void load_hud() throws IOException {
      InputStream input_stream = Files.newInputStream(this.HUD_PATH);
      JsonObject main_hud = (new JsonParser()).parse(new InputStreamReader(input_stream)).getAsJsonObject();
      JsonObject main_frame = main_hud.get("frame").getAsJsonObject();
      JsonObject main_huds = main_hud.get("hud").getAsJsonObject();
      ListedHack.click_hud.get_frame_hud().set_x(main_frame.get("x").getAsInt());
      ListedHack.click_hud.get_frame_hud().set_y(main_frame.get("y").getAsInt());
      Iterator var5 = ListedHack.get_hud_manager().get_array_huds().iterator();

      while(var5.hasNext()) {
         WurstplusPinnable pinnables = (WurstplusPinnable)var5.next();
         JsonObject hud_info = main_huds.get(pinnables.get_tag()).getAsJsonObject();
         WurstplusPinnable pinnable_requested = ListedHack.get_hud_manager().get_pinnable_with_tag(hud_info.get("tag").getAsString());
         pinnable_requested.set_active(hud_info.get("state").getAsBoolean());
         pinnable_requested.set_dock(hud_info.get("dock").getAsBoolean());
         pinnable_requested.set_x(hud_info.get("x").getAsInt());
         pinnable_requested.set_y(hud_info.get("y").getAsInt());
      }

      input_stream.close();
   }

   private void save_binds() throws IOException {
      String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
      Path file_path = Paths.get(file_name);
      this.delete_file(file_name);
      this.verify_file(file_path);
      File file = new File(file_name);
      BufferedWriter br = new BufferedWriter(new FileWriter(file));
      Iterator var5 = ListedHack.get_hack_manager().get_array_hacks().iterator();

      while(var5.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var5.next();
         br.write(modules.get_tag() + ":" + modules.get_bind(1) + ":" + modules.is_active() + "\r\n");
      }

      br.close();
   }

   private void load_binds() throws IOException {
      String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
      File file = new File(file_name);
      FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
      DataInputStream di_stream = new DataInputStream(fi_stream);
      BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));

      String line;
      while((line = br.readLine()) != null) {
         try {
            String colune = line.trim();
            String tag = colune.split(":")[0];
            String bind = colune.split(":")[1];
            String active = colune.split(":")[2];
            WurstplusHack module = ListedHack.get_hack_manager().get_module_with_tag(tag);
            module.set_bind(Integer.parseInt(bind));
            module.set_active(Boolean.parseBoolean(active));
         } catch (Exception var12) {
         }
      }

      br.close();
   }

   public void save_settings() {
      try {
         this.verify_dir(this.MAIN_FOLDER_PATH);
         this.verify_dir(this.CONFIGS_FOLDER_PATH);
         this.verify_dir(this.ACTIVE_CONFIG_FOLDER_PATH);
         this.save_hacks();
         this.save_binds();
         this.save_friends();
         this.save_enemies();
         this.save_client();
         this.save_drawn();
         this.save_ezmessage();
         this.save_hud();
      } catch (IOException var2) {
         ListedHack.send_minecraft_log("Something has gone wrong while saving settings");
         ListedHack.send_minecraft_log(var2.toString());
      }

   }

   public void load_settings() {
      try {
         this.load_binds();
         this.load_client();
         this.load_drawn();
         this.load_enemies();
         this.load_ezmessage();
         this.load_friends();
         this.load_hacks();
         this.load_hud();
      } catch (IOException var2) {
         ListedHack.send_minecraft_log("Something has gone wrong while loading settings");
         ListedHack.send_minecraft_log(var2.toString());
      }

   }

   public boolean delete_file(String path) throws IOException {
      File f = new File(path);
      return f.delete();
   }

   public void verify_file(Path path) throws IOException {
      if (!Files.exists(path, new LinkOption[0])) {
         Files.createFile(path);
      }

   }

   public void verify_dir(Path path) throws IOException {
      if (!Files.exists(path, new LinkOption[0])) {
         Files.createDirectory(path);
      }

   }
}
