package me.pzdrs.deathswap;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskTimer extends BukkitRunnable {
    private DeathSwap plugin;
    private Game game;
    private int timer;

    public TaskTimer(DeathSwap plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
        this.timer = plugin.getConfig().getInt("period");
    }

    @Override
    public void run() {
        if (!game.getTimer()) cancel();
        timer--;
        if (timer == 0) {
            Location player1 = game.getPlayers().get(0).getLocation();
            Location player2 = game.getPlayers().get(1).getLocation();

            game.getPlayers().get(0).teleport(player2);
            game.getPlayers().get(1).teleport(player1);
            timer = plugin.getConfig().getInt("period");
        }
        if (timer <= 10) {
            plugin.getServer().getOnlinePlayers().forEach(online -> online.sendMessage(Utils.color("&c&lSwapping in " + timer + (timer == 1 ? " second" : " seconds") + "!")));
        }
    }
}
