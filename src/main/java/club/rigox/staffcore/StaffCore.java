package club.rigox.staffcore;

import club.rigox.staffcore.utils.PlayerModel;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

public final class StaffCore extends JavaPlugin {
    public static StaffCore instance;

    private Map<Player, PlayerModel> playerModelMap = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
    }

    public Map<Player, PlayerModel> getModel() {
        return playerModelMap;
    }
}
