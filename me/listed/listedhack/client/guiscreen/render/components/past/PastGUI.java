package me.listed.listedhack.client.guiscreen.render.components.past;

import java.util.ArrayList;
import java.util.Iterator;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import net.minecraft.client.gui.GuiScreen;

public class PastGUI extends GuiScreen {
   public static ArrayList<Panel> panels;

   public PastGUI() {
      panels = new ArrayList();
      int panelX = 5;
      int panelY = 5;
      int panelWidth = 100;
      int panelHeight = 15;
      WurstplusCategory[] var5 = WurstplusCategory.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WurstplusCategory c = var5[var7];
         if (!c.is_hidden()) {
            panels.add(new Panel(c.get_name(), panelX, panelY, panelWidth, panelHeight, c));
            panelX += 105;
         }
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_146276_q_();
      Iterator var4 = panels.iterator();

      while(var4.hasNext()) {
         Panel p = (Panel)var4.next();
         p.updatePosition(mouseX, mouseY);
         p.drawScreen(mouseX, mouseY, partialTicks);
         Iterator var6 = p.getComponents().iterator();

         while(var6.hasNext()) {
            Component comp = (Component)var6.next();
            comp.updateComponent(mouseX, mouseY);
         }
      }

   }

   public void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      Iterator var4 = panels.iterator();

      while(true) {
         while(var4.hasNext()) {
            Panel p = (Panel)var4.next();
            if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
               p.setDragging(true);
               p.dragX = mouseX - p.getX();
               p.dragY = mouseY - p.getY();
            } else if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
               p.setOpen(!p.isOpen());
            } else if (p.isOpen() && !p.getComponents().isEmpty()) {
               Iterator var6 = p.getComponents().iterator();

               while(var6.hasNext()) {
                  Component component = (Component)var6.next();
                  component.mouseClicked(mouseX, mouseY, mouseButton);
               }
            }
         }

         return;
      }
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      Iterator var3 = panels.iterator();

      while(true) {
         Panel panel;
         do {
            do {
               do {
                  if (!var3.hasNext()) {
                     if (keyCode == 1) {
                        this.field_146297_k.func_147108_a((GuiScreen)null);
                     }

                     return;
                  }

                  panel = (Panel)var3.next();
               } while(!panel.isOpen());
            } while(panel.getComponents().isEmpty());
         } while(keyCode == 1);

         Iterator var5 = panel.getComponents().iterator();

         while(var5.hasNext()) {
            Component component = (Component)var5.next();
            component.keyTyped(typedChar, keyCode);
         }
      }
   }

   public void func_146281_b() {
      ListedHack.get_hack_manager().get_module_with_tag("ClickGUI").set_active(false);
   }

   public void func_146286_b(int mouseX, int mouseY, int state) {
      Iterator var4 = panels.iterator();

      while(true) {
         Panel p;
         do {
            do {
               if (!var4.hasNext()) {
                  return;
               }

               p = (Panel)var4.next();
               p.setDragging(false);
            } while(!p.isOpen());
         } while(p.getComponents().isEmpty());

         Iterator var6 = p.getComponents().iterator();

         while(var6.hasNext()) {
            Component component = (Component)var6.next();
            component.mouseReleased(mouseX, mouseY, state);
         }
      }
   }

   public static ArrayList<Panel> getPanels() {
      return panels;
   }

   public static Panel getPanelByName(String name) {
      Panel panel = null;
      Iterator var2 = getPanels().iterator();

      while(var2.hasNext()) {
         Panel p = (Panel)var2.next();
         if (p.title.equalsIgnoreCase(name)) {
            panel = p;
         }
      }

      return panel;
   }

   public boolean func_73868_f() {
      return ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIPauseGame").get_value(true);
   }
}
