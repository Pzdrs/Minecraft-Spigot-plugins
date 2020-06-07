package me.pzdrs.playertracker.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static double getDistance(Location location, Location location2) {
        return Math.round(
                Math.sqrt(
                        Math.pow(location2.getX() - location.getX(), 2) +
                                Math.pow(location2.getY() - location.getY(), 2) +
                                Math.pow(location2.getZ() - location.getZ(), 2)
                )
        );
    }
}
