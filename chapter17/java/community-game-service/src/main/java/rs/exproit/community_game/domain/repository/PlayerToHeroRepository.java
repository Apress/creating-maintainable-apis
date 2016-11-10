package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Player.class, target = Hero.class)
public final class PlayerToHeroRepository extends BaseRelationshipRepository<Player, Hero> {
    @Inject
    public PlayerToHeroRepository(PlayerRepository sourceRepository,
            HeroRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
