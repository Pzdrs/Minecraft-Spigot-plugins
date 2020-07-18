package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.guis.GuiBingo;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBingo implements TabExecutor {
    private Bingo plugin;
    private GameManager gameManager;
    private List<SubCommand> subCommands;

    public CommandBingo(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        this.subCommands = new ArrayList<>();

        subCommands.add(new SubCommandCheatMode(plugin));
        subCommands.add(new SubCommandStart(plugin));
        subCommands.add(new SubCommandEnd(plugin));
        subCommands.add(new SubCommandSee(plugin));
        subCommands.add(new SubCommandVersion(plugin));
        subCommands.add(new SubCommandHelp(subCommands));

        plugin.getCommand("bingo").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().severe(plugin.getLang().getString("console.notAvailable"));
            return true;
        }
        Player player = (Player) sender;

        if (args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(args[0]) || containsIgnoreCase(subCommand.getAliases(), args[0])) {
                    if (!player.hasPermission(subCommand.getPermission()) && !player.hasPermission("bingo.*")) {
                        player.sendMessage(Message.noPerms(subCommand.getPermission()));
                        return true;
                    }
                    subCommand.handle(player, args);
                    return true;
                }
            }
            player.sendMessage(Message.error("chat.invalidArgs"));
            return true;
        } else {
            if (!gameManager.isGameInProgress()) {
                player.sendMessage(Message.info("chat.gameNotStarted"));
                return true;
            }
            new GuiBingo(plugin, player, null).show();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> subCommandsList = new ArrayList<>();
        if (args.length == 1) subCommands.forEach(subCommand -> subCommandsList.add(subCommand.getName()));
        if (args.length == 2) return null;
        return subCommandsList;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    private boolean containsIgnoreCase(String[] aliases, String args) {
        for (String string : aliases) if (string.equalsIgnoreCase(args)) return true;
        return false;
    }
}
