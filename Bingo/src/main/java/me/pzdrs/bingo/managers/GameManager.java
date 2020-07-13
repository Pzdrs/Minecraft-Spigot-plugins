package me.pzdrs.bingo.managers;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.Mode;
import me.pzdrs.bingo.listeners.events.GameEndEvent;
import me.pzdrs.bingo.listeners.events.GameStartEvent;
import me.pzdrs.bingo.utils.Message;
import me.pzdrs.bingo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Random;

public class GameManager {
    private static GameManager instance;
    private Bingo plugin;
    private ConfigurationManager configurationManager;
    private boolean gameInProgress;
    private Material[] cardItems;
    private Mode mode;
    private BukkitTask gameCountdown, lobbyCountdown;
    private int timer;

    public GameManager(Bingo plugin) {
        instance = this;
        this.plugin = plugin;
        this.configurationManager = plugin.getConfigurationManager();
        this.gameInProgress = false;
        this.cardItems = new Material[25];
        this.mode = plugin.getConfig().getString("mode").equalsIgnoreCase("normal") ? Mode.NORMAL : Mode.FULL_HOUSE;
    }

    public void setGameInProgress(boolean b) {
        gameInProgress = b;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public Mode getMode() {
        return mode;
    }

    public BukkitTask getGameCountdown() {
        return gameCountdown;
    }

    public BukkitTask getLobbyCountdown() {
        return lobbyCountdown;
    }

    public void generateCards() {
        for (int i = 0; i < cardItems.length; i++) {
            Material material;
            do {
                material = Material.values()[new Random().nextInt(Material.values().length)];
            } while (plugin.getConfig().getStringList("blacklist").contains(material.toString()) ||
                    Arrays.asList(cardItems).contains(material) || material.equals(Material.LIME_STAINED_GLASS_PANE) || !material.isItem());
            cardItems[i] = material;
        }
        // Assign the cards to players
        plugin.getPlayers().forEach((uuid, bingoPlayer) -> bingoPlayer.setCard(new BingoCard(cardItems)));
        for (Material cardItem : cardItems) {
            System.out.println(cardItem);
        }
    }

    public void startGameCountdown() {
        timer = (int) ((plugin.getConfig().getString("mode").equals("normal") ? plugin.getConfig().getDouble("countdown.normal") : plugin.getConfig().getDouble("countdown.full-house")) * 60);
        this.gameCountdown = new BukkitRunnable() {
            @Override
            public void run() {
                if (timer == 0) cancel();
                plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                    if (timer <= 60) {
                        bingoPlayer.getScoreboard().updateLine(0, Utils.color("Time left:&a " + timer + "s"));
                    } else {
                        bingoPlayer.getScoreboard().updateLine(0, Utils.color("Time left:&a " + timer / 60 + "m"));
                    }
                });
                timer--;
            }

            @Override
            public synchronized void cancel() throws IllegalStateException {
                super.cancel();
                Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameEndEvent.Outcome.TIMEOUT, null));
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void startLobbyCountdown() {
        timer = plugin.getConfig().getInt("countdown.lobby");
        this.lobbyCountdown = new BukkitRunnable() {
            @Override
            public void run() {
                if (timer == 0) {
                    Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());
                    cancel();
                    return;
                }
                Bukkit.getOnlinePlayers().forEach(online -> {
                    online.sendMessage(Message.info("chat.lobbyCountdown").replace("$timer", String.valueOf(timer)));
                    online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                });
                Utils.updateScoreboards();
                timer--;
            }

            @Override
            public synchronized void cancel() throws IllegalStateException {
                super.cancel();
                Utils.updateScoreboards();
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public int getTimer() {
        return timer;
    }
}
