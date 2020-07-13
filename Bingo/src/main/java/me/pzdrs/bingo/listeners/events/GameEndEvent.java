package me.pzdrs.bingo.listeners.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {
    public enum Outcome {
        DEFAULT,
        TIMEOUT
    }

    private Outcome outcome;
    private Player winner;
    private static final HandlerList handlers = new HandlerList();

    public GameEndEvent(Outcome outcome, Player winner) {
        this.winner = winner;
        this.outcome = outcome;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Player getWinner() {
        return winner;
    }
}
