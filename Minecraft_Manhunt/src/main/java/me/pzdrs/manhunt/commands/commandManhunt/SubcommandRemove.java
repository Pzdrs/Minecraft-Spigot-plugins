package me.pzdrs.manhunt.commands.commandManhunt;

import me.pzdrs.manhunt.Manhunt;
import me.pzdrs.manhunt.Utils;
import me.pzdrs.manhunt.commands.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SubcommandRemove extends Subcommand {
    private Manhunt plugin;

    public SubcommandRemove(Manhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Remove a player from a specific group";
    }

    @Override
    public String getUsage() {
        return "/manhunt remove <group> <player>";
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
                        plugin.getHunters().remove(target);
                        player.sendMessage(Utils.color(plugin.getConfig().getString("message.playerRemoved").replace("$player", target.getDisplayName()).replace("$group", "Hunter")));
                        break;
                    case "speedrunner":
                        plugin.getSpeedrunners().remove(target);
                        player.sendMessage(Utils.color(plugin.getConfig().getString("message.playerRemoved").replace("$player", target.getDisplayName()).replace("$group", "Speedrunner")));
                        break;
                    default:
                        player.sendMessage(Utils.color(plugin.getConfig().getString("message.invalidGroup")));
                        break;
                }
                target.setPlayerListName(target.getName());
            } catch (NullPointerException exception) {
                player.sendMessage(Utils.color(plugin.getConfig().getString("message.playerOffline")));
            }
        }
    }
}
