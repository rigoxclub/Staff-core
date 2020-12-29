package club.rigox.staffcore.utils;

import club.rigox.staffcore.StaffCore;
import org.bukkit.entity.Player;

public class PlayerUtils {
    private StaffCore plugin;

    public PlayerUtils (StaffCore plugin) {
        this.plugin = plugin;
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
