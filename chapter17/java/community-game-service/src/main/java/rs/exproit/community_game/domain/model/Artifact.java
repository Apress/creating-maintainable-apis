package rs.exproit.community_game.domain.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

/**
 * Represent an item belonging to a game, which may be collected by a hero.
 * A weapon or a health pack may be owned by a hero. A treasure is accrued
 * by a player.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
@Entity("artifacts")
@JsonApiResource(type = "artifacts")
public final class Artifact extends NamedEntity {
    @Size(max = 80, message = "Description may not exceed {max} characters.")    
    private String description;

    @Min(value = 1, message = "Value cannot be lower than {value}.")
    @Max(value = 100, message = "Value cannot be higher than {value}.")
    private short value;

    @NotNull(message = "An partifact must have some purpose.")        
    @Pattern(regexp = "Defense|Attack|Magic|Health|Treasure", 
             message = "An artifact's purpose may be: Defense or Attack or Magic or Health or Treasure")   
    private String purpose;
    
    @Reference
    @JsonApiToOne
    private Game game;
    
    @JsonIgnore 
    private boolean available = true;

    public final String getDescription() {
        return description;
    }
    
    public final void setDescription(String description) {
        this.description = description;
    }
    
    public final String getPurpose() {
        return purpose;
    }
    
    public final void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public final Game getGame() {
        return game;
    }

    public final void setGame(Game game) {
        this.game = game;
    }

    public final short getValue() {
        return value;
    }

    public final void setValue(short value) {
        this.value = value;
    }

    public final boolean isAvailable() {
        return available;
    }

    public final void setAvailable(boolean available) {
        this.available = available;
    }
}
