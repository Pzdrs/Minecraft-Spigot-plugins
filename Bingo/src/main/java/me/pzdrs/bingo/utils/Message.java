package me.pzdrs.bingo.utils;

import me.pzdrs.bingo.Bingo;

public class Message {
    private static Bingo plugin = Bingo.getInstance();

    public static String success(String path) {
        return Utils.color(plugin.getLang().getString("prefix") + " &a&l>>&7 " + plugin.getLang().getString(path).replace("&r", "&7"));
    }

    public static String info(String path) {
        return Utils.color(plugin.getLang().getString("prefix") + " &e&l>>&7 " + plugin.getLang().getString(path).replace("&r", "&7"));
    }

    public static String error(String path) {
        return Utils.color(plugin.getLang().getString("prefix") + " &c&l>>&7 " + plugin.getLang().getString(path).replace("&r", "&7"));
    }

    public static String noPerms() {
        return error("chat.noPermission");
    }

    public static String noPerms(String permission) {
        return error("chat.noPermissionAug").replace("$permission", permission);
    }
}
