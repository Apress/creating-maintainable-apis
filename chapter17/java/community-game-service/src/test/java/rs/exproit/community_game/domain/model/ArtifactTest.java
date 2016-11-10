package rs.exproit.community_game.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test case for the 'artifact' resource.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public class ArtifactTest {
    @Test
    public final void testConstructor() {
        final Artifact artifact = new Artifact();

        assertNull(artifact.getDescription());
        assertNull(artifact.getPurpose());
        assertNull(artifact.getGame());
        assertEquals(0, artifact.getValue());
        assertTrue(artifact.isAvailable());
    }
    
    @Test
    public void testGettingAndSettingDescription() {
        final Artifact artifact = new Artifact();

        artifact.setDescription("Test artifact");
        assertEquals("Test artifact", artifact.getDescription());
    }

    @Test
    public void testGettingAndSettingPurpose() {
        final Artifact artifact = new Artifact();

        artifact.setPurpose("Attack");
        assertEquals("Attack", artifact.getPurpose());
    }
    
    @Test
    public void testGettingAndSettingValue() {
        final Artifact artifact = new Artifact();

        artifact.setValue((short) 1);
        assertEquals(1, artifact.getValue());
    }

    @Test
    public void testGettingAndSettingAvailable() {
        final Artifact artifact = new Artifact();

        artifact.setAvailable(false);
        assertFalse(artifact.isAvailable());
    }

    @Test
    public void testGettingAndSettingGameRelationship() {
        final Artifact artifact = new Artifact();
        final Game game = new Game();

        artifact.setGame(game);
        assertSame(game, artifact.getGame());
    }
}