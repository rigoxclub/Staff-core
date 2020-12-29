package club.rigox.staffcore.commands;

import club.rigox.staffcore.StaffCore;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

import static club.rigox.staffcore.utils.Logger.debug;

@CommandAlias("inventory")
public class InvCMD extends BaseCommand {
    private final StaffCore plugin;

    public InvCMD (StaffCore plugin) {
        this.plugin = plugin;
    }

    @Subcommand("save")
    @CommandCompletion("@players")
    @Syntax("<player>")
    public void onSave(Player player) {
        plugin.getInventoryUtil().saveInventory(player);
        player.getInventory().clear();
    }

    @Subcommand("restore")
    @CommandCompletion("@players")
    @Syntax("<player>")
    public void onRestore(Player player) throws IOException {
        plugin.getInventoryUtil().restoreInventory(player);
    }

}
