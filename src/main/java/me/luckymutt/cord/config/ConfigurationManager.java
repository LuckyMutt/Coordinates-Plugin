package me.luckymutt.cord.config;

import me.luckymutt.cord.Coordinates;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

    private final static ConfigurationManager INSTANCE = new ConfigurationManager();

    private final Plugin plugin;
    private final Map<String, Configuration> configurationMap = new HashMap<>();

    public enum Config {
        MAIN,
        PLAYER_DATA;

        public String getConfigName() {
            return name().toLowerCase();
        }
    }

    private ConfigurationManager() {
        this.plugin = JavaPlugin.getProvidingPlugin(Coordinates.class);
        loadConfigurations();
    }

    private void loadConfigurations() {
        configurationMap.put(
                Config.MAIN.getConfigName(),
                new Configuration(plugin, "config.yml")
                        .copy("config.yml")
                        .load()
        );

        configurationMap.put(
                Config.PLAYER_DATA.getConfigName(),
                new Configuration(plugin, "player_data.yml")
                        .load()
        );
    }

    public Configuration getConfiguration(Config config) {
        return configurationMap.get(config.getConfigName());
    }

    public static ConfigurationManager get() {
        return INSTANCE;
    }

}
