package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Game.class, target = Hero.class)
public final class GameToHeroRepository extends BaseRelationshipRepository<Game, Hero> {
    @Inject
    public GameToHeroRepository(GameRepository sourceRepository,
            HeroRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
