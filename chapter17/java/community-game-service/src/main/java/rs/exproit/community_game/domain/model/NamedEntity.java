package rs.exproit.community_game.domain.model;

import io.katharsis.resource.annotations.JsonApiId;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is the base class, which defines common properties for all resources.
 * The 'id' is a globally unique identifier of a resource. The 'name' is
 * its human readable counterpart (doesn't need to be unique).
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public abstract class NamedEntity {
    @Id
    @JsonApiId
    private ObjectId id;

    private String name;
    
    @JsonIgnore
    @Version
    private Long version;
        
    @JsonIgnore
    private Date createdAt = new Date();    
    @JsonIgnore
    private Date updatedAt;

    @PrePersist 
    void alterUpdatedAt() { updatedAt = new Date(); }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final Date getCreatedAt() {
        return createdAt;
    }

    public final Date getUpdatedAt() {
        return updatedAt;
    }
}
