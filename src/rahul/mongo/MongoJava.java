package rahul.mongo;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoJava {

    public static void main(String[] args) {
        try {

            // Connect to MongoDB Server on localhost, port 27017 (default)
            final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

            // Connect to Database "PokemonGo"
            final MongoDatabase database = mongoClient.getDatabase("PokemonGo");
            System.out.println("Successful database connection established. \n");

            //Insert a document into the "pokemon" collection.
            MongoCollection<Document> collection = database.getCollection("pokemon");

            // Delete the collection and start fresh
            collection.drop();



            //Create Documents

            Document Bulbasaur Document();
            Document Charizard Document();
            Document Pikachu Document();


            //Adding info to the documents

            Bulbasaur.append("_id",1)
                    .append("pokemonName","Bulbasaur")
                    .append("HP","45")
                    .append("attack","49")
                    .append("Def","49")
                    .append("Speed","45");

            Charizard.append("_id",2)
                    .append("pokemonName","Charizard")
                    .append("HP","78")
                    .append("attack","84")
                    .append("Def","78")
                    .append("Speed","100");

            Pikachu.append("_id",3)
                    .append("pokemonName","Pikachu")
                    .append("HP","35")
                    .append("attack","50")
                    .append("Def","40")
                    .append("Speed","90");


            //Add Documents to the collection

            try {
                collection.insertOne(Bulbasaur);
                collection.insertOne(Charizard);
                collection.insertOne(Pikachu);
                System.out.println("Successfully inserted documents. \n");
            } catch (MongoWriteException mwe) {
                if (mwe.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                    System.out.println("Document with that id already exists");
                }
            }

            // Basic data on collection
            System.out.println("Collection size: " + collection.count() + " documents. \n");

            // Create and insert multiple documents
            List<Document> documents = new ArrayList<Document>();
            for (int i = 3; i < 51; i++) {
                documents.add(new Document ("_id", i)
                        .append("pokemonName", "")
                        .append("HP", "")
                        .append("attack", "")
                        .append("Def","")
                        .append("Speed","")
                );
            }
            collection.insertMany(documents);

            // Basic data on collection
            System.out.println("Collection size: " + collection.count() + " documents. \n");



            // Update a document
            // print the second document before update.
            Document second = collection.find(Filters.eq("_id", 2)).first();
            System.out.println("Original second document:");
            System.out.println(second.toJson());

            collection.updateOne(new Document("_id", 2),
                    new Document("$set", new Document("pokemonName", "Raichu")
                            .append("HP", "60")
                            .append("attack", "90")
                            .append("Def","55")
                            .append("Speed","110")
                    )
            );

            System.out.println("\nUpdated second document:");
            Document dilbert = collection.find(Filters.eq("_id", 2)).first();
            System.out.println(dilbert.toJson());

            // Find and print ALL documents in the collection
            System.out.println("Print the documents.");

            MongoCursor<Document> cursor = collection.find().iterator();
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next().toJson());
                }

            } finally {
                cursor.close();
            }

            //Delete data
            System.out.println("\nDelete documents with an id greater than or equal to 3.");
            collection.deleteMany(Filters.gte("_id", 3));

            // Find and print ALL documents in the collection
            System.out.println("\nPrint all documents.");

            MongoCursor<Document> cursor2 = collection.find().iterator();
            try {
                while (cursor2.hasNext()) {
                    System.out.println(cursor2.next().toJson());
                }

            } finally {
                cursor2.close();
            }

        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }
}