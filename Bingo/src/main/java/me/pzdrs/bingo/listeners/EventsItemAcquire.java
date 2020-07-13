package me.pzdrs.bingo.listeners;

import me.pzdrs.bingo.Bingo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class EventsItemAcquire implements Listener {
    private Bingo plugin;

    public EventsItemAcquire(Bingo plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!plugin.getGameManager().isGameInProgress()) return;
        plugin.getPlayer(event.getEntity().getUniqueId()).getCard().checkItem(event.getItem().getItemStack().getType());
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!plugin.getGameManager().isGameInProgress()) return;
        plugin.getPlayer(event.getWhoClicked().getUniqueId()).getCard().checkItem(event.getCurrentItem().getType());
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        if (!plugin.getGameManager().isGameInProgress()) return;
        plugin.getPlayer(event.getPlayer().getUniqueId()).getCard().checkItem(event.getItemType());
    }
}
