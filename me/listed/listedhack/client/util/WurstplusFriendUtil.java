package me.listed.listedhack.client.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class WurstplusFriendUtil {
   public static ArrayList<WurstplusFriendUtil.Friend> friends = new ArrayList();

   public static boolean isFriend(String name) {
      return friends.stream().anyMatch((friend) -> {
         return friend.username.equalsIgnoreCase(name);
      });
   }

   public static WurstplusFriendUtil.Friend get_friend_object(String name) {
      ArrayList<NetworkPlayerInfo> infoMap = new ArrayList(Minecraft.func_71410_x().func_147114_u().func_175106_d());
      NetworkPlayerInfo profile = (NetworkPlayerInfo)infoMap.stream().filter((networkPlayerInfo) -> {
         return networkPlayerInfo.func_178845_a().getName().equalsIgnoreCase(name);
      }).findFirst().orElse((Object)null);
      if (profile != null) {
         return new WurstplusFriendUtil.Friend(profile.func_178845_a().getName(), profile.func_178845_a().getId());
      } else {
         String s = request_ids("[\"" + name + "\"]");
         if (s != null && !s.isEmpty()) {
            JsonElement element = (new JsonParser()).parse(s);
            if (element.getAsJsonArray().size() != 0) {
               try {
                  String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                  String username = element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                  return new WurstplusFriendUtil.Friend(username, UUIDTypeAdapter.fromString(id));
               } catch (Exception var7) {
                  var7.printStackTrace();
               }
            }
         }

         return null;
      }
   }

   private static String request_ids(String data) {
      try {
         String query = "https://api.mojang.com/profiles/minecraft";
         URL url = new URL(query);
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setConnectTimeout(5000);
         conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         conn.setDoOutput(true);
         conn.setDoInput(true);
         conn.setRequestMethod("POST");
         OutputStream os = conn.getOutputStream();
         os.write(data.getBytes("UTF-8"));
         os.close();
         InputStream in = new BufferedInputStream(conn.getInputStream());
         String res = convertStreamToString(in);
         in.close();
         conn.disconnect();
         return res;
      } catch (Exception var7) {
         return null;
      }
   }

   private static String convertStreamToString(InputStream is) {
      Scanner s = (new Scanner(is)).useDelimiter("\\A");
      String r = s.hasNext() ? s.next() : "/";
      return r;
   }

   public static class Friend {
      String username;
      UUID uuid;

      public Friend(String username, UUID uuid) {
         this.username = username;
         this.uuid = uuid;
      }

      public String getUsername() {
         return this.username;
      }

      public UUID getUUID() {
         return this.uuid;
      }
   }
}
