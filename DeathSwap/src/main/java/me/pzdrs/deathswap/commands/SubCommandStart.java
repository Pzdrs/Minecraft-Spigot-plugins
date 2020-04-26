package me.pzdrs.deathswap.commands;

import me.pzdrs.deathswap.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCommandStart extends SubCommand {
    private DeathSwap plugin;
    private CommandDeathSwap command;
    private Game game;
    private int timer = 0;

    public SubCommandStart(DeathSwap plugin, CommandDeathSwap command) {
        this.plugin = plugin;
        this.command = command;
        this.game = plugin.getGame();
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start the timer";
    }

    @Override
    public String getUsage() {
        return Utils.getUsage(command.getMainCommand(), getName());
    }

    @Override
    public void handle(Player player, String[] args) {
        if (game.getTimer()) {
            player.sendMessage(Utils.color("&cThe timer is already running!"));
            return;
        }
        if (plugin.getServer().getOnlinePlayers().size() != 2) {
            player.sendMessage(Utils.color("&cThere has to be exactly 2 people on the server!"));
            return;
        }
        new TaskTimer(plugin, game).runTaskTimer(plugin, 0, 20);
        player.sendMessage(Utils.color("&aYou started the timer."));

        List<Player> players = new ArrayList<>();
        plugin.getServer().getOnlinePlayers().forEach(online -> players.add(online));
        game.setPlayers(players);
        game.setTimer(true);
    }
}
