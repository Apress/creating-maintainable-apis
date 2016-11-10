package rs.exproit.community_game.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;

/**
 * Represent a hero in the game, and is controlled by a player.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
@Entity("heroes")
@JsonApiResource(type = "heroes")
public final class Hero extends NamedEntity {
    @Min(value = 1, message = "Attack power cannot be lower than {value}.")
    @Max(value = 1000, message = "Attack power cannot be higher than {value}.")    
    private short attackPower;

    @Min(value = 1, message = "Defense power cannot be lower than {value}.")
    @Max(value = 1000, message = "Defense power cannot be higher than {value}.")    
    private short defensePower;

    @Min(value = 1, message = "Magic power cannot be lower than {value}.")
    @Max(value = 1000, message = "Magic power cannot be higher than {value}.")    
    private short magicPower;

    @Min(value = 1, message = "Health cannot be lower than {value}.")
    @Max(value = 100, message = "Health cannot be higher than {value}.")    
    private short health;

    @NotNull(message = "A hero must have a style.")
    @Pattern(regexp = "Warrior|Wizard", message = "A hero may be either a Warrior or a Wizard")   
    private String style;

    @Min(value = 1, message = "Experience cannot be lower than {value}.")
    @Max(value = 100, message = "Experience cannot be higher than {value}.")    
    private short experience;

    @Reference
    @JsonApiToOne
    private Game game;

    @Reference
    @JsonApiToMany(lazy = false)
    @JsonApiIncludeByDefault
    private List<Artifact> artifacts = new ArrayList<>();
    
    @JsonIgnore
    private boolean available = true;
    
    public final short getAttackPower() {
        return attackPower;
    }
    
    public final void setAttackPower(short attackPower) {
        this.attackPower = attackPower;
    }
    
    public final short getDefensePower() {
        return defensePower;
    }
    
    public final void setDefensePower(short defensePower) {
        this.defensePower = defensePower;
    }
    
    public final short getMagicPower() {
        return magicPower;
    }
    
    public final void setMagicPower(short magicPower) {
        this.magicPower = magicPower;
    }
    
    public final short getHealth() {
        return health;
    }
    
    public final void setHealth(short health) {
        this.health = health;
    }
    
    public final String getStyle() {
        return style;
    }
    
    public final void setStyle(String style) {
        this.style = style;
    }
    
    public final short getExperience() {
        return experience;
    }
    
    public final void setExperience(short experience) {
        this.experience = experience;
    }

    public final Game getGame() {
        return game;
    }

    public final void setGame(Game game) {
        this.game = game;
    }

    public final boolean isAvailable() {
        return available;
    }

    public final void setAvailable(boolean available) {
        this.available = available;
    }

    public final List<Artifact> getArtifacts() {
        return artifacts;
    }

    public final void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
}
