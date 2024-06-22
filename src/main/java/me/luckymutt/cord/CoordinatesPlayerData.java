package me.luckymutt.cord;

import me.luckymutt.cord.config.Configuration;
import me.luckymutt.cord.config.ConfigurationManager;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("CoordinatesPlayer")
public class CoordinatesPlayerData implements ConfigurationSerializable {

    private boolean enabled;

    public CoordinatesPlayerData(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String buildConfigPath(UUID uuid, String rest) {
        return uuid.toString() + rest;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("enabled", enabled);

        return map;
    }

    public static CoordinatesPlayerData deserialize(Map<String, Object> map) {
        return new CoordinatesPlayerData((boolean) map.get("enabled"));
    }

    public static CoordinatesPlayerData createDefault() {
        Configuration mainConfiguration = ConfigurationManager.get()
                .getConfiguration(ConfigurationManager.Config.MAIN);

        return new CoordinatesPlayerData(mainConfiguration.get("enabled_by_default", Boolean.class));
    }
}
