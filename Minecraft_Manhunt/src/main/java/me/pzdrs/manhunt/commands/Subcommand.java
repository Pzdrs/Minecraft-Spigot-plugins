package me.pzdrs.manhunt.commands;

import org.bukkit.entity.Player;

public abstract class Subcommand {
    public abstract String getName();

    public abstract String getDescription();

    public abstract String getUsage();

    public abstract void action(Player player, String args[]);
}
