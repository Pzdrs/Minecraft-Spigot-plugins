package me.pzdrs.bingo.managers;

import me.pzdrs.bingo.Bingo;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {
    private Bingo plugin;
    public static ConfigurationManager instance = new ConfigurationManager();

    private ConfigurationManager() {
    }

    private File langFile;
    private FileConfiguration langConfig;

    public static ConfigurationManager getInstance() {
        return instance;
    }

    public void setup() {
        plugin = Bingo.getInstance();
        plugin.saveDefaultConfig();

        langFile = new File(plugin.getDataFolder(), "lang.yml");

        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            plugin.saveResource("lang.yml", false);
        }

        langConfig = new YamlConfiguration();
        try {
            langConfig.load(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getLang() {
        return langConfig;
    }
}
