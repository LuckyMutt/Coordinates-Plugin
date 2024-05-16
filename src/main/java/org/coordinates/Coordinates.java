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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Coordinates extends JavaPlugin implements Listener {

    private static Map<Player, Boolean> coordinatesEnabledMap = new HashMap<>();
    private DataManager dm;
    private ConfigManager cm;

    private Boolean default_value;

    @Override
    public void onEnable() {

        //bstats
        int pluginId = 20194;
        Metrics metrics = new Metrics(this, pluginId);

        //load config
        dm.setup();
        cm.setup();


        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Coordinates plugin has been enabled");

    }

    @Override
    public void onDisable() {

        //saveConfig();
        dm.saveData();
        cm.saveConfig();

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
                dm.setData(player, false);
                player.sendMessage(ChatColor.RED + "Coordinates disabled. ");
            } else {
                coordinatesEnabledMap.put(player, true);
                dm.setData(player, true);
                player.sendMessage(ChatColor.BLACK + "Coordinates enabled. ");
            }
        }
        return false;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        //If there is no data about player in the config file
        if (dm.isData(player) == false) {
            dm.setData(player, cm.getConfig("DefaultValue"));
        }

        boolean coordinatesEnabled = dm.getData(player);

        coordinatesEnabledMap.put(player, coordinatesEnabled);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (coordinatesEnabledMap.get(player) == true) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "X : " + ChatColor.RESET + player.getLocation().getBlockX() + ChatColor.GREEN + " Y : " + ChatColor.RESET + player.getLocation().getBlockY() + ChatColor.GREEN + " Z : " + ChatColor.RESET + player.getLocation().getBlockZ()));
        }
    }
}
