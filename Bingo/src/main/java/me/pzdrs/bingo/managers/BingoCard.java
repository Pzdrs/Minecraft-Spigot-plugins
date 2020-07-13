package me.pzdrs.bingo.managers;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.listeners.events.GameEndEvent;
import me.pzdrs.bingo.utils.Message;
import me.pzdrs.bingo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BingoCard {
    private Bingo plugin;
    private Player owner;
    private List<BingoItem> items;
    private int score;

    public BingoCard(Material[] items) {
        this.plugin = Bingo.getInstance();
        this.items = new ArrayList<>();
        // Convert array to HashMap
        for (Material material : items) {
            if (Arrays.asList(items).indexOf(material) == 12) {
                this.items.add(new BingoItem(Material.LIME_STAINED_GLASS_PANE, true));
            } else {
                this.items.add(new BingoItem(material, false));
            }
        }
    }

    public boolean checkItem(Material material) {
        if (!contains(material)) return false;
        if (alreadyFound(material)) return false;
        score++;
        get(material).setFound(true);
        owner.sendMessage(Message.info("chat.found").replace("$item", Utils.typeToFriendlyName(material)));
        owner.playSound(owner.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        plugin.getPlayer(owner.getUniqueId()).getScoreboard().updateLine(6, "Your score: " + ChatColor.GREEN + score);
        // Check if this item was the last needed to win
        if (isComplete())
            Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameEndEvent.Outcome.DEFAULT, owner));
        return true;
    }

    private boolean isComplete() {
        switch (plugin.getGameManager().getMode()) {
            case NORMAL:
                return checkRows() || checkColumns();
            case FULL_HOUSE:
                return score == 24;
        }
        return false;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<BingoItem> getItems() {
        return items;
    }

    public int getScore() {
        return score;
    }

    private boolean checkRows() {
        int done = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (items.get(i * 5 + j).isFound()) done++;
                if (done == 5) return true;
            }
            done = 0;
        }
        return false;
    }

    private boolean checkColumns() {
        int done = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (items.get(j * 5 + i).isFound()) done++;
                if (done == 5) return true;
            }
            done = 0;
        }
        return false;
    }

    private boolean contains(Material material) {
        for (BingoItem bingoItem : items) {
            if (bingoItem.getMaterial().equals(material)) {
                return true;
            }
        }
        return false;
    }

    private boolean alreadyFound(Material material) {
        for (BingoItem item : items) {
            if (item.getMaterial().equals(material) && item.isFound()) {
                return true;
            }
        }
        return false;
    }

    private BingoItem get(Material material) {
        for (BingoItem item : items) {
            if (item.getMaterial().equals(material)) return item;
        }
        return null;
    }
}
