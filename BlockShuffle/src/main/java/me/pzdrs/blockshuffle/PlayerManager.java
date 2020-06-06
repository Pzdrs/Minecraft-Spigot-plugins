package me.pzdrs.blockshuffle;

import me.pzdrs.blockshuffle.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerManager {
    private Player player;
    private Material currentBlock;
    private boolean finished;

    public PlayerManager(Player player, Material randomBlock) {
        this.player = player;
        this.currentBlock = randomBlock;
        this.finished = false;

        player.sendMessage(Utils.color("&aYou must find and stand on a " + currentBlock.toString().toLowerCase().replace("_", " ") + "."));
    }

    public Player getPlayer() {
        return player;
    }

    public Material getCurrentBlock() {
        return currentBlock;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
