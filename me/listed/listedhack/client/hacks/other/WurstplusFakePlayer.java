package me.listed.listedhack.client.hacks.other;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class WurstplusFakePlayer extends WurstplusHack {
   WurstplusSetting Inv = this.create("Copy Inventory", "CopyInv", true);
   private EntityOtherPlayerMP fake_player;

   public WurstplusFakePlayer() {
      super(WurstplusCategory.WURSTPLUS_OTHER);
      this.name = "Spawn Listed";
      this.tag = "Spawn Listed";
      this.description = "spawns in listed";
   }

   protected void enable() {
      this.fake_player = new EntityOtherPlayerMP(mc.field_71441_e, new GameProfile(UUID.fromString("ef845538-72e9-49e5-9675-1d2995036cc3"), "Listed"));
      this.fake_player.func_82149_j(mc.field_71439_g);
      this.fake_player.field_70759_as = mc.field_71439_g.field_70759_as;
      mc.field_71441_e.func_73027_a(-100, this.fake_player);
      if (this.Inv.get_value(true)) {
         this.fake_player.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by);
      }

   }

   protected void disable() {
      try {
         mc.field_71441_e.func_72900_e(this.fake_player);
      } catch (Exception var2) {
      }

   }
}
