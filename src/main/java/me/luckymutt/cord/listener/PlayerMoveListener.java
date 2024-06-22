package me.luckymutt.cord.listener;

import me.luckymutt.cord.CoordinatesPlayerData;
import me.luckymutt.cord.CoordinatesPlayerManager;
import me.luckymutt.cord.config.Configuration;
import me.luckymutt.cord.config.ConfigurationManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final String COORDINATE_MESSAGE;

    private final CoordinatesPlayerManager playerManager;

    public PlayerMoveListener(CoordinatesPlayerManager playerManager) {
        Configuration mainConfig = ConfigurationManager.get()
                .getConfiguration(ConfigurationManager.Config.MAIN);
        COORDINATE_MESSAGE = mainConfig.get("actionbar_coordinates_message", String.class);

        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("coordinates.show")) return;

        Location playerLocation = player.getLocation();
        CoordinatesPlayerData playerData = playerManager.getCoordinatesPlayer(player.getUniqueId());

        if(playerData == null || !playerData.isEnabled()) return;

        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacy(
                        ChatColor.translateAlternateColorCodes('&', COORDINATE_MESSAGE
                                .replaceAll("\\{world}", playerLocation.getWorld().getName())
                                .replaceAll("\\{x}", String.valueOf(playerLocation.getBlockX()))
                                .replaceAll("\\{y}", String.valueOf(playerLocation.getBlockY()))
                                .replaceAll("\\{z}", String.valueOf(playerLocation.getBlockZ()))
                        )
                )
        );
    }
}
