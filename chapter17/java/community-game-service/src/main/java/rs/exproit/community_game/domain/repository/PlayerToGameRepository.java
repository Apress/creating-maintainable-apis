package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Player.class, target = Game.class)
public final class PlayerToGameRepository extends BaseRelationshipRepository<Player, Game> {
    @Inject
    public PlayerToGameRepository(PlayerRepository sourceRepository,
            GameRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
