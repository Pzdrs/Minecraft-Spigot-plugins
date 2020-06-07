package me.pzdrs.playertracker.commands;

import me.pzdrs.playertracker.PlayerTracker;
import me.pzdrs.playertracker.utilities.ItemBuilder;
import me.pzdrs.playertracker.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandGetTracker implements TabExecutor {
    private PlayerTracker plugin;

    public CommandGetTracker(PlayerTracker plugin) {
        this.plugin = plugin;

        plugin.getCommand("getTracker").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().severe("Only players may use this command.");
            return true;
        }
        Player player = (Player) sender;

        player.getInventory().addItem(
                new ItemBuilder(Material.COMPASS)
                        .setDisplayName("&3Player Tracker")
                        .addLoreLine("&7Target: &cNone")
                        .addLoreLine("&7Right-click to track a player")
                        .addLoreLine("")
                        .addLoreLine("&7Use &a/track &7command to choose who to track")
                        .build()
        );
        player.sendMessage(Utils.color("&aYou received a player tracker."));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
