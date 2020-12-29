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
//        plugin.getAttributes().saveAttributes(player);
        player.getInventory().clear();
        saveAttributes(player);
    }

    @Subcommand("restore")
    @CommandCompletion("@players")
    @Syntax("<player>")
    public void onRestore(CommandSender sender, Player player) throws IOException {
        plugin.getInventoryUtil().restoreInventory(player);
//        plugin.getAttributes().restoreAttributes(player);
        restoreAttributes(player);
    }

    /**
     * @param player save attributes to specified player.
     */
    public void saveAttributes(Player player) {
        double playerHealth = player.getHealth();
        int playerFood = player.getFoodLevel();

        float playerExp = player.getExp();

        int playerExpLevel = player.getLevel();

        plugin.getAttributesMap().get(player).setHealth(playerHealth);
        plugin.getAttributesMap().get(player).setFood(playerFood);
        plugin.getAttributesMap().get(player).setExperience(playerExp);
        plugin.getAttributesMap().get(player).setExperienceLevel(playerExpLevel);

        setDefaultAttributes(player);
    }

    /**
     * @param player restore attributes to specified player.
     */
    public void restoreAttributes(Player player) {
        double playerHealth = plugin.getAttributesMap().get(player).getHealth();
        int playerFood = plugin.getAttributesMap().get(player).getFood();

        float playerExp = plugin.getAttributesMap().get(player).getExperience();

        int playerExpLevel = plugin.getAttributesMap().get(player).getExperienceLevel();

        player.setHealth(playerHealth);
        player.setFoodLevel(playerFood);
        player.setExp(playerExp);
        player.setLevel(playerExpLevel);
    }

    /**
     * Should be called on the saveAttributes so player it's
     * being cleared after a save.
     *
     * @param player player to apply
     */
    private void setDefaultAttributes(Player player) {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);

    }

}
