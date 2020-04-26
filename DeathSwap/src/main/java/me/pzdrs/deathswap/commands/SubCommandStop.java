package me.pzdrs.deathswap.commands;

import me.pzdrs.deathswap.DeathSwap;
import me.pzdrs.deathswap.Game;
import me.pzdrs.deathswap.SubCommand;
import me.pzdrs.deathswap.Utils;
import org.bukkit.entity.Player;

public class SubCommandStop extends SubCommand {
    private DeathSwap plugin;
    private CommandDeathSwap command;
    private Game game;

    public SubCommandStop(DeathSwap plugin, CommandDeathSwap command) {
        this.plugin = plugin;
        this.command = command;
        this.game = plugin.getGame();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stop the timer";
    }

    @Override
    public String getUsage() {
        return Utils.getUsage(command.getMainCommand(), getName());
    }

    @Override
    public void handle(Player player, String[] args) {
        if (!game.getTimer()) {
            player.sendMessage(Utils.color("&cThe timer is already stopped!"));
            return;
        }
        player.sendMessage(Utils.color("&cYou stopped the timer."));
        game.setTimer(false);
    }
}
