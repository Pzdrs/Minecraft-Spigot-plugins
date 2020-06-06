package me.pzdrs.blockshuffle.commands;

import me.pzdrs.blockshuffle.BlockShuffle;
import me.pzdrs.blockshuffle.PlayerManager;
import me.pzdrs.blockshuffle.utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBlockShuffle implements TabExecutor {
    private BlockShuffle plugin;

    public CommandBlockShuffle(BlockShuffle plugin) {
        this.plugin = plugin;
        plugin.timer = plugin.getConfig().getInt("period");

        plugin.getCommand("blockShuffle").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "start":
                    if (sender instanceof Player) {
                        if (plugin.gameInProgress) {
                            sender.sendMessage(Utils.color("&cThe game is already in progress."));
                            return true;
                        }
                        sender.sendMessage(Utils.color("&aBlock shuffle started."));
                    } else {
                        if (plugin.gameInProgress) {
                            plugin.getLogger().severe("The game is already in progress.");
                            return true;
                        }
                        plugin.getLogger().info("Block shuffle started.");
                    }
                    plugin.getPlayers().clear();
                    Utils.loadPlayers();

                    plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                        if (plugin.timer == 0) {
                            plugin.getPlayers().forEach(player -> {
                                if (!player.isFinished()) {
                                    plugin.getServer().getOnlinePlayers().forEach(online -> online.sendMessage(Utils.color("&4&l" + player.getPlayer().getDisplayName() + " failed to find their block!")));
                                }
                            });

                            plugin.getPlayers().clear();
                            Utils.loadPlayers();
                            plugin.timer = plugin.getConfig().getInt("period");
                        } else if (plugin.timer <= 10)
                            plugin.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(Utils.color("&c&lYou have " + plugin.timer + " seconds to stand on your block.")));
                        plugin.timer--;
                    }, 0, 20);
                    plugin.gameInProgress = true;
                    break;
                case "stop":
                    if (sender instanceof Player) {
                        if (!plugin.gameInProgress) {
                            sender.sendMessage(Utils.color("&cThe game is not in progress."));
                            return true;
                        }
                        sender.sendMessage(Utils.color("&cBlock shuffle stopped."));
                    } else {
                        if (!plugin.gameInProgress) {
                            plugin.getLogger().severe("The game is not in progress.");
                            return true;
                        }
                        plugin.getLogger().info("Block shuffle stopped.");
                    }
                    plugin.gameInProgress = false;
                    plugin.getServer().getScheduler().cancelTasks(plugin);
                    break;
                default:
                    if (sender instanceof Player) {
                        sender.sendMessage(Utils.color("&cUnknown subcommand."));
                    } else {
                        plugin.getLogger().severe("Unknown command.");
                    }
                    break;
            }
        } else {
            if (sender instanceof Player) {
                sender.sendMessage(Utils.color("Usage: " + command.getUsage()));
            } else {
                plugin.getLogger().severe("Usage: " + command.getUsage());
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.addAll(Arrays.asList("start", "stop"));
            return subCommands;
        }
        return new ArrayList<>();
    }
}
