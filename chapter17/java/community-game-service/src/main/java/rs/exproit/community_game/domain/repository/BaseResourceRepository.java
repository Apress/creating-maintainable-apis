package rs.exproit.community_game.domain.repository;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.*;
import io.katharsis.resource.exception.ResourceNotFoundException;
import io.katharsis.response.MetaInformation;
import jersey.repackaged.com.google.common.collect.Lists;
import rs.exproit.community_game.domain.model.NamedEntity;
import rs.exproit.community_game.managed.MongoManaged;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

/**
 * Base repository implementation to perform CRUD operations on resources.
 * Each concrete repository is extended from this class, and provides only
 * a type specific constructor.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 * 
 * @param <Resource> the concrete resource type (Artifact, Player, etc.).
 */
public abstract class BaseResourceRepository<Resource extends NamedEntity> {
    private static final Logger log = Logger.getLogger(BaseResourceRepository.class);
    
    private final Datastore datastore;
    private final Class<Resource> targetClass;

    public BaseResourceRepository(MongoManaged mongoManaged, Class<Resource> targetClass) {
        datastore = mongoManaged.getDatastore();
        this.targetClass = targetClass;
    }

    @JsonApiSave
    public Resource save(Resource entity) {
        Key<Resource> saveKey = datastore.save(entity);
        log.debug("Resource is successfully saved: " + entity);
        return datastore.getByKey(targetClass, saveKey);
    }

    @JsonApiFindOne
    public Resource findOne(ObjectId id, QueryParams requestParams) {
        Resource namedEntity = datastore.get(targetClass, id);
        if (namedEntity == null) {
            throw new ResourceNotFoundException("Cannot find a resource with id: " + id);
        }
        return namedEntity;
    }

    @JsonApiFindAll
    public Iterable<Resource> findAll(QueryParams requestParams) {
        return datastore.find(targetClass);
    }

    @JsonApiFindAllWithIds
    public Iterable<Resource> findAll(Iterable<ObjectId> ids, QueryParams requestParams) {
        return datastore.get(targetClass, ids);
    }

    @JsonApiDelete
    public void delete(ObjectId id) {
        datastore.delete(targetClass, id);
        log.debug("Resource with id=" + id + " is successfully deleted.");
    }
    
    @JsonApiMeta
    public MetaInformation getMetaInformation(Iterable<Resource> resources) {
        final List<Resource> resourceList = Lists.newArrayList(resources);
        
        if (resourceList.size() == 1) {
            final Resource primaryResource = resourceList.get(0);
            return new MetaInformation() {                
                @SuppressWarnings("unused")
                public final Date createdAt = primaryResource.getCreatedAt();
                @SuppressWarnings("unused")
                public final Date updatedAt = primaryResource.getUpdatedAt();
            };
        } else {
            return new MetaInformation() {
                @SuppressWarnings("unused")
                public final int count = resourceList.size();
            };
        }
    }
}