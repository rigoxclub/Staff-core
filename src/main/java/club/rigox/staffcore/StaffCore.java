package club.rigox.staffcore;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffCore extends JavaPlugin {
    public static StaffCore instance;

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
    }

}
