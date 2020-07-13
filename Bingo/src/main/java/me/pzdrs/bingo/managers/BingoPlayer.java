package me.pzdrs.bingo.managers;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.fastBoard.FastBoard;
import org.bukkit.entity.Player;

public class BingoPlayer {
    private Bingo plugin;
    private Player player;
    private BingoCard card;
    private FastBoard scoreboard;
    private boolean cheatMode;

    public BingoPlayer(Player player, FastBoard scoreboard) {
        this.plugin = Bingo.getInstance();
        this.player = player;
        this.scoreboard = scoreboard;
        this.cheatMode = false;
    }

    public void setScoreboard(FastBoard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void setCard(BingoCard card) {
        this.card = card;
        card.setOwner(player);
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    public Bingo getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public BingoCard getCard() {
        return card;
    }

    public FastBoard getScoreboard() {
        return scoreboard;
    }

    public boolean isCheatMode() {
        return cheatMode;
    }
}
