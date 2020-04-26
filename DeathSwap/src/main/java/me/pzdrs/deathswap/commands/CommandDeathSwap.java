package me.pzdrs.deathswap.commands;

import me.pzdrs.deathswap.DeathSwap;
import me.pzdrs.deathswap.SubCommand;
import me.pzdrs.deathswap.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandDeathSwap implements TabExecutor {
    private DeathSwap plugin;
    private List<SubCommand> subCommands;
    private String mainCommand = "deathSwap";

    public CommandDeathSwap(DeathSwap plugin) {
        this.plugin = plugin;
        plugin.getCommand(mainCommand).setExecutor(this);
        this.subCommands = new ArrayList<>();

        subCommands.add(new SubCommandStart(plugin, this));
        subCommands.add(new SubCommandStop(plugin, this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().severe("Only players may use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length > 0) {
            subCommands.forEach(subCommand -> {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.handle(player, args);
                }
            });
        } else {
            Utils.help(player, subCommands);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subCommandsTabs = new ArrayList<>();
            subCommands.forEach(subCommand -> subCommandsTabs.add(subCommand.getName()));
            return subCommandsTabs;
        }
        return null;
    }

    public String getMainCommand() {
        return mainCommand;
    }
}
