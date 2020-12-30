package club.rigox.staffcore.listeners;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.database.MongoDB;
import club.rigox.staffcore.player.Attributes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

import static club.rigox.staffcore.utils.Logger.debug;

public class PlayerListener implements Listener {
    private final StaffCore plugin;

    public PlayerListener (StaffCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {
        Player player = e.getPlayer();
        MongoDB mongo = plugin.getMongo();

        plugin.getAttributesMap().put(player, new Attributes(plugin));

        if (mongo.quitOnStaff(player.getUniqueId())) {
            plugin.getPlayerUtils().restoreSessionFromQuit(player);
            plugin.getAttributesMap().get(player).setStaffMode(true, player);
        }
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
