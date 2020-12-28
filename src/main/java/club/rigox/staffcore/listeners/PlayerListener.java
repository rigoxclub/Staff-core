package club.rigox.staffcore.listeners;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.utils.PlayerModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {
    private final StaffCore plugin;

    public PlayerListener (StaffCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        Player player = e.getPlayer();

        plugin.getModel().put(player, new PlayerModel());
    }
}
