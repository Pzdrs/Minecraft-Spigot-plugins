package me.pzdrs.bingo.utils;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.Mode;
import me.pzdrs.bingo.fastBoard.FastBoard;
import me.pzdrs.bingo.managers.ConfigurationManager;
import me.pzdrs.bingo.managers.GameManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Utils {
    private static Bingo plugin = Bingo.getInstance();
    private static ConfigurationManager config = plugin.getConfigurationManager();
    private static GameManager gameManager = plugin.getGameManager();

/*    public static void setFound(Player player, Material material) {
        plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
            if (uuid.equals(player.getUniqueId())) {
                for (BingoItem bingoItem : bingoPlayer.getCard()) {
                    if (bingoItem.getType().equals(material)) {
                        bingoItem.setFound(true);
                        player.sendMessage(Message.info("chat.found").replace("$item", typeToFriendlyName(material)));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                        return;
                    }
                }
            }
        });
        if (isComplete(player))
            Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameEndEvent.Outcome.DEFAULT, player));
    }*/

    public static boolean isEnoughPlayers() {
        return plugin.getPlayers().size() >= plugin.getConfig().getDouble("playersNeeded") * Bukkit.getMaxPlayers();
    }

    public static String typeToFriendlyName(Material material) {
        return WordUtils.capitalize(material.toString().toLowerCase().replace("_", " "));
    }

    public static FastBoard getGameScoreboard(Player player) {
        FastBoard scoreboard = new FastBoard(player);
        scoreboard.updateTitle(Utils.color(config.getLang().getString("scoreboard.header")));
        scoreboard.updateLines(
                "Time left: " + ChatColor.GREEN + "$timer",
                "",
                "Players: " + ChatColor.GREEN + plugin.getServer().getOnlinePlayers().size() + "/" + plugin.getServer().getMaxPlayers(),
                "",
                "Mode: " + ChatColor.GREEN + Mode.toString(gameManager.getMode()),
                "",
                "Your score: " + ChatColor.GREEN + 0,
                "",
                Utils.color(config.getLang().getString("scoreboard.footer"))
        );
        return scoreboard;
    }

    public static FastBoard getLobbyScoreboard(Player player) {
        FastBoard scoreboard = new FastBoard(player);
        scoreboard.updateTitle(Utils.color(config.getLang().getString("scoreboard.header")));
        scoreboard.updateLines(
                "Starting in: ",
                "",
                "Players: " + ChatColor.GREEN + plugin.getServer().getOnlinePlayers().size() + "/" + plugin.getServer().getMaxPlayers(),
                "",
                "Mode: " + ChatColor.GREEN + Mode.toString(gameManager.getMode()),
                "",
                Utils.color(config.getLang().getString("scoreboard.footer"))
        );
        return scoreboard;
    }

    public static void updateScoreboards() {
        if (gameManager.isGameInProgress()) {
            plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                if (gameManager.getTimer() <= 60) {
                    bingoPlayer.getScoreboard().updateLine(0, Utils.color("Time left:&a " + gameManager.getTimer() + "s"));
                } else {
                    bingoPlayer.getScoreboard().updateLine(0, Utils.color("Time left:&a " + gameManager.getTimer() / 60 + "m"));
                }
                bingoPlayer.getScoreboard().updateLine(2, "Players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + "/" + plugin.getServer().getMaxPlayers());
            });
        } else {
            plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                if (isEnoughPlayers()) {
                    bingoPlayer.getScoreboard().updateLine(0, "Starting in: " + ChatColor.GREEN + gameManager.getTimer());
                } else {
                    bingoPlayer.getScoreboard().updateLine(0, "Waiting for players...");
                }
                bingoPlayer.getScoreboard().updateLine(2, "Players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + "/" + plugin.getServer().getMaxPlayers());
            });
        }
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
