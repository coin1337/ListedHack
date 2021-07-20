package me.listed.listedhack.client.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.util.WurstplusMessageUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.init.SoundEvents;

public class WurstplusEntityAlert extends WurstplusHack {
   WurstplusSetting donkey = this.create("Donkey", "Donkey", true);
   WurstplusSetting mule = this.create("Mule", "Mule", true);
   WurstplusSetting llama = this.create("Llama", "Llama", true);
   WurstplusSetting sound = this.create("Sound", "Sound", true);

   public WurstplusEntityAlert() {
      super(WurstplusCategory.WURSTPLUS_CHAT);
      this.name = "Entity Alert";
      this.tag = "EntityAlert";
      this.description = "Notifys entities";
   }

   public void update() {
      if (!this.nullCheck()) {
         Iterator var1 = mc.field_71441_e.func_72910_y().iterator();

         while(true) {
            while(var1.hasNext()) {
               Entity entity = (Entity)var1.next();
               if (entity instanceof EntityDonkey && this.donkey.get_value(true)) {
                  WurstplusMessageUtil.send_client_message(ChatFormatting.WHITE + "There is a " + ChatFormatting.BLUE + "donkey " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.field_70142_S) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70137_T) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70136_U) + ChatFormatting.GRAY + "]");
                  if (this.sound.get_value(true)) {
                     mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_187680_c, 1.0F, 1.0F));
                     this.toggle();
                  }
               }

               if (entity instanceof EntityMule && this.mule.get_value(true)) {
                  WurstplusMessageUtil.send_client_message(ChatFormatting.WHITE + "There is a " + ChatFormatting.BLUE + "llama " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.field_70142_S) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70137_T) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70136_U) + ChatFormatting.GRAY + "]");
                  if (this.sound.get_value(true)) {
                     mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_187680_c, 1.0F, 1.0F));
                     this.toggle();
                  }
               } else if (entity instanceof EntityLlama && this.llama.get_value(true)) {
                  WurstplusMessageUtil.send_client_message(ChatFormatting.WHITE + "There is a " + ChatFormatting.BLUE + "mule " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.field_70142_S) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70137_T) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70136_U) + ChatFormatting.GRAY + "]");
                  if (this.sound.get_value(true)) {
                     mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_187680_c, 1.0F, 1.0F));
                     this.toggle();
                  }
               }
            }

            return;
         }
      }
   }
}
