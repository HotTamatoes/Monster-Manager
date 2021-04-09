package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static model.Monster.*;
import static org.junit.jupiter.api.Assertions.*;

public class MonsterGroupTest {
    MonsterGroup mg;
    Monster randomMonster;
    Monster monster1;
    Monster monster2;
    ArrayList<String> fullNames;
    ArrayList<String> fullTypes;

    @BeforeEach
    public void setUpTests() {
        fullNames = new ArrayList<String>();
        fullTypes = new ArrayList<String>();

        fullNames.add("Aarakocra");
        fullNames.add("Abishai");
        fullNames.add("Aboleth");
        fullNames.add("Acererak");
        fullNames.add("Aeorian Absorber");
        fullNames.add("Akro");
        fullNames.add("Aldani");
        fullNames.add("Ahmaergo");

        fullTypes.add("Abberation");
        fullTypes.add("Animal");
        fullTypes.add("Basilisk");
        fullTypes.add("Beast");
        fullTypes.add("Caveling");
        fullTypes.add("Celestial");

        randomMonster = new Monster( fullNames, fullTypes, 0, "");
        monster1 = new Monster( fullNames, fullTypes, 0, "");
        monster2 = new Monster( fullNames, fullTypes, 0, "");
        mg = new MonsterGroup("Test");
    }

    @Test
    public void testAddMonster() {
        assertEquals(0, mg.getAllMonsters().size());

        mg.addMonster(randomMonster);
        assertEquals(1, mg.getAllMonsters().size());
        assertEquals(randomMonster, mg.getMonster(0));

        mg.addMonster(monster1);
        assertEquals(2, mg.getAllMonsters().size());
        assertEquals(monster1, mg.getMonster(1));
    }

    @Test
    public void testRemoveMonster() {
        mg.addMonster(randomMonster);
        assertEquals(1, mg.getAllMonsters().size());

        mg.removeMonster(randomMonster);
        assertEquals(0, mg.getAllMonsters().size());
    }

    @Test
    public void testAddMonsterGroup() {
        mg.addMonster(randomMonster);

        MonsterGroup monGroup = new MonsterGroup("Quiz");
        monGroup.addMonster(monster1);
        monGroup.addMonster(monster2);

        assertEquals(1, mg.getAllMonsters().size());
        assertEquals(2, monGroup.getAllMonsters().size());

        mg.addMonsterGroup(monGroup);
        assertEquals(3, mg.getAllMonsters().size());
        assertEquals(randomMonster, mg.getMonster(0));
        assertEquals(monster1, mg.getMonster(1));
        assertEquals(monster2, mg.getMonster(2));
    }

    @Test
    public void testToggleFavourite() {
        mg.addMonster(randomMonster);
        assertFalse(randomMonster.isFavourite());

        mg.toggleFavourite(randomMonster);
        assertTrue(randomMonster.isFavourite());
    }

    @Test
    public void testSetName() {
        mg.setName("monstahz");
        assertEquals("monstahz", mg.getName());
    }

    @Test
    public void testGetFavouritesWith() {
        mg.addMonster(randomMonster);
        mg.addMonster(monster1);
        mg.addMonster(monster2);
        mg.toggleFavourite(monster1);

        assertFalse(randomMonster.isFavourite());
        assertTrue(monster1.isFavourite());
        assertFalse(monster2.isFavourite());

        try {
            LinkedList<Monster> favList = mg.getFavourites();
            assertEquals(null, favList.get(0));
            assertEquals(monster1, favList.get(1));
            assertEquals(null, favList.get(2));
            assertEquals(3, favList.size());
        } catch (NoFavouriteMonstersException nfm) {
            fail();
        }
    }

    @Test
    public void testGetFavoritesWithoutAny() {
        LinkedList<Monster> favList = new LinkedList<Monster>();
        mg.addMonster(randomMonster);
        mg.addMonster(monster1);
        mg.addMonster(monster2);

        assertFalse(randomMonster.isFavourite());
        assertFalse(monster1.isFavourite());
        assertFalse(monster2.isFavourite());

        try {
            favList = mg.getFavourites();
            fail();
        } catch (NoFavouriteMonstersException nfm) {
            // Succeed!
        }
    }
}
