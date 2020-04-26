package me.pzdrs.deathswap;

import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract String getName();

    public abstract String getDescription();

    public abstract String getUsage();

    public abstract void handle(Player player, String[] args);
}
