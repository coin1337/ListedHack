package me.listed.listedhack.client.guiscreen.render.components.past.items;

import me.listed.listedhack.ListedHack;
import me.listed.listedhack.client.guiscreen.render.WurstplusDraw;
import me.listed.listedhack.client.guiscreen.render.components.past.Component;
import me.listed.listedhack.client.guiscreen.render.components.past.font.FontUtil;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import net.minecraft.client.gui.Gui;

public class BooleanComponent extends Component {
   private WurstplusSetting op;
   private ModuleButton parent;
   private int offset;
   private int x;
   private int y;

   public BooleanComponent(WurstplusSetting op, ModuleButton parent, int offset) {
      this.op = op;
      this.parent = parent;
      this.x = parent.parent.getX() + parent.parent.getWidth();
      this.y = parent.parent.getY() + parent.offset;
      this.offset = offset;
   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void renderComponent() {
      WurstplusDraw.draw_rect(this.parent.parent.getX() - 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX(), this.parent.parent.getY() + 15 + this.offset, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() + 1, this.parent.parent.getY() + 15 + this.offset, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      WurstplusDraw.draw_rect(this.parent.parent.getX() - 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() + 1, this.parent.parent.getY() + this.offset + 16, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      if (this.op.get_value(true)) {
         WurstplusDraw.draw_rect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
         Gui.func_73734_a(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, -13684945);
      } else {
         Gui.func_73734_a(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, -14606047);
      }

      WurstplusDraw.draw_rect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset + 15, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 1, this.parent.parent.getY() + this.offset + 15, ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), ListedHack.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
      FontUtil.drawText(this.op.get_name(), (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), -1);
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
         this.op.set_value(!this.op.get_value(true));
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
   }
}
