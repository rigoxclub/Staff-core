package club.rigox.staffcore.utils;

import club.rigox.staffcore.StaffCore;
import club.rigox.staffcore.database.MongoDB;
import club.rigox.staffcore.player.Attributes;
import club.rigox.staffcore.player.InventorySerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

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

        saveInventory(player);
        setDefaultAttributes(player);
    }

    public void saveAttributesToDatabase(Player player) {
        Attributes attributes = plugin.getAttributesMap().get(player);

        double health = attributes.getHealth();
        int food = attributes.getFood();
        float exp = attributes.getExperience();
        int expLevel = attributes.getExperienceLevel();

        plugin.getMongo().updateAttributesToDatabase(player.getUniqueId(), health, food, exp, expLevel);
    }

    public void restoreSessionFromQuit(Player player) throws IOException {
        MongoDB mongo = plugin.getMongo();
        Attributes attributes = plugin.getAttributesMap().get(player);

        restoreInventory(player);

        double health = mongo.getDoubleAttributesFromDatabase(player.getUniqueId(), "health");
        int food = mongo.getIntAttributesFromDatabase(player.getUniqueId(), "food");
//      TODO  float exp = (float) mongo.getIntAttributesFromDatabase(player.getUniqueId(), "exp");
        int expLevel = mongo.getIntAttributesFromDatabase(player.getUniqueId(), "expLevel");

        attributes.setHealth(health);
        attributes.setFood(food);
//      TODO  attributes.setExperience(exp);

        attributes.setExperienceLevel(expLevel);
    }

    /**
     * @param player restore attributes to specified player.
     */
    public void restoreAttributes(Player player) throws IOException {
        Attributes attributes = plugin.getAttributesMap().get(player);

        double playerHealth = attributes.getHealth();
        int playerFood = attributes.getFood();
        float playerExp = attributes.getExperience();
        int playerExpLevel = attributes.getExperienceLevel();

        player.setHealth(playerHealth);
        player.setFoodLevel(playerFood);
        player.setExp(playerExp);
        player.setLevel(playerExpLevel);

        restoreInventory(player);
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

    /**
     * Serialize the inventory and send it to
     * the database.
     *
     * @param player to save inventory
     * @throws IllegalStateException exception
     */
    public void saveInventory(Player player) throws IllegalStateException {
        InventorySerializer serializer = plugin.getInventoryUtil();

        UUID uuid = player.getUniqueId();
        String inventory = serializer.toBase64(player.getInventory());
        String armor = serializer.itemStackArrayToBase64(player.getInventory().getArmorContents());

        plugin.getMongo().updateInventoryDatabase(uuid, inventory, armor);
        player.getInventory().clear();
    }

    /**
     * Deserialize the inventory from the
     * database and convert it to a ItemStack[]
     * to set on Player's inventory again.
     *
     * @param player to restore inventory
     * @throws IOException exception
     */
    public void restoreInventory(Player player) throws IOException {
        InventorySerializer serializer = plugin.getInventoryUtil();

        String armorData = plugin.getMongo().getInventoryArmorDatabase(player.getUniqueId());
        String contentsData = plugin.getMongo().getInventoryContentsDatabase(player.getUniqueId());

        ItemStack[] armor = serializer.itemStackArrayFromBase64(armorData);
        ItemStack[] contents = serializer.itemStackArrayFromBase64(contentsData);

        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armor);
    }
}
