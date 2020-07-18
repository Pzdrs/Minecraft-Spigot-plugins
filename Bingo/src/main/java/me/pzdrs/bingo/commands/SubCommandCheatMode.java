package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.Message;
import org.bukkit.entity.Player;

public class SubCommandCheatMode extends SubCommand {
    private Bingo plugin;
    private GameManager gameManager;

    SubCommandCheatMode(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
    }

    @Override
    public String getName() {
        return "cheatMode";
    }

    @Override
    public String getDescription() {
        return "Toggles the cheat mode";
    }

    @Override
    public String getUsage() {
        return "/bingo cheatMode";
    }

    @Override
    public String getPermission() {
        return "bingo.cheatMode";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"cM"};
    }

    @Override
    public void handle(Player player, String[] args) {
        if (!gameManager.isGameInProgress()) {
            player.sendMessage(Message.info("chat.gameNotStarted"));
            return;
        }
        if (plugin.getPlayer(player.getUniqueId()).isCheatMode()) {
            plugin.getPlayer(player.getUniqueId()).setCheatMode(false);
            player.sendMessage(Message.success("chat.cheatModeDisabled").replace("$state", "disabled"));
            return;
        }
        plugin.getPlayer(player.getUniqueId()).setCheatMode(true);
        player.sendMessage(Message.success("chat.cheatModeEnabled").replace("$state", "enabled"));
    }
}
