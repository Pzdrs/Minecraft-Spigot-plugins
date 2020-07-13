package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.guis.GuiBingo;
import me.pzdrs.bingo.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SubCommandSee extends SubCommand {
    private Bingo plugin;

    public SubCommandSee(Bingo plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "see";
    }

    @Override
    public String getDescription() {
        return "Displays player's bingo card";
    }

    @Override
    public String getUsage() {
        return "/bingo see <player>";
    }

    @Override
    public String getPermission() {
        return "bingo.see";
    }

    @Override
    public void handle(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(Message.info("chat.specifyPlayer"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Message.error("chat.playerNotOnline"));
            return;
        }
        new GuiBingo(plugin, player, target).show();
    }
}
