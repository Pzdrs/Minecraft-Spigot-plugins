package me.pzdrs.bingo.listeners;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.listeners.events.GameEndEvent;
import me.pzdrs.bingo.managers.BingoPlayer;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.Message;
import me.pzdrs.bingo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayerJoinQuit implements Listener {
    private Bingo plugin;
    private GameManager gameManager;

    public EventPlayerJoinQuit(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Set join message
        event.setJoinMessage(Message.info("chat.join").replace("$player", player.getDisplayName()));
        player.setGameMode(GameMode.SURVIVAL);
        // Put player in HashMap players
        plugin.getPlayers().put(player.getUniqueId(), new BingoPlayer(player, Utils.getLobbyScoreboard(player)));
        // Check if enough players to start the game
        if (Utils.isEnoughPlayers()) gameManager.startLobbyCountdown();
        updateScoreboards();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(Message.info("chat.quit").replace("$player", player.getDisplayName()));
        // Remove player from HashMap players
        plugin.getPlayers().remove(player.getUniqueId());
        // Check if still enough players to start the game
        if (!Utils.isEnoughPlayers() && gameManager.getLobbyCountdown() != null)
            gameManager.getLobbyCountdown().cancel();
        updateScoreboards();
        if (plugin.getPlayers().size() == 1 && gameManager.isGameInProgress())
            Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameEndEvent.Outcome.NO_PLAYERS_LEFT, Bukkit.getPlayer(plugin.getPlayers().keySet().stream().findFirst().get())));
    }

    private void updateScoreboards() {
        plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
            // Update countdown
            if (Utils.isEnoughPlayers()) {
                bingoPlayer.getScoreboard().updateLine(1, Utils.color(" &9>>&a " + gameManager.getTimer() + "s"));
            } else {
                bingoPlayer.getScoreboard().updateLine(1, Utils.color(" &9>>&7 Waiting for player..."));
            }
            bingoPlayer.getScoreboard().updateLine(4, Utils.color(" &9>>&a " + plugin.getPlayers().size() + "/" + plugin.getServer().getMaxPlayers()));
        });
    }
}
