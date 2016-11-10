package rs.exproit.community_game.managed;

import com.mongodb.MongoClient;
import rs.exproit.community_game.DropwizardConfiguration.MongoConfiguration;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.ValidationExtension;

public final class MongoManagedImpl implements MongoManaged {
    private final MongoClient mongoClient;
    private final Datastore datastore;

    public MongoManagedImpl(MongoConfiguration mongoConfig) throws Exception {
        mongoClient = new MongoClient(mongoConfig.host, mongoConfig.port);
        Morphia morphia = new Morphia();
        new ValidationExtension(morphia);
        datastore = morphia.createDatastore(mongoClient, mongoConfig.db);
    }
    
    public Datastore getDatastore() {
        return datastore;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        mongoClient.close();
    }
}
