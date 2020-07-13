package me.pzdrs.bingo.managers;

import org.bukkit.Material;

public class BingoItem {
    private Material material;
    private boolean found;

    public BingoItem(Material material, boolean found) {
        this.material = material;
        this.found = found;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
