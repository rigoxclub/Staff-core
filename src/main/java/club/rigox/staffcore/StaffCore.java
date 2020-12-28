package club.rigox.staffcore;

import club.rigox.staffcore.utils.Config;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffCore extends JavaPlugin {
    public static StaffCore instance;

    private FileConfiguration lang;

    @Override
    public void onEnable() {
        instance = this;

        loadConfiguration();
    }

    public void loadConfiguration() {
        Config config = new Config(this);
        lang = config.createConfig("lang");
    }

    public FileConfiguration getLang() {
        return lang;
    }
}
