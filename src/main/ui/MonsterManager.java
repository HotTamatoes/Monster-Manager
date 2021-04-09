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


// SOURCE for names/types: https://www.dndbeyond.com/monsters?

// Represents the text-based UI
public class MonsterManager {
    private static final String JSON_STORE = "./data/monsters.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private MonsterGroup mg;
    private ArrayList<String> namesList;
    private ArrayList<String> typesList;
    private Scanner userInput;

    // MODIFIES: this
    // EFFECTS: creates a new MonsterManager
    public MonsterManager() {
        init();
        mainMethod();
    }

    // MODIFIES: this
    // EFFECTS: initializes the variables in MonsterManager
    public void init() {
        mg = new MonsterGroup("DND Monsters");
        userInput = new Scanner(System.in);

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

    // EFFECTS: takes the user's input for the first action of the code
    public void firstRun() {
        String input;
        System.out.println(" - Enter l to load monsters in from a file");
        System.out.println(" - Enter nothing to create a new monster");
        System.out.print("Enter here: ");
        input = userInput.nextLine();
        input = input.toLowerCase();

        if (input.equals("l")) {
            loadMonsterGroup();
        } else if (input.equals("")) {
            createMonster();
        } else {
            System.out.println("Invalid Input");
            firstRun();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to modify and change the MonsterManager
    public void mainMethod() {
        String input;
        boolean running = true;

        firstRun();

        while (running) {
            System.out.println(" - Enter x to exit");
            System.out.println(" - Enter g to generate a new monster");
            System.out.println(" - Enter g(number) to generate a (number) of random monsters (Ex: g10)");
            System.out.println(" - Enter v to view all the monsters you've created");
            System.out.println(" - Enter s to save all the monsters you've created");
            System.out.println(" - Enter l to load monsters in from a file");
            System.out.print("Enter here: ");
            input = userInput.nextLine();
            input = input.toLowerCase();
            //clearScreen();

            if (input.equals("x")) {
                running = false;
            } else {
                processMain(input);
            }
        }
    }

    // EFFECTS: processes the command from the mainMethod
    public void processMain(String command) {
        if (command.charAt(0) == 'g') {
            generateMonsters(command);
        } else if (command.equals("v")) {
            viewAll();
        } else if (command.equals("s")) {
            saveMonsterGroup();
        } else if (command.equals("l")) {
            loadMonsterGroup();
        } else {
            System.out.println("Invalid input");
        }
    }

    // MODIFIES: this
    // EFFECTS: Collects level and type from the user
    // creates a new monster from input and adds it to the manager
    public void createMonster() {
        int level;
        String type;

        level = getLevelFromUser();
        type = getTypeFromUser();

        mg.addMonster(new Monster(namesList, typesList, level, type));
        System.out.println("Monster Created!");
    }

    // EFFECTS: reads a Monster level in from the user and returns it
    public int getLevelFromUser() {
        int level;
        System.out.println("Creating a new monster!");
        System.out.println("First step is to enter the monster's level");
        System.out.println(" - Enter a level 1-" + Monster.MAX_LEVEL + " to choose that level");
        System.out.println(" - Enter 0 if you want a random level");
        System.out.print("Enter level here: ");
        try {
            level = Integer.parseInt(userInput.nextLine());
            //clearScreen();
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid: not a number");
            return getLevelFromUser();
        }

        if (level < 0 || level > Monster.MAX_LEVEL) {
            System.out.println("Invalid: level outside of range (0, " + Monster.MAX_LEVEL + ")");
            return getLevelFromUser();
        }
        return level;
    }

    // EFFECTS: reads a Monster type in from the user and returns it
    public String getTypeFromUser() {
        String type;
        System.out.println("The next step is to enter the monster's type");
        System.out.println(" - Type in something to set the monster's type");
        System.out.println(" - Type in nothing if you want a randomly generated type");
        System.out.println(" - Type in \"h\" if you want a suggestions for types");
        System.out.print("Enter type here: ");
        type = userInput.nextLine();

        if (type.equals("h")) {
            type = printTypes();
        }

        //clearScreen();
        return type;
    }

    // ADAPTED FROM https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the MonsterGroup to file
    private void saveMonsterGroup() {
        try {
            jsonWriter.open();
            jsonWriter.write(mg);
            jsonWriter.close();
            System.out.println("Saved " + mg.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the favourite monsters to the file
    public void saveFavourites() {
        try {
            jsonWriter.open();
            jsonWriter.writeFavourites(mg);
            jsonWriter.close();
            System.out.println("Saved " + mg.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // ADAPTED FROM https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadMonsterGroup() {
        try {
            mg.addMonsterGroup(jsonReader.read());
            System.out.println("Loaded " + mg.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // REQUIRES: First character of input is a g
    // MODIFIES: this
    // EFFECTS: if input is "g", send the user to create a monster the normal way
    //          if input is g followed by a number (Ex: "g10"), generate that number of random monsters
    public void generateMonsters(String input) {
        int genNum;
        String restInput = input.substring(1);
        if (restInput.isEmpty()) {
            createMonster();
        } else {
            try {
                genNum = Integer.parseInt(restInput);
                for (int i = 0; i < genNum; i++) {
                    mg.addMonster(new Monster(namesList, typesList, 0, ""));
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input");
            }
        }
    }

    // EFFECTS: prints all the possible entries for "type" from the user
    // Then, collects and returns a type from the user
    public String printTypes() {
        int counter = 1;
        for (String type : typesList) {
            System.out.println(counter + ".) " + type);
            counter++;
        }
        System.out.print("Enter type here: ");
        return userInput.nextLine();
    }

    // EFFECTS: prints the monsters onto the screen, favourite monsters are surrounded in !!! (mon) !!!
    public void printMonsters(List<Monster> lom) {
        int count = 0;
        int numMonPrinted = 0;
        for (Monster mon : lom) {
            count++;
            if (mon != null) {
                System.out.println(count + ".) " + mon);
                numMonPrinted++;
            }
        }
        if (numMonPrinted == 0) {
            System.out.println("THERE ARE NO MONSTERS!\n");
        }
    }

    // EFFECTS: displays all created monsters to the user
    // also allows user to select a monster
    public void viewAll() {
        printMonsters(mg.getAllMonsters());

        System.out.println(" - Enter f to show your favourite monsters");
        System.out.println(" - Enter the position number of the monster you would like to select");
        System.out.println(" - Enter 0 to go back");
        System.out.print("Enter position here: ");

        processView();
    }

    // EFFECTS: processes the user's input from the viewMonsters method
    public void processView() {
        int position;
        String output = userInput.nextLine();
        try {
            position = Integer.parseInt(output);
            //clearScreen();

            if (position != 0) {
                selectMonster(mg.getMonster(position - 1));
            }
        } catch (NumberFormatException nfe) {
            if (output.equals("f")) {
                viewFavourites();
            } else {
                System.out.println("Invalid: not a number or f");
                viewAll();
            }
        } catch (IndexOutOfBoundsException ioob) {
            System.out.println("Invalid: not an index");
            viewFavourites();
        }
    }

    // EFFECTS: display all monsters that are marked as favourite monsters
    public void viewFavourites() {
        try {
            printMonsters(mg.getFavourites());
        } catch (NoFavouriteMonstersException nfm) {
            printMonsters(new LinkedList<Monster>());
        }

        System.out.println(" - Enter the position number of the monster you would like to select");
        System.out.println(" - Enter s if you would like to save your favourite monsters");
        System.out.println(" - Enter 0 to go back");
        System.out.print("Enter position here: ");

        processFavourites();
    }

    // EFFECTS: processes the user input from the viewFavourites method
    public void processFavourites() {
        int position;
        String input = userInput.nextLine();
        try {
            position = Integer.parseInt(input);
            //clearScreen();

            if (position != 0) {
                selectMonster(mg.getMonster(position - 1));
            }
        } catch (NumberFormatException e) {
            if (input.equals("s")) {
                saveFavourites();
            } else {
                System.out.println("Invalid: not a number or s");
                viewFavourites();
            }
        } catch (IndexOutOfBoundsException ioob) {
            System.out.println("Invalid: not an index");
            viewFavourites();
        }
    }

    // MODIFIES: Monster mon
    // EFFECTS: displays a selected monster, allowing the user to modify the monster
    public void selectMonster(Monster mon) {
        String input;

        showMonster(mon);

        System.out.println(" - Enter r to refresh the monster's stats");
        System.out.println(" - Enter d to delete the monster");
        System.out.println(" - Enter f to toggle if this monster is a favourite");
        System.out.println(" - Type in the attribute you would like to change (Ex: charisma) (Ex: name)");
        System.out.println(" - Type in nothing to go back");
        System.out.print("Enter here: ");

        input = userInput.nextLine();
        input = input.toLowerCase();
        //clearScreen();

        handleSelection(mon, input);
    }

    // EFFECTS: displays the individual monster in detail
    private void showMonster(Monster mon) {
        System.out.println(mon);
        System.out.printf("%-20s", "Armor Class: " + mon.getArmor());
        System.out.println("Health Points: " + mon.getHp());
        System.out.printf("%-20s", "Strength: " + mon.getStr());
        System.out.println("Intelligence: " + mon.getIntel());
        System.out.printf("%-20s", "Dexterity: " + mon.getDex());
        System.out.println("Wisdom: " + mon.getWis());
        System.out.printf("%-20s", "Constitution: " + mon.getCon());
        System.out.println("Charisma: " + mon.getCha());
        System.out.println();
    }

    // EFFECTS: handles the user's input from the selectMonster method
    public void handleSelection(Monster mon, String input) {
        if (input.equals("d")) {
            mg.removeMonster(mon);
        } else if (input.equals("r")) {
            mon.generateStats(mon.getLevel());
            selectMonster(mon);
        } else if (input.equals("f")) {
            mg.toggleFavourite(mon);
        } else if (input.equals("name") || input.equals("type")) {
            changeString(mon, input);
            selectMonster(mon);
        } else if (!input.equals("")) {
            changeNumber(mon, input);
            selectMonster(mon);
        }
    }

    // REQUIRES: the attribute is one of:
    // - name
    // - type
    // MODIFIES: Monster mon
    // EFFECTS: changes the attribute given in the Monster mon
    public void changeString(Monster mon, String att) {
        if (att.equals("name")) {
            System.out.print("Enter the new name here: ");
            mon.setName(userInput.nextLine());
        } else {
            System.out.print("Enter the new type here: ");
            mon.setType(userInput.nextLine());
        }
    }

    // MODIFIES: Monster mon
    // EFFECTS: changes the attribute given in the Monster mon
    private void changeNumber(Monster mon, String att) {
        if (att.equals("health points") || att.equals("hp")) {
            mon.setHp(readNumber());
        } else if (att.equals("armor class") || att.equals("ac")) {
            mon.setArmor(readNumber());
        } else if (att.equals("level")) {
            int nextLevel = readNumber();
            mon.setLevel(nextLevel);
            mon.generateStats(nextLevel);
        } else {
            changeStat(mon, att);
        }
    }

    // MODIFIES: Monster mon
    // EFFECTS: changes the stat to the given amount
    private void changeStat(Monster mon, String stat) {
        if (stat.equals("strength") || stat.equals("str")) {
            mon.setStr(readNumber());
        } else if (stat.equals("dexterity") || stat.equals("dex")) {
            mon.setDex(readNumber());
        } else if (stat.equals("constitution") || stat.equals("con")) {
            mon.setCon(readNumber());
        } else if (stat.equals("intelligence") || stat.equals("int")) {
            mon.setIntel(readNumber());
        } else if (stat.equals("wisdom") || stat.equals("wis")) {
            mon.setWis(readNumber());
        } else if (stat.equals("charisma") || stat.equals("cha")) {
            mon.setCha(readNumber());
        } else {
            System.out.println("Invalid: entry not recognized");
        }
    }

    // EFFECTS: reads in a number from the user, returning it if possible
    public int readNumber() {
        System.out.print("Enter the new value: ");
        try {
            return Integer.parseInt(userInput.nextLine());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid: not a number");
            return readNumber();
        }
    }
}