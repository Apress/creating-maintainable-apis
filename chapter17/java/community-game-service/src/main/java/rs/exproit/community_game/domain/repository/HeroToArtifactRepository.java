package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Hero.class, target = Artifact.class)
public final class HeroToArtifactRepository extends BaseRelationshipRepository<Hero, Artifact> {
    @Inject
    public HeroToArtifactRepository(HeroRepository sourceRepository,
            ArtifactRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
