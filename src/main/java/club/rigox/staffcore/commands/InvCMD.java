package club.rigox.staffcore.commands;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.player.InventoryUtil;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static club.rigox.staffcore.utils.Logger.debug;

@CommandAlias("inventory")
public class InvCMD extends BaseCommand {
    private final StaffCore plugin;

    public InvCMD (StaffCore plugin) {
        this.plugin = plugin;
    }

    @Subcommand("save")
    public void onSave(CommandSender sender, Player player) {
        plugin.getInventoryUtil().playerInventoryToBase64(player);
        sender.sendMessage("Ok");
    }

//    @Subcommand("restore")
//    public void onRestore(CommandSender sender, Player player) {
//        String[] dbInventory = plugin.getMongo().getDatabaseInventory(player.getUniqueId());
//        InventoryUtil.fromBase64(plugin.getMongo().getDatabaseInventory(player.getUniqueId()));
//    }

}
