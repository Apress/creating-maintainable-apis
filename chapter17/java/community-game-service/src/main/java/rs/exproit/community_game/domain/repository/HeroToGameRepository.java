package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Hero.class, target = Game.class)
public final class HeroToGameRepository extends BaseRelationshipRepository<Hero, Game> {
    @Inject
    public HeroToGameRepository(HeroRepository sourceRepository,
            GameRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
