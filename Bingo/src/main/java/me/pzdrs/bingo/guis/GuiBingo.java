package me.pzdrs.bingo.guis;

import me.pzdrs.bingo.Bingo;
import me.pzdrs.bingo.managers.BingoItem;
import me.pzdrs.bingo.managers.BingoPlayer;
import me.pzdrs.bingo.managers.ConfigurationManager;
import me.pzdrs.bingo.managers.GameManager;
import me.pzdrs.bingo.utils.ItemBuilder;
import me.pzdrs.bingo.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiBingo extends GUI {
    private GameManager gameManager;
    private Player player;
    private Player target;

    public GuiBingo(Bingo plugin, Player player, Player target) {
        super(plugin, player);
        this.gameManager = plugin.getGameManager();
        this.player = player;
        this.target = target;
    }

    @Override
    public String getTitle() {
        return Utils.color(target == null ? plugin.getLang().getString("other.ownCard") :
                plugin.getLang().getString("other.playerCard").replace("$player", target.getDisplayName()));
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public void setContents() {
        for (int i = 0; i < 45; i += 9) {
            inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).removeDisplayName().build());
            inventory.setItem(i + 1, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).removeDisplayName().build());
            inventory.setItem(i + 7, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).removeDisplayName().build());
            inventory.setItem(i + 8, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).removeDisplayName().build());
        }

        for (BingoItem item : plugin.getPlayer(player.getUniqueId()).getCard().getItems()) {
            if (!item.isFound()) {
                inventory.addItem(new ItemBuilder(item.getMaterial())
                        .addLoreLine("&7&m                                     ")
                        .addLoreLine(plugin.getLang().getString("other.notFound"))
                        .build());
            } else {
                if (item.getMaterial().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    inventory.addItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                            .setDisplayName(plugin.getLang().getString("other.freeSpace"))
                            .addLoreLine("&7&m                                     ")
                            .addLoreLine(plugin.getLang().getString("other.found"))
                            .build());
                } else {
                    inventory.addItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                            .setDisplayName(ChatColor.RESET + Utils.typeToFriendlyName(item.getMaterial()))
                            .addLoreLine("&7&m                                     ")
                            .addLoreLine(plugin.getLang().getString("other.found"))
                            .build());
                }
            }
        }
    }

    @Override
    public void handle(InventoryClickEvent event) {
        BingoPlayer bingoPlayer = plugin.getPlayer(player.getUniqueId());
        if (!bingoPlayer.isCheatMode()) return;
        bingoPlayer.getCard().checkItem(event.getCurrentItem().getType());
        reload();
    }
}
