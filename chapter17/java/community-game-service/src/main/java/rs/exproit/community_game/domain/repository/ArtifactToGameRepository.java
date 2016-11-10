package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Artifact.class, target = Game.class)
public final class ArtifactToGameRepository extends BaseRelationshipRepository<Artifact, Game> {
    @Inject
    public ArtifactToGameRepository(ArtifactRepository sourceRepository,
            GameRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
