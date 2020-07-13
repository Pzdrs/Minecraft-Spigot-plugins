package me.pzdrs.bingo.utils;

import me.pzdrs.bingo.managers.ConfigurationManager;

public class Message {
    private static ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    public static String success(String path) {
        return Utils.color(configurationManager.getLang().getString("prefix") + " &a&l>>&7 " + configurationManager.getLang().getString(path).replace("&r", "&7"));
    }

    public static String info(String path) {
        return Utils.color(configurationManager.getLang().getString("prefix") + " &e&l>>&7 " + configurationManager.getLang().getString(path).replace("&r", "&7"));
    }

    public static String error(String path) {
        return Utils.color(configurationManager.getLang().getString("prefix") + " &c&l>>&7 " + configurationManager.getLang().getString(path).replace("&r", "&7"));
    }

    public static String noPerms() {
        return error("chat.noPermission");
    }

    public static String noPerms(String permission) {
        return error("chat.noPermissionAug").replace("$permission", permission);
    }
}
