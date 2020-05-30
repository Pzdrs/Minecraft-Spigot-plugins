package me.pzdrs.manhunt;

import me.pzdrs.manhunt.commands.commandGetCompass.CommandGetCompass;
import me.pzdrs.manhunt.commands.commandManhunt.CommandManhunt;
import me.pzdrs.manhunt.events.EventPlayerInteraction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Manhunt extends JavaPlugin {
    private List<Player> speedrunners, hunters;

    @Override
    public void onEnable() {
        this.speedrunners = new ArrayList<>();
        this.hunters = new ArrayList<>();
        saveDefaultConfig();

        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        new CommandManhunt(this);
        new CommandGetCompass(this);

        new EventPlayerInteraction(this);
    }

    public List<Player> getSpeedrunners() {
        return speedrunners;
    }

    public List<Player> getHunters() {
        return hunters;
    }
}
