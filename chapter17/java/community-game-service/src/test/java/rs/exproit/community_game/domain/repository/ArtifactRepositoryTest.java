package rs.exproit.community_game.domain.repository;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.katharsis.resource.exception.ResourceNotFoundException;
import rs.exproit.community_game.domain.model.Artifact;

import javax.validation.ConstraintViolationException;

/**
 * Integration test for the 'artifact' resource repository. This test
 * exercises all resource repositories, as they are just wrappers 
 * around the base resource repository class.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public class ArtifactRepositoryTest {    
    private static final MockedMongoManagedImpl mongoManaged = new MockedMongoManagedImpl();
    private static ArtifactRepository repo;
    
    @BeforeClass
    public static void setupRepository() {
        repo = new ArtifactRepository(mongoManaged);
    }
    
    @Before
    public void dropDatabase() {
        mongoManaged.getFongo().dropDatabase(MockedMongoManagedImpl.TEST_DB_NAME);
    }
    
    @Test(expected = ConstraintViolationException.class)
    public final void testConstraintValidation() throws Exception {
        final Artifact artifact = new Artifact();

        artifact.setDescription("This is a correct description");
        artifact.setPurpose("Attack");
        artifact.setValue((short) -1);
        repo.save(artifact);  
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void testSearchingForNonexistentArtifact() {
        repo.findOne(new ObjectId(), null);
    }
    
    @Test
    public void testSavingAndReadingArtifact() {
        final Artifact origArtifact = new Artifact();
        origArtifact.setDescription("This is a correct description");
        origArtifact.setPurpose("Attack");
        origArtifact.setValue((short) 2);

        final Artifact savedArtifact = repo.save(origArtifact);  

        assertEquals(origArtifact.getId(), savedArtifact.getId());
        assertEquals(origArtifact.getDescription(), savedArtifact.getDescription());
        assertEquals(origArtifact.getPurpose(), savedArtifact.getPurpose());
        
        final Artifact readArtifact = repo.findOne(origArtifact.getId(), null);
        
        assertEquals(origArtifact.getId(), readArtifact.getId());
        assertEquals(origArtifact.getDescription(), readArtifact.getDescription());
        assertEquals(origArtifact.getPurpose(), readArtifact.getPurpose());        
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testSavingDeletingAndReadingArtifact() {
        final Artifact origArtifact = new Artifact();
        origArtifact.setDescription("This is a correct description");
        origArtifact.setPurpose("Attack");
        origArtifact.setValue((short) 2);

        repo.save(origArtifact);
        repo.findOne(origArtifact.getId(), null);
        repo.delete(origArtifact.getId());
        repo.findOne(origArtifact.getId(), null);
    }
}
