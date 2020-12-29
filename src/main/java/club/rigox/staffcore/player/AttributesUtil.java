package club.rigox.staffcore.player;

import club.rigox.staffcore.StaffCore;
import org.bukkit.entity.Player;

public class AttributesUtil {
    private final StaffCore plugin;

    private double health;

    private float experience;

    private int experienceLevel;
    private int food;

    public AttributesUtil (StaffCore plugin) {
        this.plugin = plugin;
    }

    /**
     * @param value set the player's health value.
     */
    public void setHealth(double value) {
        this.health = value;
    }

    /**
     * @return player's heart.
     */
    public double getHealth() {
        return health;
    }

    /**
     * @param value set the player's food value.
     */
    public void setFood(int value) {
        this.food = value;
    }

    /**
     * @return player's food level.
     */
    public int getFood() {
        return food;
    }

    /**
     * @param value set the player's experience float.
     */
    public void setExperience(float value) {
        this.experience = value;
    }

    /**
     * @return player's experience float.
     */
    public float getExperience() {
        return experience;
    }


    /**
     * @param value set the player's experience level integer.
     */
    public void setExperienceLevel(int value) {
        this.experienceLevel = value;
    }

    /**
     * @return player's experience level integer.
     */
    public int getExperienceLevel() {
        return experienceLevel;
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
        double playerHealth = getHealth();
        int playerFood = getFood();

        float playerExp = getExperience();

        int playerExpLevel = getExperienceLevel();

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
