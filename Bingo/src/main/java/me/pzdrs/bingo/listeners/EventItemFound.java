package me.pzdrs.bingo.listeners;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.listeners.events.ItemFoundEvent;
import me.pzdrs.bingo.managers.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventItemFound implements Listener {
    private Bingo plugin;
    private GameManager gameManager;

    public EventItemFound(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemFound(ItemFoundEvent event) {
        if (!gameManager.isGameInProgress()) return;
        plugin.getPlayer(event.getPlayer().getUniqueId()).getCard().checkItem(event.getItemType());
    }
}
