package me.pzdrs.playertracker;

import me.pzdrs.playertracker.commands.CommandGetTracker;
import me.pzdrs.playertracker.commands.CommandTrack;
import me.pzdrs.playertracker.events.EventPlayerInteract;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class PlayerTracker extends JavaPlugin {
    private HashMap<Player, Player> players;

    @Override
    public void onEnable() {
        players = new HashMap<>();
        saveDefaultConfig();
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        new CommandGetTracker(this);
        new CommandTrack(this);
        new EventPlayerInteract(this);
    }

    public HashMap<Player, Player> getPlayers() {
        return players;
    }
}
