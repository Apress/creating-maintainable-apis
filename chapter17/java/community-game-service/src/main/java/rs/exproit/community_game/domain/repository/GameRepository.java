package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiResourceRepository;
import rs.exproit.community_game.domain.model.Game;
import rs.exproit.community_game.managed.MongoManaged;

@JsonApiResourceRepository(Game.class)
public final class GameRepository extends BaseResourceRepository<Game> {
    @Inject
    public GameRepository(MongoManaged mongoManaged) {
        super(mongoManaged, Game.class);
    }
}
