package org.coordinates;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static Coordinates plugin = Coordinates.getPlugin(Coordinates.class);

    public static FileConfiguration config;


    public static void setup() {
        //Save default config if not existing
        plugin.saveDefaultConfig();

        config = plugin.getConfig();
    }

    public static Boolean getConfig(String path) {
        return config.getBoolean(path);
    }

    public static void setConfig(String key, Boolean bool) {
        config.set(key, bool);
        plugin.saveConfig();
    }

    public static void saveConfig() {
        plugin.saveConfig();
    }

}
