package me.pzdrs.deathswap;

import me.pzdrs.deathswap.commands.CommandDeathSwap;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathSwap extends JavaPlugin {
    private Game game;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        game = new Game(this);

        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        new CommandDeathSwap(this);
    }

    public Game getGame() {
        return game;
    }
}
