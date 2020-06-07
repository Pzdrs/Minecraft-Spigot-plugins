package me.pzdrs.playertracker.events;

import me.pzdrs.playertracker.PlayerTracker;
import me.pzdrs.playertracker.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EventPlayerInteract implements Listener {
    private PlayerTracker plugin;

    public EventPlayerInteract(PlayerTracker plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
                if (plugin.getPlayers().containsKey(player)) {
                    if (!Bukkit.getOnlinePlayers().contains(plugin.getPlayers().get(player))) {
                        player.sendMessage(Utils.color("&cThis player is no longer online."));
                        resetLore(event.getItem(), player);
                        return;
                    }
                    player.setCompassTarget(plugin.getPlayers().get(player).getLocation());
                    resetLore(event.getItem(), player);
                    player.sendMessage(Utils.color("&aCompass now pointing to &9" + plugin.getPlayers().get(player).getName()
                            + (plugin.getConfig().getBoolean("show-distance") ? "&a, he is &6"
                            + Utils.getDistance(player.getLocation(), plugin.getPlayers().get(player).getLocation()) + " &ablocks away." : "&a.")));
                }
            }
        }
    }

    private void resetLore(ItemStack itemStack, Player player) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> newLore = new ArrayList<>();
        newLore.add(Utils.color("&7Target: &a" + (Bukkit.getOnlinePlayers().contains(plugin.getPlayers().get(player)) ? plugin.getPlayers().get(player).getName() : "&cNot Online")));
        newLore.add(Utils.color("&7Right-click to track a player"));
        newLore.add("");
        newLore.add(Utils.color("&7Use &a/track &7command to choose who to track"));
        meta.setLore(newLore);
        itemStack.setItemMeta(meta);
    }
}
