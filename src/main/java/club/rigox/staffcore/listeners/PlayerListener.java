package club.rigox.staffcore.listeners;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.player.Attributes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static club.rigox.staffcore.utils.Logger.debug;

public class PlayerListener implements Listener {
    private final StaffCore plugin;

    public PlayerListener (StaffCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (plugin.getMongo().quitOnStaff(player.getUniqueId())) {
            debug("Player has quit on staff mode");
        }

        plugin.getAttributesMap().put(player, new Attributes(plugin));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        // If player is on Staff Mode and quit, store
        // their attributes to the database.
        if (player.hasPermission("staff.use") && plugin.getAttributesMap().get(player).isOnStaffMode()) {
            plugin.getPlayerUtils().saveAttributesToDatabase(player);
            debug("Saved player on Attributes field!");
        }

        // TODO: Remove player from AttributesMap on leave.
    }
}
