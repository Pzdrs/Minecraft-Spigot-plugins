package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class SubCommandHelp extends SubCommand {
    private List<SubCommand> subCommands;

    public SubCommandHelp(List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displays the help page";
    }

    @Override
    public String getUsage() {
        return "/bingo help";
    }

    @Override
    public String getPermission() {
        return "bingo.help";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public void handle(Player player, String[] args) {
        player.sendMessage(Utils.color("&7&m                                               "));
        subCommands.forEach(subCommand -> player.sendMessage(Utils.color("&9" + subCommand.getUsage() + " &8-&r " + subCommand.getDescription())));
        player.sendMessage(Utils.color("&7&m                                               "));
    }
}
