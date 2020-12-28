package club.rigox.staffcore.utils;

import club.rigox.staffcore.StaffCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static club.rigox.staffcore.utils.Logger.error;
import static club.rigox.staffcore.utils.Logger.warn;

public class Config {
    private final StaffCore plugin;

    public Config(StaffCore plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration createConfig(String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(configName + ".yml", false);
        }

        FileConfiguration cfg = new YamlConfiguration();
        try {
            cfg.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            warn(String.format("A error occurred while copying the config %s.yml to the plugin data folder. Error: %s", configName, e));
            e.printStackTrace();
        }
        return cfg;
    }

    public static String getLangString(String path) {
        if (StaffCore.instance.getLang().getString(path) == null) {
            error(String.format("The following path doesn't exists on Lang.yml! %s, please add it.", path));
            return "&cPlease inform this error to an administrator and let it know to review the console.";
        }
        return StaffCore.instance.getLang().getString(Objects.requireNonNull(path));
    }

    public static String getDBString(String path) {
        if (StaffCore.instance.getDatabase().getString(path) == null) {
            error(String.format("The following path doesn't exists on Database.yml! %s, please add it.", path));
            return "&cPlease inform this error to an administrator and let it know to review the console.";
        }
        return StaffCore.instance.getDatabase().getString(Objects.requireNonNull(path));
    }
}