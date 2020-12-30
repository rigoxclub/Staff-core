package club.rigox.staffcore.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.UUID;

import static club.rigox.staffcore.utils.Config.getDBString;
import static club.rigox.staffcore.utils.Logger.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class MongoDB {
    private MongoCollection<Document> playerCollection;
    private MongoClient client;

    public void connect(){
        MongoClientURI uri = new MongoClientURI(String.format("mongodb+srv://%s:%s@%s/admin?retryWrites=true&w=majority",
                getDBString("MONGO.AUTH.USERNAME"),
                getDBString("MONGO.AUTH.PASSWORD"),
                getDBString("MONGO.HOST")));

        client = new MongoClient(uri);

        String databaseString = getDBString("MONGO.DATABASE");
        MongoDatabase database = client.getDatabase(databaseString);
        playerCollection = database.getCollection("players");

    }

    public void close() {
        client.close();
        info("MongoDB connection has been closed!");
    }

    public void storePlayerToDatabase(UUID uuid) {
        Document document = new Document("UUID", uuid.toString());
        playerCollection.insertOne(document);
    }


    public void updateInventoryDatabase(UUID uuid, String contents, String armor) {
        Document document = playerCollection.find(new Document("UUID", uuid.toString())).first();

        // Call the storeInventoryToDatabase method if the UUID doesn't exists on the database
        if (document == null) {
            debug("Storing player on updateInventoryDatabase.");
            storePlayerToDatabase(uuid);
        }

        playerCollection.updateOne(eq("UUID", uuid.toString()),
                combine(set("inventory.contents", contents),
                        set("inventory.armor", armor)));
    }

    public String getInventoryDatabase(UUID uuid, String type) {
        Document document = playerCollection.find(eq("UUID", uuid.toString())).first();
        String inventory = ((Document) document.get("inventory")).getString(type);

        if (inventory == null) {
            error("Player doesn't have an inventory on the database.");
            return null;
        }

        return inventory;
    }

    public void updateAttributesToDatabase(UUID uuid, double health, int food, float exp, int expLevel) {
        Document document = playerCollection.find(new Document("UUID", uuid.toString())).first();

        // Call the storeInventoryToDatabase method if the UUID doesn't exists on the database
        if (document == null) {
            debug("Storing player on updateAttributesToDatabase.");
            storePlayerToDatabase(uuid);
        }

        playerCollection.updateOne(eq("UUID", uuid.toString()),
                combine(set("attributes.health", health),
                        set("attributes.food", food),
                        set("attributes.exp", exp),
                        set("attributes.expLevel", expLevel)));
    }

    public Integer getIntAttributesFromDatabase(UUID uuid, String value) {
        Document document = playerCollection.find(eq("UUID", uuid.toString())).first();
        return ((Document) document.get("attributes")).getInteger(value);
    }

    public Double getDoubleAttributesFromDatabase(UUID uuid, String value) {
        Document document = playerCollection.find(eq("UUID", uuid.toString())).first();
        return ((Document) document.get("attributes")).getDouble(value);
    }

    public void updateStaffToDatabase(UUID uuid, boolean staff) {
        Document document = playerCollection.find(new Document("UUID", uuid.toString())).first();

        // Call the storeInventoryToDatabase method if the UUID doesn't exists on the database
        if (document == null) {
            debug("Player doesn't exists on the collection!");
            return;
        }

        playerCollection.updateOne(eq("UUID", uuid.toString()),
                        set("staff.quitOnStaff", staff));
    }

    public Boolean quitOnStaff(UUID uuid) {
        Document document = playerCollection.find(eq("UUID", uuid.toString())).first();
        Boolean staff = ((Document) document.get("staff")).getBoolean("quitOnStaff");

        if (staff == null) {
            error("Player doesn't have staff boolean on the database.");
            return false;
        }

        return staff;
    }
}