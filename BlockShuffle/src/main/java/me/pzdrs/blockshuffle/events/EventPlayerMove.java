package me.pzdrs.blockshuffle.events;

import me.pzdrs.blockshuffle.BlockShuffle;
import me.pzdrs.blockshuffle.PlayerManager;
import me.pzdrs.blockshuffle.utilities.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventPlayerMove implements Listener {
    private BlockShuffle plugin;

    public EventPlayerMove(BlockShuffle plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!plugin.gameInProgress)
            return;
        for (PlayerManager player : plugin.getPlayers()) {
            if (player.getPlayer() == event.getPlayer()) {
                Block blockUnderPlayer = new Location(
                        event.getPlayer().getLocation().getWorld(),
                        event.getPlayer().getLocation().getX(),
                        event.getPlayer().getLocation().getY() - 1,
                        event.getPlayer().getLocation().getZ()
                ).getBlock();
                if ((blockUnderPlayer.getType() == player.getCurrentBlock() || event.getPlayer().getLocation().getBlock().getType() == player.getCurrentBlock()) && !player.isFinished()) {
                    plugin.getServer().getOnlinePlayers().forEach(online -> online.sendMessage(Utils.color("&6&l" + player.getPlayer().getDisplayName() + " found their block!")));
                    player.setFinished(true);
                    if (Utils.allFinished()) {
                        plugin.getPlayers().clear();
                        Utils.loadPlayers();
                        plugin.timer = plugin.getConfig().getInt("period");
                    }
                }
            }
        }
    }
}
