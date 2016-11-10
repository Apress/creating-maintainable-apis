package rs.exproit.community_game.domain.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test case for the 'hero' resource.
 * 
 * @author Ervin Varga
 * @since 1.0.0
 */
public class HeroTest {
    @Test
    public final void testConstructor() {
        final Hero hero = new Hero();

        assertNull(hero.getStyle());
        assertEquals(0, hero.getAttackPower());
        assertEquals(0, hero.getDefensePower());
        assertEquals(0, hero.getMagicPower());
        assertEquals(0, hero.getHealth());
        assertEquals(0, hero.getExperience());
        assertEquals(0, hero.getArtifacts().size());
        assertNull(hero.getGame());
    }
    
    @Test
    public void testGettingAndSettingAttackPower() {
        final Hero hero = new Hero();

        hero.setAttackPower((short) 20);
        assertEquals(20, hero.getAttackPower());
    }
    
    @Test
    public void testGettingAndSettingDefensePower() {
        final Hero hero = new Hero();

        hero.setDefensePower((short) 30);
        assertEquals(30, hero.getDefensePower());
    }

    @Test
    public void testGettingAndSettingMagicPower() {
        final Hero hero = new Hero();

        hero.setMagicPower((short) 40);
        assertEquals(40, hero.getMagicPower());
    }

    @Test
    public void testGettingAndSettingHealth() {
        final Hero hero = new Hero();

        hero.setHealth((short) 90);
        assertEquals(90, hero.getHealth());
    }

    @Test
    public void testGettingAndSettingExperience() {
        final Hero hero = new Hero();

        hero.setExperience((short) 60);
        assertEquals(60, hero.getExperience());
    }

    @Test
    public void testGettingAndSettingStyle() {
        final Hero hero = new Hero();

        hero.setStyle("Warrior");
        assertEquals("Warrior", hero.getStyle());
    }

    @Test
    public void testGettingAndSettingArtifactsRelationship() {
        final Hero hero = new Hero();
        final List<Artifact> artifacts = new ArrayList<>();
        final Artifact artifact = new Artifact();

        artifacts.add(artifact);
        hero.setArtifacts(artifacts);
        assertEquals(artifacts, hero.getArtifacts());
    }

    @Test
    public void testGettingAndSettingGameRelationship() {
        final Hero hero = new Hero();
        final Game game = new Game();

        hero.setGame(game);
        assertSame(game, hero.getGame());
    }
}