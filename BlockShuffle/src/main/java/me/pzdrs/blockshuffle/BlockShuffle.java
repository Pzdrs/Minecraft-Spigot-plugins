package me.pzdrs.blockshuffle;

import me.pzdrs.blockshuffle.commands.CommandBlockShuffle;
import me.pzdrs.blockshuffle.events.EventPlayerMove;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class BlockShuffle extends JavaPlugin {
    public static BlockShuffle instance;
    private List<PlayerManager> players;
    public int timer;
    public boolean gameInProgress = false;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        init();

        this.players = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BlockShuffle getInstance() {
        return instance;
    }

    public List<PlayerManager> getPlayers() {
        return players;
    }

    private void init() {
        new CommandBlockShuffle(this);
        new EventPlayerMove(this);
    }
}
