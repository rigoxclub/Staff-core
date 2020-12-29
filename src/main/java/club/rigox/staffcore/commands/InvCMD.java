package club.rigox.staffcore.commands;

import club.rigox.staffcore.StaffCore;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

@CommandAlias("inventory")
public class InvCMD extends BaseCommand {
    private final StaffCore plugin;

    public InvCMD (StaffCore plugin) {
        this.plugin = plugin;
    }

    @Subcommand("save")
    @CommandCompletion("@players")
    @Syntax("<player>")
    public void onSave(CommandSender sender, Player player) {
        plugin.getInventoryUtil().saveInventory(player);
        player.getInventory().clear();
    }

    @Subcommand("restore")
    @CommandCompletion("@players")
    @Syntax("<player>")
    public void onRestore(CommandSender sender, Player player) throws IOException {
        plugin.getInventoryUtil().restoreInventory(player);
    }

}
