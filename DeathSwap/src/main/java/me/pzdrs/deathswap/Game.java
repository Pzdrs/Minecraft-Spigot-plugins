package me.pzdrs.deathswap;

import org.bukkit.entity.Player;

import java.util.List;

public class Game {
    private DeathSwap plugin;
    private List<Player> players;
    private boolean timer = false;

    public Game(DeathSwap plugin) {
        this.plugin = plugin;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean getTimer() {
        return timer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }
}
