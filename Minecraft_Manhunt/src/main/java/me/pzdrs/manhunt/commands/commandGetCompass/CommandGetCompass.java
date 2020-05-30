package me.pzdrs.manhunt.commands.commandGetCompass;

import me.pzdrs.manhunt.Manhunt;
import me.pzdrs.manhunt.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandGetCompass implements CommandExecutor {
    private Manhunt plugin;

    public CommandGetCompass(Manhunt plugin) {
        this.plugin = plugin;

        plugin.getCommand("getCompass").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().severe("Only players may use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (plugin.getHunters().contains(player)) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            ItemMeta compassMeta = compass.getItemMeta();
            compassMeta.setDisplayName(Utils.color("&3Player tracker"));
            compass.setItemMeta(compassMeta);

            player.getInventory().addItem(compass);
            player.sendMessage(Utils.color(plugin.getConfig().getString("message.getCompass")));
        } else {
            player.sendMessage(Utils.color(plugin.getConfig().getString("message.notAHunter")));
        }
        return true;
    }
}
