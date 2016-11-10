package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiRelationshipRepository;
import rs.exproit.community_game.domain.model.*;

@JsonApiRelationshipRepository(source = Game.class, target = Artifact.class)
public final class GameToArtifactRepository extends BaseRelationshipRepository<Game, Artifact> {
    @Inject
    public GameToArtifactRepository(GameRepository sourceRepository,
            ArtifactRepository destinationRepository) {
        super(sourceRepository, destinationRepository);
    }
}
