package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
abstract
import java.util.ArrayList;

import static model.Monster.*;
import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {
    Monster randomMonster;
    ArrayList<String> fullNames;
    ArrayList<String> fullTypes;
    ArrayList<String> emptyNames;
    ArrayList<String> emptyTypes;

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
        emptyNames = new ArrayList<String>();
        emptyTypes = new ArrayList<String>();
    }

/*    public void readFilesIn() throws FileNotFoundException {
        File namesFile = new File(".\\data\\NamesData.txt");
        File typesFile = new File(".\\data\\TypesData.txt");
        namesScanner = new Scanner(namesFile);
        typesScanner = new Scanner(typesFile);
        while (namesScanner.hasNextLine()) {
            fullNames.add(namesScanner.nextLine());
        }
        while (typesScanner.hasNextLine()) {
            fullTypes.add(typesScanner.nextLine());
        }
    }*/

    @Test
    public void testBasicConstructorAllParams() {
        Monster zack = new Monster(fullNames, fullTypes, 9, "Carnivore", "Zack",
                20, 150, 10, 11, 12, 13, 14, 15);

        assertEquals(10, zack.getStr());
        assertEquals(11, zack.getDex());
        assertEquals(12, zack.getCon());
        assertEquals(13, zack.getIntel());
        assertEquals(14, zack.getWis());
        assertEquals(15, zack.getCha());
        assertEquals(9, zack.getLevel());
        assertEquals(20, zack.getArmor());
        assertEquals(150, zack.getHp());
        assertEquals("Carnivore", zack.getType());
        assertEquals("Zack", zack.getName());
    }

    @Test
    public void testToggleFavouriteFalse() {
        randomMonster.setFavourite(false);
        assertFalse(randomMonster.isFavourite());

        randomMonster.toggleFavourite();
        assertTrue(randomMonster.isFavourite());
    }

    @Test
    public void testToggleFavouriteTrue() {
        randomMonster.setFavourite(true);
        assertTrue(randomMonster.isFavourite());

        randomMonster.toggleFavourite();
        assertFalse(randomMonster.isFavourite());
    }

    @Test
    public void testSelectLevelZero() {
        int randomLevel = randomMonster.selectLevel(0);
        assertTrue(randomLevel <= MAX_LEVEL);
    }

    @Test
    public void testSelectLevelLow() {
        int lowLevel = randomMonster.selectLevel(1);
        assertEquals(1, lowLevel);
    }

    @Test
    public void testSelectLevelMiddle() {
        int middleLevel = randomMonster.selectLevel(5);
        assertEquals(5, middleLevel);
    }

    @Test
    public void testSelectLevelHigh() {
        int highLevel = randomMonster.selectLevel(20);
        assertEquals(20, highLevel);
    }

    @Test
    public void testSelectTypeEmpty() {
        String str = randomMonster.selectType("");
        assertTrue(fullTypes.contains(str));
    }

    @Test
    public void testSelectTypeGiven() {
        String str = randomMonster.selectType("Wolf");
        assertEquals("Wolf", str);
    }

    @Test
    public void testGenerateStatsLowestLevel() {
        randomMonster.generateStats(1);

        assertEquals(60, randomMonster.getStr() + randomMonster.getDex() + randomMonster.getCon() +
                randomMonster.getIntel() + randomMonster.getWis() + randomMonster.getCha());

        assertNotEquals("", randomMonster.getName());

        int armor = randomMonster.getArmor();
        assertTrue((10 <= armor && armor <= MAX_ARMOR));

        // Max HP is if the dice roll max every time
        int hp = randomMonster.getHp();
        assertTrue((0 < hp && hp <= HEALTH_DICE + ((randomMonster.getCon() - 10) / 2)));
    }

    @Test
    public void testGenerateStatsRandomLevel() {
        randomMonster.generateStats(0);

        int level = randomMonster.getLevel();
        assertTrue((0 < level && level <= MAX_LEVEL));

        assertTrue((randomMonster.getStr() + randomMonster.getDex() + randomMonster.getCon() +
                randomMonster.getIntel() + randomMonster.getWis() + randomMonster.getCha())
                < (int)(60 + ((60 * (level - 1)) / 19)));

        assertNotEquals("", randomMonster.getName());

        int armor = randomMonster.getArmor();
        assertTrue((10 <= armor && armor <= MAX_ARMOR));

        // Max HP is if the dice roll max every time
        int hp = randomMonster.getHp();
        assertTrue((0 < hp && hp <= (HEALTH_DICE * level) + (((randomMonster.getCon() - 10) / 2) * level)));

    }

    @Test
    public void testGenerateStatsHighestLevel() {
        randomMonster.generateStats(20);

        assertEquals(120, randomMonster.getStr() + randomMonster.getDex() + randomMonster.getCon() +
                randomMonster.getIntel() + randomMonster.getWis() + randomMonster.getCha());

        assertNotEquals("", randomMonster.getName());

        int armor = randomMonster.getArmor();
        assertTrue((10 <= armor && armor <= MAX_ARMOR));

        // Max HP is if the dice roll max every time
        int hp = randomMonster.getHp();
        assertTrue((0 < hp && hp <= (HEALTH_DICE * 20) + (((randomMonster.getCon() - 10) / 2) * 20)));
    }

    @Test
    public void testGenerateStatsMiddleLevel() {
        randomMonster.generateStats(7);

        assertEquals(78, randomMonster.getStr() + randomMonster.getDex() + randomMonster.getCon() +
                randomMonster.getIntel() + randomMonster.getWis() + randomMonster.getCha());

        assertNotEquals("", randomMonster.getName());

        int armor = randomMonster.getArmor();
        assertTrue((10 <= armor && armor <= MAX_ARMOR));

        // Max HP is if the dice roll max every time
        int hp = randomMonster.getHp();
        assertTrue((0 < hp && hp <= (HEALTH_DICE * 7) + (((randomMonster.getCon() - 10) / 2) * 7)));
    }

    @Test
    public void testConstructorLimitedSelection() {
        ArrayList<String> demoNames = new ArrayList<String>();
        demoNames.add("Charlie");
        ArrayList<String> demoTypes = new ArrayList<String>();
        demoTypes.add("Chicken");
        Monster charlieTheChicken = new Monster(demoNames, demoTypes, 1, "");

        assertEquals("Charlie", charlieTheChicken.getName());
        assertEquals("Chicken", charlieTheChicken.getType());
        assertFalse(charlieTheChicken.isFavourite());
        assertEquals(1, charlieTheChicken.getLevel());
    }

    @Test
    public void testConstructorFullParams() {
        ArrayList<String> demoNames = new ArrayList<String>();
        demoNames.add("Charlie");
        ArrayList<String> demoTypes = new ArrayList<String>();
        demoTypes.add("Chicken");

        Monster charlieTheChicken = new Monster(demoNames, demoTypes, 1, "Rooster");

        assertEquals("Charlie", charlieTheChicken.getName());
        assertEquals("Rooster", charlieTheChicken.getType());
        assertFalse(charlieTheChicken.isFavourite());
    }

    @Test
    public void testSettersAttributes() {
        randomMonster.setStr(1);
        randomMonster.setDex(2);
        randomMonster.setCon(3);
        randomMonster.setIntel(4);
        randomMonster.setWis(5);
        randomMonster.setCha(6);

        assertEquals(1, randomMonster.getStr());
        assertEquals(2, randomMonster.getDex());
        assertEquals(3, randomMonster.getCon());
        assertEquals(4, randomMonster.getIntel());
        assertEquals(5, randomMonster.getWis());
        assertEquals(6, randomMonster.getCha());
    }

    @Test
    public void testSettersOtherValues() {
        randomMonster.setArmor(20);
        randomMonster.setLevel(1);
        randomMonster.setHp(5);
        randomMonster.setType("Fox");
        randomMonster.setName("Bananas");

        assertEquals(20, randomMonster.getArmor());
        assertEquals(1, randomMonster.getLevel());
        assertEquals(5, randomMonster.getHp());
        assertEquals("Fox", randomMonster.getType());
        assertEquals("Bananas", randomMonster.getName());
    }

    @Test
    public void testToStringNotFavourite() {
        randomMonster.setName("chuck");
        randomMonster.setType("cheese");
        randomMonster.setLevel(1);
        randomMonster.setFavourite(false);
        String str = randomMonster.toString();

        assertEquals("chuck the cheese (level: 1)", str);
    }

    @Test
    public void testToStringFavourite() {
        randomMonster.setName("chuck");
        randomMonster.setType("cheese");
        randomMonster.setLevel(1);
        randomMonster.setFavourite(true);
        String str = randomMonster.toString();

        assertEquals("!!! chuck the cheese (level: 1) !!!", str);
    }

    @Test
    public void testGetName() {
        randomMonster.setName("Fred");

        assertEquals("Fred", randomMonster.getName());
    }
}