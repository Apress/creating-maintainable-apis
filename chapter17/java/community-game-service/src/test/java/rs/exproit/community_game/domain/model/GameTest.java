package rs.exproit.community_game.domain.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test case for the 'game' resource.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public class GameTest {
    @Test
    public final void testConstructor() {
        final Game game = new Game();

        assertNull(game.getDescription());
        assertEquals(0, game.getMinPlayers());
        assertEquals(0, game.getMaxPlayers());
        assertEquals(0, game.getPlayers().size());
        assertEquals(0, game.getHeroes().size());
        assertEquals(0, game.getArtifacts().size());
    }
    
    @Test
    public void testGettingAndSettingDescription() {
        final Game game = new Game();

        game.setDescription("Test game");
        assertEquals("Test game", game.getDescription());
    }

    @Test
    public void testGettingAndSettingMinPlayers() {
        final Game game = new Game();

        game.setMinPlayers((short) 2);
        assertEquals(2, game.getMinPlayers());
    }
    
    @Test
    public void testGettingAndSettingMaxPlayers() {
        final Game game = new Game();

        game.setMaxPlayers((short) 4);
        assertEquals(4, game.getMaxPlayers());
    }

    @Test
    public void testGettingAndSettingPlayersRelationship() {
        final Game game = new Game();
        final List<Player> players = new ArrayList<>();
        final Player player = new Player();

        players.add(player);
        game.setPlayers(players);
        assertEquals(players, game.getPlayers());
    }

    @Test
    public void testGettingAndSettingHeroesRelationship() {
        final Game game = new Game();
        final List<Hero> heroes = new ArrayList<>();
        final Hero hero = new Hero();

        heroes.add(hero);
        game.setHeroes(heroes);
        assertEquals(heroes, game.getHeroes());
    }

    @Test
    public void testGettingAndSettingArtifactsRelationship() {
        final Game game = new Game();
        final List<Artifact> artifacts = new ArrayList<>();
        final Artifact artifact = new Artifact();

        artifacts.add(artifact);
        game.setArtifacts(artifacts);
        assertEquals(artifacts, game.getArtifacts());
    }
}