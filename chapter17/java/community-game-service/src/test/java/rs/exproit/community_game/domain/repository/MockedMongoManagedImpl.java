package rs.exproit.community_game.domain.repository;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.ValidationExtension;

import com.github.fakemongo.Fongo;

import rs.exproit.community_game.managed.MongoManaged;

/**
 * This is the mocked wrapper around the Mongo database. This
 * class should be used in all repository integration tests.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
final class MockedMongoManagedImpl implements MongoManaged {
    static final String TEST_DB_NAME = "test_community_game";
    private final Datastore datastore;
    private final Fongo fongo;

    MockedMongoManagedImpl() {
        fongo = new Fongo(TEST_DB_NAME);
        Morphia morphia = new Morphia();
        new ValidationExtension(morphia);
        datastore = morphia.createDatastore(fongo.getMongo(), TEST_DB_NAME);
    }

    public void start() throws Exception {
        throw new UnsupportedOperationException();
    }

    public void stop() throws Exception {
        throw new UnsupportedOperationException();
    }

    public Datastore getDatastore() {
        return datastore;
    }
    
    Fongo getFongo() {
        return fongo;
    }
}
