package me.luckymutt.cord.listener;

import me.luckymutt.cord.CoordinatesPlayerData;
import me.luckymutt.cord.CoordinatesPlayerManager;
import me.luckymutt.cord.config.Configuration;
import me.luckymutt.cord.config.ConfigurationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    private final Configuration playerDataConfiguration;
    private final CoordinatesPlayerManager coordinatesPlayerManager;

    public PlayerConnectionListener(
            CoordinatesPlayerManager coordinatesPlayerManager
    ) {
        this.playerDataConfiguration = ConfigurationManager.get()
                .getConfiguration(ConfigurationManager.Config.PLAYER_DATA);

        this.coordinatesPlayerManager = coordinatesPlayerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!player.hasPlayedBefore() || !playerDataConfiguration.has(uuid.toString())) {
            playerDataConfiguration.set(uuid.toString(), CoordinatesPlayerData.createDefault());
        }

        CoordinatesPlayerData playerData = playerDataConfiguration.get(uuid.toString(), CoordinatesPlayerData.class);
        coordinatesPlayerManager.addCoordinatesPlayer(uuid, playerData);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        CoordinatesPlayerData playerData = coordinatesPlayerManager.getCoordinatesPlayer(uuid);

        if (playerData == null) return;

        playerDataConfiguration.set(uuid.toString(), playerData);
        coordinatesPlayerManager.removeCoordinatesPlayer(uuid);
    }
}
