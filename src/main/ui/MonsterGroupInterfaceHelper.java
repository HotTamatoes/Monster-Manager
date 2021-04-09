package ui;

import model.Monster;
import model.MonsterGroup;
import model.NoFavouriteMonstersException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// a class that helps the user interface interact with the monsters
public class MonsterGroupInterfaceHelper {
    private static final String JSON_STORE = "./data/monsters.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private MonsterGroup mg;
    private ArrayList<String> namesList;
    private ArrayList<String> typesList;

    // MODIFIES: this
    // EFFECTS: creates a new MonsterGroupInterfaceHelper
    public MonsterGroupInterfaceHelper() {
        init();
        mg = new MonsterGroup("DND Monsters");
    }

    // MODIFIES: this
    // EFFECTS: initializes the variables in MonsterManager
    public void init() {
        mg = new MonsterGroup("DND Monsters");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(namesList, typesList, JSON_STORE);

        try {
            readIn();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find NamesData.txt or TypesData.txt in data folder");
        }
    }

    // MODIFIES: this
    // EFFECTS: reads the data file containing all the info required for random generation
    public void readIn() throws FileNotFoundException {
        Scanner namesInput = new Scanner(new File("./data/NamesData.txt"));
        Scanner typesInput = new Scanner(new File("./data/TypesData.txt"));
        namesList = new ArrayList<String>();
        typesList = new ArrayList<String>();
        while (namesInput.hasNextLine()) {
            namesList.add(namesInput.nextLine());
        }
        while (typesInput.hasNextLine()) {
            typesList.add(typesInput.nextLine());
        }
    }

    // MODIFIES: JSON_STORE
    // EFFECTS: writes the MonsterGroup to the JSON file
    public void saveMonsters() {
        try {
            jsonWriter.open();
            jsonWriter.write(mg);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: JSON_STORE
    // EFFECTS: Saves only the favourite monsters to the JSON file
    public void saveFavouriteMonsters() {
        try {
            jsonWriter.open();
            jsonWriter.writeFavourites(mg);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadMonsters() {
        try {
            mg.addMonsterGroup(jsonReader.read());
            System.out.println("Loaded " + mg.getName() + " from " + JSON_STORE); // !!!!!
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE); // !!!!!
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new monster in the monster group
    public void addSingle(int level, String type) {
        mg.addMonster(new Monster(namesList, typesList, level, type));
    }

    // MODIFIES: this
    // EFFECTS: creates multiple monsters and adds them to the monster group
    public void addMultiple(int number, int level) {
        for (int i = 0; i < number; i++) {
            mg.addMonster(new Monster(namesList, typesList, level, ""));
        }
    }

    // REQUIRES: no monster is null
    // MODIFIES: this
    // EFFECTS: removes the monsters that are both in lom and mg
    public void delete(List<Monster> lom) {
        for (Monster m : lom) {
            mg.removeMonster(m);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the monster group, effectively wiping all information
    public void deleteAll() {
        mg = new MonsterGroup("DND Monsters");
    }

    // MODIFIES: All Monsters in lom
    // EFFECTS: Calls every Monster's ToggleFavourite Method
    public void favourite(List<Monster> lom) {
        for (Monster m : lom) {
            mg.toggleFavourite(m);
        }
    }

    // EFFECTS: returns a list of all the monsters
    public LinkedList<Monster> getMonsters() {
        return mg.getAllMonsters();
    }

    // EFFECTS: returns a list of only the favourite monsters
    public LinkedList<Monster> getFavouriteMonsters() throws NoFavouriteMonstersException {
        return mg.getFavourites();
    }
}
