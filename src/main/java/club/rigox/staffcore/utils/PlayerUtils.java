package club.rigox.staffcore.utils;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.player.Attributes;
import org.bukkit.entity.Player;

public class PlayerUtils {
    private final StaffCore plugin;

    public PlayerUtils (StaffCore plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player save attributes to specified player.
     */
    public void saveAttributes(Player player) {
        Attributes attributes = plugin.getAttributesMap().get(player);

        double playerHealth = player.getHealth();
        int playerFood = player.getFoodLevel();
        float playerExp = player.getExp();
        int playerExpLevel = player.getLevel();

        attributes.setHealth(playerHealth);
        attributes.setFood(playerFood);
        attributes.setExperience(playerExp);
        attributes.setExperienceLevel(playerExpLevel);

        setDefaultAttributes(player);
    }

    /**
     * @param player restore attributes to specified player.
     */
    public void restoreAttributes(Player player) {
        Attributes attributes = plugin.getAttributesMap().get(player);

        double playerHealth = attributes.getHealth();
        int playerFood = attributes.getFood();
        float playerExp = attributes.getExperience();
        int playerExpLevel = attributes.getExperienceLevel();

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
