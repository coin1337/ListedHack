package me.listed.listedhack.client.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import me.listed.listedhack.client.command.commands.WurstplusAlert;
import me.listed.listedhack.client.command.commands.WurstplusBind;
import me.listed.listedhack.client.command.commands.WurstplusConfig;
import me.listed.listedhack.client.command.commands.WurstplusDrawn;
import me.listed.listedhack.client.command.commands.WurstplusEnemy;
import me.listed.listedhack.client.command.commands.WurstplusEzMessage;
import me.listed.listedhack.client.command.commands.WurstplusFriend;
import me.listed.listedhack.client.command.commands.WurstplusHelp;
import me.listed.listedhack.client.command.commands.WurstplusPrefix;
import me.listed.listedhack.client.command.commands.WurstplusSettings;
import me.listed.listedhack.client.command.commands.WurstplusToggle;
import me.listed.turok.values.TurokString;
import net.minecraft.util.text.Style;

public class WurstplusCommands {
   public static ArrayList<WurstplusCommand> command_list = new ArrayList();
   static HashMap<String, WurstplusCommand> list_command = new HashMap();
   public static final TurokString prefix = new TurokString("Prefix", "Prefix", ".");
   public final Style style;

   public WurstplusCommands(Style style_) {
      this.style = style_;
      add_command(new WurstplusBind());
      add_command(new WurstplusPrefix());
      add_command(new WurstplusSettings());
      add_command(new WurstplusToggle());
      add_command(new WurstplusAlert());
      add_command(new WurstplusHelp());
      add_command(new WurstplusFriend());
      add_command(new WurstplusDrawn());
      add_command(new WurstplusEzMessage());
      add_command(new WurstplusEnemy());
      add_command(new WurstplusConfig());
      command_list.sort(Comparator.comparing(WurstplusCommand::get_name));
   }

   public static void add_command(WurstplusCommand command) {
      command_list.add(command);
      list_command.put(command.get_name().toLowerCase(), command);
   }

   public String[] get_message(String message) {
      String[] arguments = new String[0];
      if (this.has_prefix(message)) {
         arguments = message.replaceFirst(prefix.get_value(), "").split(" ");
      }

      return arguments;
   }

   public boolean has_prefix(String message) {
      return message.startsWith(prefix.get_value());
   }

   public void set_prefix(String new_prefix) {
      prefix.set_value(new_prefix);
   }

   public String get_prefix() {
      return prefix.get_value();
   }

   public static ArrayList<WurstplusCommand> get_pure_command_list() {
      return command_list;
   }

   public static WurstplusCommand get_command_with_name(String name) {
      return (WurstplusCommand)list_command.get(name.toLowerCase());
   }
}
