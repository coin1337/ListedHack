package me.listed.listedhack.client.guiscreen.render.components.past.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.components.past.Component;
import me.listed.listedhack.client.guiscreen.render.components.past.font.FontUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeybindComponent extends Component {
   private boolean isBinding;
   private ModuleButton parent;
   private int offset;
   private int x;
   private int y;
   private String points;
   private float tick;

   public KeybindComponent(ModuleButton parent, int offset) {
      this.parent = parent;
      this.x = parent.parent.getX() + parent.parent.getWidth();
      this.y = parent.parent.getY() + parent.offset;
      this.offset = offset;
      this.points = ".";
      this.tick = 0.0F;
   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void renderComponent() {
      WurstplusDraw.draw_rect(this.parent.parent.getX() - 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX(), this.parent.parent.getY() + 15 + this.offset, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() + 1, this.parent.parent.getY() + 15 + this.offset, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      WurstplusDraw.draw_rect(this.parent.parent.getX() - 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() + 1, this.parent.parent.getY() + this.offset + 16, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      Gui.func_73734_a(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, -13684945);
      WurstplusDraw.draw_rect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset + 15, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 1, this.parent.parent.getY() + this.offset + 15, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      if (this.isBinding) {
         this.tick += 0.5F;
         FontUtil.drawText("Press a Key" + ChatFormatting.GRAY + " " + this.points, (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), -1);
      } else {
         FontUtil.drawText("Bind" + ChatFormatting.GRAY + " " + this.parent.mod.get_bind("string"), (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), -1);
      }

      if (this.isBinding) {
         if (this.tick >= 15.0F) {
            this.points = "..";
         }

         if (this.tick >= 30.0F) {
            this.points = "...";
         }

         if (this.tick >= 45.0F) {
            this.points = ".";
            this.tick = 0.0F;
         }
      }

   }

   public void updateComponent(int mouseX, int mouseY) {
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
         this.isBinding = !this.isBinding;
      }

   }

   public void keyTyped(char typedChar, int key) {
      if (this.isBinding) {
         if (Keyboard.isKeyDown(211)) {
            this.parent.mod.set_bind(0);
            this.isBinding = false;
         } else if (Keyboard.isKeyDown(14)) {
            this.parent.mod.set_bind(0);
            this.isBinding = false;
         } else {
            this.parent.mod.set_bind(key);
            this.isBinding = false;
         }
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
   }
}
