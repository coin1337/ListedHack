package me.listed.listedhack.client.guiscreen.render.components.past;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.components.past.font.FontUtil;
import me.listed.listedhack.client.guiscreen.render.components.past.items.ModuleButton;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class Panel {
   protected Minecraft mc = Minecraft.func_71410_x();
   public ArrayList<Component> components = new ArrayList();
   public String title;
   public int x;
   public int y;
   public int width;
   public int height;
   public boolean isSettingOpen;
   private boolean isDragging;
   private boolean open;
   public int dragX;
   public int dragY;
   public WurstplusCategory cat;
   public int tY;

   public Panel(String title, int x, int y, int width, int height, WurstplusCategory cat) {
      this.title = title;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.dragX = 0;
      this.isSettingOpen = true;
      this.isDragging = false;
      this.open = true;
      this.cat = cat;
      this.tY = this.height;
      Iterator var7 = ListedHack.get_hack_manager().get_modules_with_category(cat).iterator();

      while(var7.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var7.next();
         if (modules.get_category() == cat) {
            ModuleButton modButton = new ModuleButton(modules, this, this.tY);
            this.components.add(modButton);
            this.tY += 15;
         }
      }

      this.refresh();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Gui.func_73734_a(this.x, this.y, this.x + this.width, this.y + this.height, -14606047);
      WurstplusDraw.draw_rect(this.x - 2, this.y - 0, this.x + this.width + 2, this.y + this.height + 0, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
         FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
         FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
      } else if (ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
         FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
      } else {
         FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
      }

      if (this.open && !this.components.isEmpty()) {
         Iterator var4 = this.components.iterator();

         while(var4.hasNext()) {
            Component component = (Component)var4.next();
            component.renderComponent();
         }
      }

   }

   public void refresh() {
      int off = this.height;

      Component comp;
      for(Iterator var2 = this.components.iterator(); var2.hasNext(); off += comp.getHeight()) {
         comp = (Component)var2.next();
         comp.setOff(off);
      }

   }

   public boolean isWithinHeader(int x, int y) {
      return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
   }

   public void updatePosition(int mouseX, int mouseY) {
      if (this.isDragging) {
         this.setX(mouseX - this.dragX);
         this.setY(mouseY - this.dragY);
      }

      this.scroll();
   }

   public void scroll() {
      int scrollWheel = Mouse.getDWheel();
      PastGUI var10000 = ListedHack.past_gui;
      Iterator var2 = PastGUI.panels.iterator();

      while(var2.hasNext()) {
         Panel panels = (Panel)var2.next();
         if (scrollWheel < 0) {
            panels.setY(panels.getY() - ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIScrollSpeed").get_value(1));
         } else if (scrollWheel > 0) {
            panels.setY(panels.getY() + ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIScrollSpeed").get_value(1));
         }
      }

   }

   public void closeAllSetting() {
      Iterator var1 = this.components.iterator();

      while(var1.hasNext()) {
         Component component = (Component)var1.next();
         component.closeAllSub();
      }

   }

   public ArrayList<Component> getComponents() {
      return this.components;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setDragging(boolean drag) {
      this.isDragging = drag;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public void setX(int newX) {
      this.x = newX;
   }

   public void setY(int newY) {
      this.y = newY;
   }

   public WurstplusCategory getCategory() {
      return this.cat;
   }
}
