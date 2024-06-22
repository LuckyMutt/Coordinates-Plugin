package me.luckymutt.cord.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Configuration {

    private final Plugin plugin;
    private final File file;

    private YamlConfiguration config;

    public Configuration(Plugin plugin, String path) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), path);
    }

    public Configuration copy(String from) {
        if (!file.exists()) {
            try {
                Files.createDirectories(file.getParentFile().toPath());

                try (InputStream in = plugin.getResource("config/" + from)) {
                    if (in == null) throw new IOException("Resource not found: config/" + from);

                    Files.copy(in, file.toPath());
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

        return this;
    }

    public Configuration load() {
        config = YamlConfiguration.loadConfiguration(file);

        config.options().copyDefaults();
        save();

        return this;
    }

    public <T> T get(String path, Class<T> type) {
        Object value = config.get(path);
        if (type.isInstance(value)) {
            return type.cast(value);
        } else {
            throw new RuntimeException("Your requested config value \"" + path + "\" isn't of type " + type.getName());
        }
    }

    public boolean has(String path) {
        return config.contains(path);
    }

    public Configuration set(String path, Object value) {
        config.set(path, value);
        save();

        return this;
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
