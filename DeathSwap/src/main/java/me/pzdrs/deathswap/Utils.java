package me.pzdrs.deathswap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Utils {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void help(Player player, List<SubCommand> subCommands) {
        player.sendMessage(Utils.color("&7&m                                                  "));
        subCommands.forEach(subCommand -> {
            player.sendMessage(color("&9" + subCommand.getUsage() + " &8-&r " + subCommand.getDescription()));
        });
        player.sendMessage(Utils.color("&7&m                                                  "));
    }

    public static String getUsage(String command, String subCommand) {
        return "/" + command + " " + subCommand;
    }
}
