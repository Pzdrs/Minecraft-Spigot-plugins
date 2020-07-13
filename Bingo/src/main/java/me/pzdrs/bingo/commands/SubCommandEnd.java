package me.pzdrs.bingo.commands;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.SubCommand;
import me.pzdrs.bingo.listeners.events.GameEndEvent;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SubCommandEnd extends SubCommand {
    private Bingo plugin;
    private GameManager gameManager;

    public SubCommandEnd(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = GameManager.getInstance();
    }

    @Override
    public String getName() {
        return "end";
    }

    @Override
    public String getDescription() {
        return "End the game manually";
    }

    @Override
    public String getUsage() {
        return "/bingo end";
    }

    @Override
    public String getPermission() {
        return "bingo.end";
    }

    @Override
    public void handle(Player player, String[] args) {
        if (!gameManager.isGameInProgress()) {
            player.sendMessage(Message.info("chat.gameNotStarted"));
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameEndEvent.Outcome.DEFAULT, player));
        player.sendMessage(Message.success("chat.manualGameStop"));
    }
}
