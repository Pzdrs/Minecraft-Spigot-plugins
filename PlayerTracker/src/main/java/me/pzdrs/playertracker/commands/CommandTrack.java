package me.pzdrs.playertracker.commands;

import me.pzdrs.playertracker.PlayerTracker;
import me.pzdrs.playertracker.utilities.ItemBuilder;
import me.pzdrs.playertracker.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTrack implements TabExecutor {
    private PlayerTracker plugin;

    public CommandTrack(PlayerTracker plugin) {
        this.plugin = plugin;

        plugin.getCommand("track").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().severe("Only players may use this command.");
            return true;
        }
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(Utils.color("&cThis player is not online."));
            return true;
        }
        if (target.equals(player)) {
            player.sendMessage(Utils.color("&cYou can't track yourself."));
            return true;
        }
        plugin.getPlayers().put(player, target);
        player.sendMessage(Utils.color("&aYou are tracking &9" + target.getName() + " &afrom now on."));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return null;
        }
        return new ArrayList<>();
    }
}
