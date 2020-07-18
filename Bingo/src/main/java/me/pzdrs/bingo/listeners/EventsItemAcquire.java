package me.pzdrs.bingo.listeners;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.listeners.events.ItemFoundEvent;
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
        plugin.getServer().getPluginManager().callEvent(new ItemFoundEvent(event.getItem().getItemStack().getType(), (Player) event.getEntity()));
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        plugin.getServer().getPluginManager().callEvent(new ItemFoundEvent(event.getCurrentItem().getType(), (Player) event.getWhoClicked()));
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        plugin.getServer().getPluginManager().callEvent(new ItemFoundEvent(event.getItemType(), event.getPlayer()));
    }
}
