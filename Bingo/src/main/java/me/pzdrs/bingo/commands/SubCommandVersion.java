package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.utils.Message;
import org.bukkit.entity.Player;

public class SubCommandVersion extends SubCommand {
    private Bingo plugin;

    SubCommandVersion(Bingo plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getDescription() {
        return "Check the version of the plugin";
    }

    @Override
    public String getUsage() {
        return "/bingo version";
    }

    @Override
    public String getPermission() {
        return "bingo.version";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"ver", "v"};
    }

    @Override
    public void handle(Player player, String[] args) {
        player.sendMessage(Message.info("chat.version")
                .replace("$plugin", plugin.getDescription().getName())
                .replace("$version", plugin.getDescription().getVersion())
                .replace("$author", plugin.getDescription().getAuthors().get(0)));
    }
}
