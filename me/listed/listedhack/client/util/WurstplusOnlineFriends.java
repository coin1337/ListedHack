package me.listed.listedhack.client.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class WurstplusOnlineFriends {
   public static List<Entity> entities = new ArrayList();

   public static List<Entity> getFriends() {
      entities.clear();
      entities.addAll((Collection)Minecraft.func_71410_x().field_71441_e.field_73010_i.stream().filter((entityPlayer) -> {
         return WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_());
      }).collect(Collectors.toList()));
      return entities;
   }
}
