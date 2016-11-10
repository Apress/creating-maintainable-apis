package rs.exproit.community_game.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

/**
 * Represent a community game, which is a container for items and heroes.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
@Entity("games")
@JsonApiResource(type = "games")
public final class Game extends NamedEntity {  
    @Size(max = 80, message = "Description may not exceed {max} characters.")        
    private String description;
    
    @Min(value = 2, message = "Min. number of players cannot be lower than {value}.")
    private short minPlayers;
    
    @Min(value = 2, message = "Max. number of players cannot be lower than {value}.")
    private short maxPlayers;
    
    @Reference
    @JsonApiToMany(lazy = false)
    private List<Artifact> artifacts = new ArrayList<>();

    @Reference
    @JsonApiToMany(lazy = false)
    private List<Hero> heroes = new ArrayList<>();

    @Reference
    @JsonApiToMany
    private List<Player> players = new ArrayList<>();

    public final String getDescription() {
        return description;
    }
    
    public final void setDescription(String description) {
        this.description = description;
    }

    public final short getMinPlayers() {
        return minPlayers;
    }

    public final void setMinPlayers(short minPlayers) {
        this.minPlayers = minPlayers;
    }

    public final short getMaxPlayers() {
        return maxPlayers;
    }

    public final void setMaxPlayers(short maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public final List<Artifact> getArtifacts() {
        return artifacts;
    }

    public final void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public final List<Hero> getHeroes() {
        return heroes;
    }

    public final void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public final List<Player> getPlayers() {
        return players;
    }

    public final void setPlayers(List<Player> players) {
        this.players = players;
    }  
}
