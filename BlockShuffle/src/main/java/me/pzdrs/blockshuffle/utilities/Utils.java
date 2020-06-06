package me.pzdrs.blockshuffle.utilities;

import me.pzdrs.blockshuffle.BlockShuffle;
import me.pzdrs.blockshuffle.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {
    private static BlockShuffle plugin = BlockShuffle.getInstance();
    private static Random random = new Random();

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Material getRandomBlock() {
        boolean isBlacklisted;
        Material randomBlock;
        do {
            isBlacklisted = false;
            randomBlock = Arrays.asList(Material.values()).get(random.nextInt(Material.values().length));
            for (String keyword : plugin.getConfig().getStringList("blacklist-keywords")) {
                if (randomBlock.toString().toLowerCase().contains(keyword.toLowerCase())) {
                    isBlacklisted = true;
                }
            }
        } while (!randomBlock.isBlock() || isBlacklisted);
        return randomBlock;
    }

    public static void loadPlayers() {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            plugin.getPlayers().add(new PlayerManager(player, Utils.getRandomBlock()));
        });
    }

    public static boolean allFinished() {
        for (PlayerManager player : plugin.getPlayers()) {
            if (!player.isFinished())
                return false;
        }
        return true;
    }
}
