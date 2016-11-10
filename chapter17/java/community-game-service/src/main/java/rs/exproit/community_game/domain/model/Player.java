package rs.exproit.community_game.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

/**
 * Represents a player in a game.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
@Entity("players")
@JsonApiResource(type = "players")
public final class Player extends NamedEntity {
    @Min(value = 0, message = "Wealth cannot be lower than {value}.")
    private int wealth;

    @NotNull(message = "A player must have a rank.")    
    @Pattern(regexp = "Rookie|Normal|Master", 
             message = "A player may be either: Rookie, Normal or Master")   
    private String rank;

    @Reference
    @JsonApiToMany
    private List<Game> games = new ArrayList<>();

    @Reference
    @JsonApiToMany
    private List<Hero> heroes = new ArrayList<>();
    
    public final int getWealth() {
        return wealth;
    }

    public final void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public final String getRank() {
        return rank;
    }

    public final void setRank(String rank) {
        this.rank = rank;
    }

    public final List<Game> getGames() {
        return games;
    }

    public final void setGames(List<Game> games) {
        this.games = games;
    }

    public final List<Hero> getHeroes() {
        return heroes;
    }

    public final void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }
}
