package me.listed.listedhack.client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

public class WurstplusCapeUtil {
   static final ArrayList<String> final_uuid_list = get_uuids();

   public static ArrayList<String> get_uuids() {
      try {
         URL url = new URL("https://pastebin.com/raw/FcwfyAjk");
         BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
         ArrayList uuid_list = new ArrayList();

         String s;
         while((s = reader.readLine()) != null) {
            uuid_list.add(s);
         }

         return uuid_list;
      } catch (Exception var4) {
         return null;
      }
   }

   public static boolean is_uuid_valid(UUID uuid) {
      Iterator var1 = ((ArrayList)Objects.requireNonNull(final_uuid_list)).iterator();

      String u;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         u = (String)var1.next();
      } while(!u.equals(uuid.toString()));

      return true;
   }
}
