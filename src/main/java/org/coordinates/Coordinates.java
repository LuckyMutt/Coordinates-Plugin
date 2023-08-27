package org.coordinates;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Coordinates extends JavaPlugin implements Listener {

    private static Map<Player, Boolean> coordinatesEnabledMap = new HashMap<>();

    @Override
    public void onEnable() {

        getLogger().info("Coordinates plugin has been enabled");

        Bukkit.getPluginManager().registerEvents(this, this);

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (coordinatesEnabledMap.get(player) == true) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "X : " + ChatColor.RESET + player.getLocation().getBlockX() + ChatColor.GREEN + " Y : " + ChatColor.RESET + player.getLocation().getBlockY() + ChatColor.GREEN + " Z : " + ChatColor.RESET + player.getLocation().getBlockZ()));
                    }
                }
            }
        };

        int taskId = task.runTaskTimerAsynchronously(this, 0, 1).getTaskId();

    }

    @Override
    public void onDisable() {

        coordinatesEnabledMap.clear();
        getLogger().info("Coordinates plugin has been disabled");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("coordinates")) {

            if (!(sender instanceof Player)) {
                return false;
            }

            if (args.length != 0) {
                return false;
            }

            Player player = (Player) sender;
            Boolean coordinatesEnabled = coordinatesEnabledMap.get(player);

            if (coordinatesEnabled == true) {
                coordinatesEnabledMap.put(player, false);
                player.sendMessage(ChatColor.RED + "Coordinates disabled. ");
            } else {
                coordinatesEnabledMap.put(player, true);
                player.sendMessage(ChatColor.GREEN + "Coordinates enabled. ");
            }
        }
        return false;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String PlayerName = event.getPlayer().getName();

        boolean coordinatesEnabled = true;
        coordinatesEnabledMap.put(player, coordinatesEnabled);

    }
}
