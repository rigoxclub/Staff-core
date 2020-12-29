package club.rigox.staffcore;

import club.rigox.staffcore.commands.InvCMD;
import club.rigox.staffcore.database.MongoDB;
import club.rigox.staffcore.listeners.PlayerListener;
import club.rigox.staffcore.player.AttributesUtil;
import club.rigox.staffcore.player.InventoryUtil;
import club.rigox.staffcore.utils.Config;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

public final class StaffCore extends JavaPlugin {
    public static StaffCore instance;

    private MongoDB mongo;
    private InventoryUtil inventoryUtil;

    private FileConfiguration lang;
    private FileConfiguration database;

    private AttributesUtil attributesUtil;
    private Map<Player, AttributesUtil> attributesMap = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        mongo = new MongoDB();
        inventoryUtil = new InventoryUtil(this);

        loadConfiguration();
        mongo.connect();
        registerCommands();
        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
        mongo.close();
    }

    public void loadConfiguration() {
        Config config = new Config(this);
        lang = config.createConfig("lang");
        database = config.createConfig("database");
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("brigadier");
        manager.registerCommand(new InvCMD(this));
    }

    public FileConfiguration getLang() {
        return lang;
    }

    public FileConfiguration getDatabase() {
        return database;
    }

    public MongoDB getMongo() {
        return mongo;
    }

    public InventoryUtil getInventoryUtil() {
        return inventoryUtil;
    }

    public AttributesUtil getAttributes() {
        return attributesUtil;
    }
    public Map<Player, AttributesUtil> getAttributesMap() {
        return attributesMap;
    }
}
