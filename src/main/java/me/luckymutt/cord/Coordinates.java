package me.luckymutt.cord;

import me.luckymutt.cord.command.CoordinatesCommand;
import me.luckymutt.cord.listener.PlayerConnectionListener;
import me.luckymutt.cord.listener.PlayerMoveListener;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Coordinates extends JavaPlugin {
    static {
        ConfigurationSerialization.registerClass(CoordinatesPlayerData.class, "CoordinatesPlayer");
    }

    @Override
    public void onEnable() {
        CoordinatesPlayerManager coordinatesPlayerManager = new CoordinatesPlayerManager();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerConnectionListener(coordinatesPlayerManager), this);
        pluginManager.registerEvents(new PlayerMoveListener(coordinatesPlayerManager), this);

        getCommand("coordinates").setExecutor(new CoordinatesCommand(coordinatesPlayerManager));
    }

}
