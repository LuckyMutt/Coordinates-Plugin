package me.luckymutt.cord.command;

import me.luckymutt.cord.CoordinatesPlayerData;
import me.luckymutt.cord.CoordinatesPlayerManager;
import me.luckymutt.cord.config.Configuration;
import me.luckymutt.cord.config.ConfigurationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CoordinatesCommand implements CommandExecutor {

    private final String NO_PERMISSION_MESSAGE;
    private final String ONLY_PLAYER_MESSAGE;
    private final String[] HELP_MESSAGE;

    private final CoordinatesPlayerManager coordinatesPlayerManager;

    public CoordinatesCommand(CoordinatesPlayerManager coordinatesPlayerManager) {
        Configuration mainConfiguration = ConfigurationManager.get()
                .getConfiguration(ConfigurationManager.Config.MAIN);

        NO_PERMISSION_MESSAGE = mainConfiguration.get("command.error.no_permission", String.class);
        ONLY_PLAYER_MESSAGE = mainConfiguration.get("command.error.only_player", String.class);
        HELP_MESSAGE = (String[]) mainConfiguration.get("command.help", List.class).toArray();

        this.coordinatesPlayerManager = coordinatesPlayerManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', ONLY_PLAYER_MESSAGE));
            return true;
        }

        Player player = (Player) commandSender;

        if (
                !player.hasPermission("coordinates.command.*") &&
                !player.hasPermission("coordinates.command")
        ) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NO_PERMISSION_MESSAGE));
            return true;
        }

        if (args.length == 0) {
            commandSender.sendMessage(Arrays.stream(HELP_MESSAGE)
                    .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                    .toArray(String[]::new)
            );
        }

        switch (args[0]) {
            case "toggle":
                if (
                        !player.hasPermission("coordinates.command.*") &&
                        !player.hasPermission("coordinates.command.toggle")
                ) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', NO_PERMISSION_MESSAGE));
                    return true;
                }

                CoordinatesPlayerData playerData = coordinatesPlayerManager.getCoordinatesPlayer(player.getUniqueId());
                if (playerData == null) return true;

                playerData.setEnabled(!playerData.isEnabled());
                return true;
        }

        return true;
    }

}
