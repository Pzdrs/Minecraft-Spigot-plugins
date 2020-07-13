package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.listeners.events.GameStartEvent;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SubCommandStart extends SubCommand {
    private Bingo plugin;
    private GameManager gameManager;

    public SubCommandStart(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = GameManager.getInstance();
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start the game manually";
    }

    @Override
    public String getUsage() {
        return "/bingo start";
    }

    @Override
    public String getPermission() {
        return "bingo.start";
    }

    @Override
    public void handle(Player player, String[] args) {
        if (gameManager.isGameInProgress()) {
            player.sendMessage(Message.info("chat.gameStarted"));
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());
        gameManager.setGameInProgress(true);
        player.sendMessage(Message.success("chat.manualGameStart"));
    }
}
