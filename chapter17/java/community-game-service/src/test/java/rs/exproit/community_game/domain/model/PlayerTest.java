package rs.exproit.community_game.domain.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test case for the 'player' resource.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public class PlayerTest {
    @Test
    public final void testConstructor() {
        final Player player = new Player();

        assertNull(player.getRank());
        assertEquals(0, player.getWealth());
        assertEquals(0, player.getHeroes().size());
        assertEquals(0, player.getGames().size());
    }
    
    @Test
    public void testGettingAndSettingWealth() {
        final Player player = new Player();

        player.setWealth(20);
        assertEquals(20, player.getWealth());
    }
    
    @Test
    public void testGettingAndSettingRank() {
        final Player player = new Player();

        player.setRank("Master");
        assertEquals("Master", player.getRank());
    }

    @Test
    public void testGettingAndSettingGamesRelationship() {
        final Game game = new Game();
        final List<Game> games = new ArrayList<>();
        final Player player = new Player();

        games.add(game);
        player.setGames(games);
        assertEquals(games, player.getGames());
    }

    @Test
    public void testGettingAndSettingHeroesRelationship() {
        final Player player = new Player();
        final List<Hero> heroes = new ArrayList<>();
        final Hero hero = new Hero();

        heroes.add(hero);
        player.setHeroes(heroes);
        assertEquals(heroes, player.getHeroes());
    }
}