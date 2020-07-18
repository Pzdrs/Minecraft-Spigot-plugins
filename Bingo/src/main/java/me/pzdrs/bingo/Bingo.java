package me.pzdrs.bingo;

import me.pzdrs.bingo.commands.CommandBingo;
import me.pzdrs.bingo.listeners.*;
import me.pzdrs.bingo.managers.BingoPlayer;
import me.pzdrs.bingo.managers.ConfigurationManager;
import me.pzdrs.bingo.managers.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Bingo extends JavaPlugin {
    private static Bingo instance;
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private GameManager gameManager;

    private HashMap<UUID, BingoPlayer> players;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        configurationManager.setup();

        this.players = new HashMap<>();
        this.gameManager = new GameManager(this);

        init();

        // Verify configuration for items
        if (getConfig().getString("itemMode") == null) throw new NullPointerException("Item mode is null");
        if (getConfig().getString("itemMode").equalsIgnoreCase("whitelist") && getConfig().getStringList("whitelist").size() < 25)
            throw new IllegalArgumentException("Item mode is set to Whitelist, but there are less than 25 items specified.");
        // TODO: 7/18/2020 create new world for the players to play the game in
    }

    @Override
    public void onDisable() {
        players.clear();
    }

    public HashMap<UUID, BingoPlayer> getPlayers() {
        return players;
    }

    public FileConfiguration getLang() {
        return configurationManager.getLang();
    }

    public BingoPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public static Bingo getInstance() {
        return instance;
    }

    private void init() {
        new CommandBingo(this);

        new EventPlayerJoinQuit(this);
        new EventInventoryClick(this);
        new EventsItemAcquire(this);
        new EventAsyncPreLogin(this);
        new EventGameStartEnd(this);
        new EventPlayerInteract(this);
        new EventItemFound(this);
    }
}
