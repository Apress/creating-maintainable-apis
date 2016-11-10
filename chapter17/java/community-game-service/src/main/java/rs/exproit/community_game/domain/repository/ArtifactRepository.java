package rs.exproit.community_game.domain.repository;

import javax.inject.Inject;

import io.katharsis.repository.annotations.JsonApiResourceRepository;
import rs.exproit.community_game.domain.model.Artifact;
import rs.exproit.community_game.managed.MongoManaged;

@JsonApiResourceRepository(Artifact.class)
public final class ArtifactRepository extends BaseResourceRepository<Artifact> {
    @Inject
    public ArtifactRepository(MongoManaged mongoManaged) {
        super(mongoManaged, Artifact.class);
    }
}
