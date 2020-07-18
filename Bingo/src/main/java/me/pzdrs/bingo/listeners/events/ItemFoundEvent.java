package me.pzdrs.bingo.listeners.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemFoundEvent extends Event {
    private Material item;
    private Player player;
    private static final HandlerList handlers = new HandlerList();

    public ItemFoundEvent(Material item, Player player) {
        this.item = item;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Material getItemType() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }
}
