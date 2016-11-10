package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Game.class, target = Player.class)
public final class GameToPlayerRepository extends BaseRelationshipRepository<Game, Player> {
    @Inject
    public GameToPlayerRepository(GameRepository sourceRepository,
            PlayerRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
