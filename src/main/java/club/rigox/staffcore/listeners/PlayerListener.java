package club.rigox.staffcore.listeners;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.player.Attributes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private StaffCore plugin;

    public PlayerListener (StaffCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        plugin.getAttributesMap().put(player, new Attributes(plugin));
    }
}
