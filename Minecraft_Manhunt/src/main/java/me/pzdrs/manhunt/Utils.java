package me.pzdrs.manhunt;

import me.pzdrs.manhunt.commands.commandManhunt.CommandManhunt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void help(Player player, CommandManhunt command) {
        player.sendMessage(color("&7&m                                                                                "));
        command.getSubcommands().forEach(subcommand -> {
            player.sendMessage(color("&9" + subcommand.getUsage() + " &8-&r " + subcommand.getDescription()));
        });
        player.sendMessage(color("&7&m                                                                                "));
    }
}
