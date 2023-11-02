package org.coordinates;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;

public class DataManager {

    private static Coordinates plugin = Coordinates.getPlugin(Coordinates.class);

    //Files
    public static FileConfiguration dataConfig;
    public static File dataFile;

    public static void setup() {

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        dataFile = new File(plugin.getDataFolder(), "data.yml");

        if (!dataFile.exists()) {
            try { dataFile.createNewFile();

            } catch (IOException e) {
                getLogger().info("Could not create data.yml file");
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    }

    public static Boolean getData(Player player) {
        String str_uuid = player.getUniqueId().toString();
        return dataConfig.getBoolean(str_uuid);

    }

    public static void setData(Player player, boolean value) {
        String str_uuid = player.getUniqueId().toString();
        dataConfig.set(str_uuid, value);
        saveData();
    }

    public static void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            getLogger().info("Could not save data.yml file");
        }
    }

    public static Boolean isData(Player player) {

        if (dataConfig.getString(player.getUniqueId().toString()) == null) {
            return false;
        } else {
            return true;
        }

    }
}
