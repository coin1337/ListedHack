package me.listed.listedhack.client.guiscreen.render.components.past.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.math.BigDecimal;
import java.math.RoundingMode;
import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.components.past.Component;
import me.listed.listedhack.client.guiscreen.render.components.past.font.FontUtil;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import net.minecraft.client.gui.Gui;

public class DoubleComponent extends Component {
   private WurstplusSetting set;
   private ModuleButton parent;
   private int offset;
   private int x;
   private int y;
   private boolean dragging = false;
   private double sliderWidth;

   public DoubleComponent(WurstplusSetting value, ModuleButton button, int offset) {
      this.set = value;
      this.parent = button;
      this.x = button.parent.getX() + button.parent.getWidth();
      this.y = button.parent.getY() + button.offset;
      this.offset = offset;
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
      WurstplusDraw.draw_rect(this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.sliderWidth - 1, this.parent.parent.getY() + this.offset + 15, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      Gui.func_73734_a(this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 1, this.parent.parent.getY() + this.offset + 15, -14606047);
      FontUtil.drawText(this.set.get_name() + ChatFormatting.GRAY + " " + this.set.get_value(1.0D), (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 3), -1);
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
      double diff = (double)Math.min(100, Math.max(0, mouseX - this.x));
      double min = this.set.get_min(1.0D);
      double max = this.set.get_max(1.0D);
      this.sliderWidth = 100.0D * (this.set.get_value(1.0D) - min) / (max - min);
      if (this.dragging) {
         if (diff == 0.0D) {
            this.set.set_value(this.set.get_min(1.0D));
         } else {
            double newValue = roundToPlace(diff / 100.0D * (max - min) + min, 2);
            this.set.set_value(newValue);
         }
      }

   }

   private static double roundToPlace(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = new BigDecimal(value);
         bd = bd.setScale(places, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
         this.dragging = true;
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
      this.dragging = false;
   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
   }
}
