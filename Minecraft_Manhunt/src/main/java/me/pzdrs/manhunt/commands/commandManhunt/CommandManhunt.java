package me.pzdrs.manhunt.commands.commandManhunt;

import me.pzdrs.manhunt.Manhunt;
import me.pzdrs.manhunt.Utils;
import me.pzdrs.manhunt.commands.Subcommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CommandManhunt implements TabExecutor {
    private Manhunt plugin;
    private List<Subcommand> subcommands;

    public CommandManhunt(Manhunt plugin) {
        this.plugin = plugin;
        this.subcommands = new ArrayList<>();

        plugin.getCommand("manhunt").setExecutor(this);

        subcommands.add(new SubcommandAdd(plugin));
        subcommands.add(new SubcommandRemove(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().severe("Only players may use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length > 0) {
            subcommands.forEach(subCommand -> {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.action(player, args);
                }
            });
        } else {
            Utils.help(player, this);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subCommands = new ArrayList<>(), groups = new ArrayList<>();
        subcommands.forEach(subcommand -> subCommands.add(subcommand.getName()));
        groups.addAll(Arrays.asList("hunter", "speedrunner"));
        if (args.length == 1) {
            return subCommands;
        } else if (args.length == 2) {
            return groups;
        }
        return null;
    }

    public List<Subcommand> getSubcommands() {
        return subcommands;
    }
}
