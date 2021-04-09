package persistence;

import model.Monster;
import model.MonsterGroup;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected ArrayList<String> fullNames;
    protected ArrayList<String> fullTypes;

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
    }

    protected void checkMonster(String name, int level, Monster monster) {
        assertEquals(name, monster.getName());
        assertEquals(level, monster.getLevel());
    }
}