package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiResourceRepository;
import rs.exproit.community_game.domain.model.Player;
import rs.exproit.community_game.managed.MongoManaged;

@JsonApiResourceRepository(Player.class)
public final class PlayerRepository extends BaseResourceRepository<Player> {
    @Inject
    public PlayerRepository(MongoManaged mongoManaged) {
        super(mongoManaged, Player.class);
    }
}
