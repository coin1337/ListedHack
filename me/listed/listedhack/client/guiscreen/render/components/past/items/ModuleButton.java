package me.listed.listedhack.client.guiscreen.render.components.past.items;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.components.past.Component;
import me.listed.listedhack.client.guiscreen.render.components.past.Panel;
import me.listed.listedhack.client.guiscreen.render.components.past.font.FontUtil;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;

public class ModuleButton extends Component {
   private ArrayList<Component> subcomponents;
   public WurstplusHack mod;
   public Panel parent;
   public int offset;
   private boolean open;
   private boolean hovered;

   public ModuleButton(WurstplusHack mod, Panel parent, int offset) {
      this.mod = mod;
      this.parent = parent;
      this.offset = offset;
      this.subcomponents = new ArrayList();
      this.open = false;
      int opY = offset + 15;
      Iterator var5 = ListedHack.get_setting_manager().get_settings_with_hack(mod).iterator();

      while(var5.hasNext()) {
         WurstplusSetting settings = (WurstplusSetting)var5.next();
         if (settings.get_type().equals("button")) {
            this.subcomponents.add(new BooleanComponent(settings, this, opY));
            opY += 15;
         } else if (settings.get_type().equals("integerslider")) {
            this.subcomponents.add(new IntegerComponent(settings, this, opY));
            opY += 15;
         } else if (settings.get_type().equals("doubleslider")) {
            this.subcomponents.add(new DoubleComponent(settings, this, opY));
            opY += 15;
         } else if (settings.get_type().equals("combobox")) {
            this.subcomponents.add(new ModeComponent(settings, this, opY));
            opY += 15;
         }
      }

      this.subcomponents.add(new KeybindComponent(this, opY));
   }

   public void setOff(int newOff) {
      this.offset = newOff;
      int opY = this.offset + 15;

      for(Iterator var3 = this.subcomponents.iterator(); var3.hasNext(); opY += 15) {
         Component comp = (Component)var3.next();
         comp.setOff(opY);
      }

   }

   public void renderComponent() {
      if (this.mod.is_active()) {
         Gui.func_73734_a(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 15 + this.offset, -13684945);
      } else {
         Gui.func_73734_a(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 15 + this.offset, -14606047);
      }

      if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIHoverChange").get_value(true) && this.hovered) {
         Gui.func_73734_a(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 15 + this.offset, -13684945);
         FontUtil.drawText(this.mod.get_name(), (float)(this.parent.getX() + 4), (float)(this.parent.getY() + this.offset + 4), -1);
      } else {
         FontUtil.drawText(this.mod.get_name(), (float)(this.parent.getX() + 4), (float)(this.parent.getY() + this.offset + 4), -1);
      }

      if (!this.isOpen()) {
         FontUtil.drawText("...", (float)(this.parent.getX() + this.parent.getWidth() - 10), (float)(this.parent.getY() + this.offset + 2), -1);
      } else if (this.isOpen()) {
         FontUtil.drawText(" ", (float)(this.parent.getX() + this.parent.getWidth() - 10), (float)(this.parent.getY() + this.offset + 2), -1);
      }

      if (this.open && !this.subcomponents.isEmpty()) {
         Iterator var1 = this.subcomponents.iterator();

         while(var1.hasNext()) {
            Component comp = (Component)var1.next();
            comp.renderComponent();
         }
      }

   }

   public void closeAllSub() {
      this.open = false;
   }

   public int getHeight() {
      return this.open ? 15 * (this.subcomponents.size() + 1) : 15;
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButton(mouseX, mouseY);
      if (!this.subcomponents.isEmpty()) {
         Iterator var3 = this.subcomponents.iterator();

         while(var3.hasNext()) {
            Component comp = (Component)var3.next();
            comp.updateComponent(mouseX, mouseY);
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
         this.mod.toggle();
         if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUISound").get_value(true)) {
            Minecraft.func_71410_x().func_147118_V().func_147682_a(PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
         }
      }

      if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
         if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUISound").get_value(true)) {
            Minecraft.func_71410_x().func_147118_V().func_147682_a(PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
         }

         if (!this.isOpen()) {
            this.parent.closeAllSetting();
            this.setOpen(true);
         } else {
            this.setOpen(false);
         }

         this.parent.refresh();
      }

      Iterator var4 = this.subcomponents.iterator();

      while(var4.hasNext()) {
         Component comp = (Component)var4.next();
         comp.mouseClicked(mouseX, mouseY, button);
      }

   }

   public void keyTyped(char typedChar, int key) {
      Iterator var3 = this.subcomponents.iterator();

      while(var3.hasNext()) {
         Component comp = (Component)var3.next();
         comp.keyTyped(typedChar, key);
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
      Iterator var4 = this.subcomponents.iterator();

      while(var4.hasNext()) {
         Component comp = (Component)var4.next();
         comp.mouseReleased(mouseX, mouseY, mouseButton);
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.parent.getX() && x < this.parent.getX() + 100 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 15 + this.offset;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }
}
