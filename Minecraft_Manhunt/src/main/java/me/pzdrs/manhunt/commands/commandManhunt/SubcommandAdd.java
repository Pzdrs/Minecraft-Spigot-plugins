package me.pzdrs.manhunt.commands.commandManhunt;

import me.pzdrs.manhunt.Manhunt;
import me.pzdrs.manhunt.Utils;
import me.pzdrs.manhunt.commands.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SubcommandAdd extends Subcommand {
    private Manhunt plugin;

    public SubcommandAdd(Manhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add a player to a specific group";
    }

    @Override
    public String getUsage() {
        return "/manhunt add <group> <player>";
    }

    @Override
    public void action(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(Utils.color(plugin.getConfig().getString("message.specifyGroup")));
        } else if (args.length == 2) {
            player.sendMessage(Utils.color(plugin.getConfig().getString("message.specifyPlayer")));
        } else if (args.length == 3) {
            try {
                Player target = Bukkit.getPlayer(args[2]);
                switch (args[1]) {
                    case "hunter":
                        plugin.getHunters().add(target);
                        player.sendMessage(Utils.color(plugin.getConfig().getString("message.playerAdded").replace("$player", target.getDisplayName()).replace("$group", "Hunter")));
                        player.setPlayerListName(Utils.color("&cHUNTER&r " + player.getName()));
                        break;
                    case "speedrunner":
                        plugin.getSpeedrunners().add(target);
                        player.sendMessage(Utils.color(plugin.getConfig().getString("message.playerAdded").replace("$player", target.getDisplayName()).replace("$group", "Speedrunner")));
                        player.setPlayerListName(Utils.color("&aSPEEDRUNNER&r " + player.getName()));
                        break;
                    default:
                        player.sendMessage(Utils.color(plugin.getConfig().getString("message.invalidGroup")));
                        break;
                }
            } catch (NullPointerException exception) {
                player.sendMessage(Utils.color(plugin.getConfig().getString("message.playerOffline")));
            }
        }
    }
}
