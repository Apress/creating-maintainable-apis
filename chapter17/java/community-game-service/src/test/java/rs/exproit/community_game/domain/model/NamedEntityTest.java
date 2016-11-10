package rs.exproit.community_game.domain.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Test;

/**
 * Test case for the base class of all resources.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public class NamedEntityTest {
    private static final class NamedEntityWrapper extends NamedEntity { };
    
    @Test
    public final void testConstructor() {
        final NamedEntity entity = new NamedEntityWrapper();

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getUpdatedAt());
        assertNotNull(entity.getCreatedAt());
        assertTrue(entity.getCreatedAt() instanceof Date);
    }
    
    @Test
    public void testGettingAndSettingId() {
        final NamedEntity entity = new NamedEntityWrapper();
        final ObjectId id = new ObjectId();
        
        entity.setId(id);
        assertEquals(id, entity.getId());
    }

    @Test
    public void testGettingAndSettingName() {
        final NamedEntity entity = new NamedEntityWrapper();
        
        entity.setName("Tester");
        assertEquals("Tester", entity.getName());
    }
    
    @Test
    public void testAlteringLastUpdateTimestamp() {
        final NamedEntity entity = new NamedEntityWrapper();
        final long currentTime = new Date().getTime();
        
        entity.alterUpdatedAt();
        assertNotNull(entity.getUpdatedAt());
        assertTrue(entity.getUpdatedAt() instanceof Date);
        assertTrue(currentTime <= entity.getUpdatedAt().getTime());
    }
}