package club.rigox.staffcore.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.UUID;
import java.util.function.Consumer;

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

    public void storeInventoryToDatabase(UUID uuid, String contents, String armor) {
        Document document = new Document("UUID", uuid.toString())
                .append("inventory", new Document("contents", contents)
                        .append("armor", armor));

        playerCollection.insertOne(document);
    }


    public void updateInventoryDatabase(UUID uuid, String contents, String armor) {
        Document document = playerCollection.find(new Document("UUID", uuid.toString())).first();

        // Call the storeInventoryToDatabase method if the UUID doesn't exists on the database
        if (document == null) {
            debug("Storing player on updateInventoryDatabase.");
            storeInventoryToDatabase(uuid, contents, armor);
            return;
        }

        playerCollection.updateOne(eq("UUID", uuid.toString()),
                combine(set("inventory.contents", contents),
                        set("inventory.armor", armor)));
    }

    public String getInventoryContentsDatabase(UUID uuid) {
        Document document = playerCollection.find(eq("UUID", uuid.toString())).first();
        String contents = ((Document) document.get("inventory")).getString("contents");

        if (contents == null) {
            error("Player doesn't have an inventory on the database.");
            return null;
        }

        return contents;
    }

    public String getInventoryArmorDatabase(UUID uuid) {
        Document document = playerCollection.find(eq("UUID", uuid.toString())).first();
        String armor = ((Document) document.get("inventory")).getString("armor");

        if (armor == null) {
            error("Player doesn't have an inventory armor on the database.");
            return null;
        }

        return armor;
    }
}