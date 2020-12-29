package club.rigox.staffcore.database;

import com.mongodb.*;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static club.rigox.staffcore.utils.Config.getDBString;
import static club.rigox.staffcore.utils.Logger.*;

public class MongoOld {
    private DBCollection playerCollection;
    private MongoClient client;

    public void connect(){
        MongoClientURI uri = new MongoClientURI(String.format("mongodb+srv://%s:%s@%s/admin?retryWrites=true&w=majority",
                getDBString("MONGO.AUTH.USERNAME"),
                getDBString("MONGO.AUTH.PASSWORD"),
                getDBString("MONGO.HOST")));

        client = new MongoClient(uri);

        String databaseString = getDBString("MONGO.DATABASE");
        DB database = client.getDB(databaseString);
        playerCollection = database.getCollection("players");
    }

    public void close() {
        client.close();
        info("MongoDB connection has been closed!");
    }

//    public String[] getDatabaseInventory(UUID uuid) {
//        DBObject r = new BasicDBObject("UUID", uuid.toString());
//        DBObject found = playerCollection.findOne(r);
//
//        if (found == null) {
//            error("Player is not on the database");
//            return null;
//        }
//
//        return (String[]) found.get("inventory");
//    }


    public void storeInventoryToDatabase(UUID uuid, String inventory, String armor) {
        DBObject object = new BasicDBObject("UUID", uuid.toString());

        object.put("inventory", inventory);
        object.put("armor", armor);
        playerCollection.insert(object);
    }

    public void updateInventoryDatabase(UUID uuid, String inventory, String armor) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = playerCollection.findOne(r);

        if (found == null) {
            debug("Player has been added to the database while being checked.");
            storeInventoryToDatabase(uuid, inventory, armor);
            return;
        }

        BasicDBObject set1 = new BasicDBObject("$set", r);

        set1.append("$set", new BasicDBObject("inventory", "alo"));
        playerCollection.update(found, set1);

        BasicDBObject set = new BasicDBObject("$set", r);

        set.append("$set", new BasicDBObject("armor", "eeeeee"));
        playerCollection.update(found, set);
    }

}