package persistence;

import model.Monster;
import model.MonsterGroup;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/*  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    This class was adapted from the JsonSerializationDemo provided by UBC, see URL
 */
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    public void testWriterInvalidFile() {
        try {
            MonsterGroup mg = new MonsterGroup("monstahz");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyMonsterGroup() {
        try {
            MonsterGroup mg = new MonsterGroup("monstahz");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMonsterGroup.json");
            writer.open();
            writer.write(mg);
            writer.close();

            JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/testWriterEmptyMonsterGroup.json");
            mg = reader.read();
            assertEquals("monstahz", mg.getName());
            assertEquals(0, mg.getAllMonsters().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralMonsterGroup() {
        try {
            MonsterGroup mg = new MonsterGroup("monstahz");
            mg.addMonster(new Monster(fullNames, fullTypes, 10, "Dinosaur", "Gerton",
                    7, 29, 9, 11, 10, 20, 8, 27));
            mg.addMonster(new Monster(fullNames, fullTypes, 9, "Carnivore", "Zack",
                    20, 150, 10, 11, 12, 13, 14, 15));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMonsterGroup.json");
            writer.open();
            writer.write(mg);
            writer.close();

            JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/testWriterGeneralMonsterGroup.json");
            mg = reader.read();
            assertEquals("monstahz", mg.getName());
            LinkedList<Monster> monsters = mg.getAllMonsters();
            assertEquals(2, monsters.size());
            assertEquals("Gerton", monsters.get(0).getName());
            assertEquals("Zack", monsters.get(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterFavouriteGeneral() {
        try {
            MonsterGroup mg = new MonsterGroup("monstahz");
            mg.addMonster(new Monster(fullNames, fullTypes, 10, "Dinosaur", "Gerton",
                    7, 29, 9, 11, 10, 20, 8, 27));
            mg.addMonster(new Monster(fullNames, fullTypes, 9, "Carnivore", "Zack",
                    20, 150, 10, 11, 12, 13, 14, 15));
            mg.toggleFavourite(mg.getMonster(1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMonsterGroup.json");
            writer.open();
            writer.writeFavourites(mg);
            writer.close();

            JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/testWriterGeneralMonsterGroup.json");
            mg = reader.read();
            assertEquals("monstahz", mg.getName());
            LinkedList<Monster> monsters = mg.getAllMonsters();
            assertEquals(1, monsters.size());
            assertEquals("Zack", monsters.get(0).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterFavouriteNone() {
        try {
            MonsterGroup mg = new MonsterGroup("monstahz");
            mg.addMonster(new Monster(fullNames, fullTypes, 10, "Dinosaur", "Gerton",
                    7, 29, 9, 11, 10, 20, 8, 27));
            mg.addMonster(new Monster(fullNames, fullTypes, 9, "Carnivore", "Zack",
                    20, 150, 10, 11, 12, 13, 14, 15));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMonsterGroup.json");
            writer.open();
            writer.writeFavourites(mg);
            writer.close();

            JsonReader reader = new JsonReader(fullNames, fullTypes, "./data/testWriterGeneralMonsterGroup.json");
            mg = reader.read();
            assertEquals("monstahz", mg.getName());
            LinkedList<Monster> monsters = mg.getAllMonsters();
            assertEquals(0, monsters.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
