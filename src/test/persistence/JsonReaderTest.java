package persistence;

import model.Monster;
import model.MonsterGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    This class was adapted from the JsonSerializationDemo provided by UBC, see URL
 */
class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/noSuchFile.json");
        try {
            MonsterGroup mg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyMonsterGroup() {
        JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/testReaderEmptyMonsterGroup.json");
        try {
            MonsterGroup mg = reader.read();
            assertEquals("", mg.getName());
            assertEquals(0, mg.getAllMonsters().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralMonsterGroup() {
        JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/testReaderGeneralMonsterGroup.json");
        try {
            MonsterGroup mg = reader.read();
            assertEquals("monstahz", mg.getName());
            List<Monster> monsters = mg.getAllMonsters();
            assertEquals(2, monsters.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}