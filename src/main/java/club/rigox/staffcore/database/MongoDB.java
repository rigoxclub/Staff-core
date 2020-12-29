package club.rigox.staffcore.database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.UUID;

import static club.rigox.staffcore.utils.Config.getDBString;
import static club.rigox.staffcore.utils.Logger.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

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

    public void storeInventoryToDatabase(UUID uuid, String inventory, String armor) {
        Document document = new Document("UUID", uuid.toString())
                .append("inventory", new Document("contents", inventory)
                        .append("armor", armor));

        playerCollection.insertOne(document);

    }


    public void updateInventoryDatabase(UUID uuid, String inventory, String armor) {
//        DBObject r = new BasicDBObject("UUID", uuid.toString());
//        DBObject found = playerCollection.findOne(r);
//
//        if (found == null) {
//            debug("Player has been added to the database while being checked.");
//            storeInventoryToDatabase(uuid, inventory, armor);
//            return;
//        }
//

        playerCollection.updateOne(eq("UUID", new ObjectId(uuid.toString())),
                combine(set("inventory.contents", "vamoo"), set("inventory.armor", "vamooo")));
    }
}