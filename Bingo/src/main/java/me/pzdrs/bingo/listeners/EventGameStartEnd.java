package me.pzdrs.bingo.listeners;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.listeners.events.GameEndEvent;
import me.pzdrs.bingo.listeners.events.GameStartEvent;
import me.pzdrs.bingo.managers.ConfigurationManager;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventGameStartEnd implements Listener {
    private Bingo plugin;
    private GameManager gameManager;
    private ConfigurationManager configurationManager;

    public EventGameStartEnd(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = GameManager.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        gameManager.generateCards();
        gameManager.setGameInProgress(true);
        gameManager.startGameCountdown();
        // Set game scoreboards to everyone
        plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
            bingoPlayer.setScoreboard(Utils.getGameScoreboard(Bukkit.getPlayer(uuid)));
        });
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        gameManager.setGameInProgress(false);
        gameManager.getGameCountdown().cancel();
        switch (event.getOutcome()) {
            case DEFAULT:
                Player winner = event.getWinner();
                plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                    // Handle everyone
                    if (winner.getUniqueId().equals(uuid)) {
                        // Handle winner
                        winner.playSound(winner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
                        winner.spawnParticle(Particle.FIREWORKS_SPARK, winner.getLocation(), 150);
                        winner.sendTitle(Utils.color(configurationManager.getLang().getString("other.victoryTitle")), Utils.color(configurationManager.getLang().getString("other.victorySubtitle")), 20, 100, 20);
                    } else {
                        // Handle loser
                        Player loser = Bukkit.getPlayer(uuid);
                        loser.sendTitle(Utils.color(configurationManager.getLang().getString("other.gameOverTitle")), Utils.color(configurationManager.getLang().getString("other.gameOverSubtitle").replace("$winner", winner.getDisplayName())), 20, 100, 20);
                        loser.playSound(loser.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);

                    }
                });
                break;
            case TIMEOUT:
                plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                    Player player = Bukkit.getPlayer(uuid);
                    player.sendTitle(Utils.color(configurationManager.getLang().getString("other.gameOverTitle")), Utils.color(configurationManager.getLang().getString("other.gameOverSubtitle2")), 20, 100, 20);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
                });
                break;
        }
        // Handle all players - UI
        Bukkit.getOnlinePlayers().forEach(o -> {
            o.getInventory().clear();
            o.setGameMode(GameMode.ADVENTURE);
            o.setHealth(20d);
            o.setFoodLevel(20);
            o.closeInventory();
            plugin.getPlayer(o.getUniqueId()).getScoreboard().delete();
        });
        // Kick everyone and restart the server
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            plugin.getServer().getOnlinePlayers().forEach(o -> o.kickPlayer("This server is restarting!"));
            plugin.getServer().reload();
        }, 10 * 20);
    }
}
