package rs.exproit.community_game.managed;

import org.mongodb.morphia.Datastore;

import io.dropwizard.lifecycle.Managed;

/**
 * This is an extended life-cycle interface including one MongoDb specific
 * detail (getting the data store).
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public interface MongoManaged extends Managed {
    Datastore getDatastore(); 
}
