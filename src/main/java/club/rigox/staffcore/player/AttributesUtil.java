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
}
