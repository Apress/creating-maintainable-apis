package rs.exproit.community_game.domain.repository;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.*;
import rs.exproit.community_game.domain.model.NamedEntity;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the base relationship repository for source -> destination resources.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 *
 * @param <SourceResource> the origin of the relationship.
 * @param <DestinationResource> the destination of the relationship.
 */
public abstract class BaseRelationshipRepository<SourceResource extends NamedEntity, DestinationResource extends NamedEntity> {
    private static final Logger log = Logger.getLogger(BaseRelationshipRepository.class);
    
    private final BaseResourceRepository<SourceResource> sourceRepository;
    private final BaseResourceRepository<DestinationResource> destinationRepository;

    public BaseRelationshipRepository(BaseResourceRepository<SourceResource> sourceRepository, 
                                      BaseResourceRepository<DestinationResource> destinationRepository) {
        this.sourceRepository = sourceRepository;
        this.destinationRepository = destinationRepository;
    }

    @JsonApiSetRelation
    public void setRelation(SourceResource source, ObjectId destinationId, String fieldName) {
        DestinationResource destination = destinationRepository.findOne(destinationId, null);
        try {
            PropertyUtils.setProperty(source, fieldName, destination);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sourceRepository.save(source);
        log.debug("Relationship is successfully set on (" + source + "," + fieldName + ")");
    }

    @JsonApiSetRelations
    public void setRelations(SourceResource source, Iterable<ObjectId> destinationIds, String fieldName) {
        Iterable<DestinationResource> destinations = destinationRepository.findAll(destinationIds, null);
        try {
            PropertyUtils.setProperty(source, fieldName, destinations);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sourceRepository.save(source);
        log.debug("Relationships are successfully set on (" + source + "," + fieldName + ")");    
    }

    @JsonApiAddRelations
    public void addRelations(SourceResource source, Iterable<ObjectId> destinationIds, String fieldName) {
        List<DestinationResource> newDestinationList = new LinkedList<>();
        Iterable<DestinationResource> destinationsToAdd = destinationRepository.findAll(destinationIds, null);
        destinationsToAdd.forEach(newDestinationList::add);
        try {
            if (PropertyUtils.getProperty(source, fieldName) != null) {
                @SuppressWarnings("unchecked")
                Iterable<DestinationResource> destinations = 
                        (Iterable<DestinationResource>) PropertyUtils.getProperty(source, fieldName);

                destinations.forEach(newDestinationList::add);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            PropertyUtils.setProperty(source, fieldName, newDestinationList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sourceRepository.save(source);
        log.debug("Relationships are successfully added on (" + source + "," + fieldName + ")");    
    }

    @JsonApiRemoveRelations
    public void removeRelations(SourceResource source, Iterable<ObjectId> destinationIds, String fieldName) {
        try {
            if (PropertyUtils.getProperty(source, fieldName) != null) {
                @SuppressWarnings("unchecked")
                Iterable<DestinationResource> destinations = 
                        (Iterable<DestinationResource>) PropertyUtils.getProperty(source, fieldName);
                Iterator<DestinationResource> iterator = destinations.iterator();
                while (iterator.hasNext()) {
                    for (ObjectId destinationIdToRemove : destinationIds) {
                        if (iterator.next().getId().equals(destinationIdToRemove)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
                List<DestinationResource> newDestinationList = new LinkedList<>();
                destinations.forEach(newDestinationList::add);

                PropertyUtils.setProperty(source, fieldName, newDestinationList);
                sourceRepository.save(source);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.debug("Relationships are successfully removed on (" + source + "," + fieldName + ")");    
    }

    @SuppressWarnings("unchecked")
    @JsonApiFindOneTarget
    public DestinationResource findOneTarget(ObjectId sourceId, String fieldName, QueryParams requestParams) {
        SourceResource source = sourceRepository.findOne(sourceId, requestParams);
        try {
            return (DestinationResource) PropertyUtils.getProperty(source, fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @JsonApiFindManyTargets
    public Iterable<DestinationResource> findManyTargets(ObjectId sourceId, String fieldName, QueryParams requestParams) {
        SourceResource source = sourceRepository.findOne(sourceId, requestParams);
        try {
            return (Iterable<DestinationResource>) PropertyUtils.getProperty(source, fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
