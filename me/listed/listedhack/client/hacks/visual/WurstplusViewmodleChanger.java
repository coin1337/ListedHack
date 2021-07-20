package me.listed.listedhack.client.hacks.visual;

import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WurstplusViewmodleChanger extends WurstplusHack {
   WurstplusSetting custom_fov = this.create("FOV", "FOVSlider", 130, 110, 170);
   WurstplusSetting items = this.create("Items", "FOVItems", false);
   WurstplusSetting viewmodle_fov = this.create("Items FOV", "ItemsFOVSlider", 130, 110, 170);
   WurstplusSetting normal_offset = this.create("Offset", "FOVOffset", true);
   WurstplusSetting offset = this.create("Offset Main", "FOVOffsetMain", 0.7D, 0.0D, 1.0D);
   WurstplusSetting offset_x = this.create("Offset X", "FOVOffsetX", 0.0D, -1.0D, 1.0D);
   WurstplusSetting offset_y = this.create("Offset Y", "FOVOffsetY", 0.0D, -1.0D, 1.0D);
   WurstplusSetting main_x = this.create("Main X", "FOVMainX", 0.0D, -1.0D, 1.0D);
   WurstplusSetting main_y = this.create("Main Y", "FOVMainY", 0.0D, -1.0D, 1.0D);
   private float fov;

   public WurstplusViewmodleChanger() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Custom Viewmodel";
      this.tag = "CustomViewmodel";
      this.description = "anti chad";
   }

   protected void enable() {
      this.fov = mc.field_71474_y.field_74334_X;
      MinecraftForge.EVENT_BUS.register(this);
   }

   protected void disable() {
      mc.field_71474_y.field_74334_X = this.fov;
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   public void update() {
      mc.field_71474_y.field_74334_X = (float)this.custom_fov.get_value(1);
   }

   @SubscribeEvent
   public void fov_event(FOVModifier m) {
      if (this.items.get_value(true)) {
         m.setFOV((float)this.viewmodle_fov.get_value(1));
      }

   }
}
