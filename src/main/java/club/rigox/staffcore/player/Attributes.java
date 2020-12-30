package club.rigox.staffcore.player;

import club.rigox.staffcore.StaffCore;
import org.bukkit.entity.Player;

import java.io.IOException;

import static club.rigox.staffcore.utils.Logger.sendMessage;

public class Attributes {
    private final StaffCore plugin;

    private double health;

    private float experience;

    private int experienceLevel;
    private int food;

    private boolean staffMode;

    private String inventory;
    private String armor;

    public Attributes (StaffCore plugin) {
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
        // If player's health is 0.4
        // set max health. Instead of
        // automatically kill the player.
        if (health <= 0.4) {
            return 20.0;
        }

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
        // If player's health is 0.4
        // set max health. Instead of
        // automatically kill the player.
        if (food <= 0) {
            return 20;
        }
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
     * @param value true / false
     * @param player to perform the action.
     * @throws IOException exception
     */
    public void setStaffMode(boolean value, Player player) throws IOException {
        if (!value) {
            this.staffMode = false;

            plugin.getPlayerUtils().restoreAttributes(player);
            plugin.getMongo().updateStaffToDatabase(player.getUniqueId(), false);
            sendMessage(player, "&8&l* &fStaff mode has been &cdisabled&f!");
            return;
        }
        this.staffMode = true;

        plugin.getPlayerUtils().saveAttributes(player);
        plugin.getMongo().updateStaffToDatabase(player.getUniqueId(), true);
        sendMessage(player, "&8&l* &fStaff mode has been &aenabled&f!");
    }

    /**
     * Getter
     * @return player staff mode
     */
    public boolean isOnStaffMode() {
        return staffMode;
    }
}
