package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiResourceRepository;
import rs.exproit.community_game.domain.model.Hero;
import rs.exproit.community_game.managed.MongoManaged;

@JsonApiResourceRepository(Hero.class)
public final class HeroRepository extends BaseResourceRepository<Hero> {
    @Inject
    public HeroRepository(MongoManaged mongoManaged) {
        super(mongoManaged, Hero.class);
    }
}
