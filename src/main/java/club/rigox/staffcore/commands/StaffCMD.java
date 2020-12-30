package club.rigox.staffcore.commands;

import club.rigox.staffcore.StaffCore;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static club.rigox.staffcore.utils.Logger.sendMessage;

@CommandAlias("staff")
public class StaffCMD extends BaseCommand {
    private final StaffCore plugin;

    public StaffCMD(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("staff.use")
    public void onStaff(CommandSender sender) throws IOException {
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;

        if (plugin.getAttributesMap().get(player).isOnStaffMode()) {
            plugin.getAttributesMap().get(player).setStaffMode(false, player);
            return;
        }

        plugin.getAttributesMap().get(player).setStaffMode(true, player);
    }
}
