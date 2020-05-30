package me.pzdrs.manhunt.events;

import me.pzdrs.manhunt.Manhunt;
import me.pzdrs.manhunt.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class EventPlayerInteraction implements Listener {
    private Manhunt plugin;

    public EventPlayerInteraction(Manhunt plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getType() == Material.COMPASS && plugin.getHunters().contains(event.getPlayer())) {
            Player player = event.getPlayer();
            if (plugin.getSpeedrunners().isEmpty()) {
                player.sendMessage(Utils.color(plugin.getConfig().getString("message.noSpeedrunnersSoFar")));
                return;
            }
            int random = new Random().nextInt(plugin.getSpeedrunners().size());
            player.setCompassTarget(plugin.getSpeedrunners().get(random).getLocation());
            player.sendMessage(Utils.color(plugin.getConfig().getString("message.tracker").replace("$player", plugin.getSpeedrunners().get(random).getDisplayName())));
        }
    }
}
