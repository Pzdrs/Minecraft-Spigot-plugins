package me.pzdrs.bingo.guis;

import me.pzdrs.bingo.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class GUI implements InventoryHolder {
    protected Bingo plugin;
    protected Inventory inventory;
    protected Player player;

    public GUI(Bingo plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public abstract String getTitle();

    public abstract int getSize();

    public abstract void setContents();

    public abstract void handle(InventoryClickEvent event);

    public void reload() {
        inventory.clear();
        setContents();
    }

    public void show() {
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        setContents();
        player.openInventory(inventory);
    }
}
