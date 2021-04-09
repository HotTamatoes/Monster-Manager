package model;

import org.json.JSONObject;

import java.util.ArrayList;

// Represents a DND Monster
public class Monster implements Writeable {
    public static final int MAX_ARMOR = 20;
    public static final int MAX_LEVEL = 20;
    public static final int HEALTH_DICE = 9; // 9 is chosen as an average of 8, 10 and 12


    // the two arraylists that contain the info
    private ArrayList<String> nameList;
    private ArrayList<String> typeList;

    // the six classic attributes to a DND entity
    private int str; // strength  (con)
    private int dex; // dexterity (int)
    private int con; // constitution
    private int intel; // intelligence
    private int wis; // wisdom (cha)
    private int cha; // charisma

    //other attributes
    private String name;
    private int level;
    private int armor;
    private int hp; // health

    private String type;

    // set and managed by the MonsterManager but stored by each monster
    private boolean favourite;

    // EFFECTS: generate a random monster with given level and type
    // if level == 0 the monsters level is randomly generated in generateStats
    // if type is "", type is randomly selected from list
    public Monster(ArrayList<String> nameList, ArrayList<String> typeList, int inputLevel, String inputType) {
        this.nameList = nameList;
        this.typeList = typeList;

        level = selectLevel(inputLevel);
        type = selectType(inputType);
        name = selectName();

        generateStats(level);
    }

    // MODIFIES: this
    // EFFECTS: creates a Monster with all of the given stats
    public Monster(ArrayList<String> nameList, ArrayList<String> typeList, int level, String type,
                   String name, int armor, int hp, int str, int dex, int con, int intel, int wis, int cha) {
        this.nameList = nameList;
        this.typeList = typeList;
        this.level = level;
        this.type = type;
        this.name = name;
        this.armor = armor;
        this.hp = hp;
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.intel = intel;
        this.wis = wis;
        this.cha = cha;
    }

    // REQUIRES: inputLevel >= 0 and inputLevel <= 20
    // EFFECTS: if inputLevel != 0, return inputLevel
    // return random level otherwise
    public int selectLevel(int inputLevel) {
        if (inputLevel != 0) {
            return inputLevel;
        } else {
            return (int)(Math.random() * MAX_LEVEL) + 1;
        }
    }

    // REQUIRES: typeList has been initialized and is non-empty
    // EFFECTS: if the inputType is not an empty string, return inputType
    // return a random String in typeList otherwise
    public String selectType(String inputType) {
        if (!inputType.equals("")) {
            System.out.println(inputType);
            return inputType;
        } else {
            return typeList.get((int)(Math.random() * typeList.size()));
        }
    }

    // REQUIRES: nameList has been initialized and is non-empty
    // EFFECTS: chooses a name from the nameList
    public String selectName() {
        return nameList.get((int)(Math.random() * nameList.size()));
    }

    // REQUIRES: 0 <= level <= MAX_LEVEL
    // MODIFIES: this
    // EFFECTS: generates random values for stats based on the monster's level
    // at level 1, the monster has an average stat value of 10 (total addition of 60)
    // at level 20, the monster has an average stat value of 20 (total addition of 120)
    public void generateStats(int level) {

        int modifierPool = (12 + ((60 * (level - 1)) / 19));

        int strAndConPool = (int)(Math.random() * ((1.0 / 3.0) * modifierPool));
        modifierPool -= strAndConPool;

        int dexAndIntelPool = (int)(Math.random() * modifierPool);
        modifierPool -= dexAndIntelPool;

        int wisAndChaPool = modifierPool;
        str = 8 + (int)(0.5 * strAndConPool) + ((int)(Math.random() * 3) - 1); // Half the pool plus one of -1, 0, 1
        strAndConPool = 8 + (strAndConPool - str);
        dex = 8 + (int)(0.5 * dexAndIntelPool) + ((int)(Math.random() * 3) - 1);
        dexAndIntelPool = 8 + (dexAndIntelPool - dex);
        con = 8 + strAndConPool;
        intel = 8 + dexAndIntelPool;
        wis = 8 + (int)(0.5 * wisAndChaPool) + ((int)(Math.random() * 3) - 1);
        wisAndChaPool = 8 + (wisAndChaPool - wis);
        cha = 8 + wisAndChaPool;


        armor = (int)(Math.random() * ((MAX_ARMOR / 2) + 1)) + 10;

        //              Roll dice for bonus health + lowest possible health
        hp = (int)(Math.random() * (HEALTH_DICE * (level - 1)))
                +  (level * (int) Math.floor(((double)con - 10) / 2)) + HEALTH_DICE;
    }

    // MODIFIES: this
    // EFFECT: switches the value of favourite, toggle
    public void toggleFavourite() {
        favourite = !favourite;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("level", level);
        json.put("type", type);
        json.put("name", name);
        json.put("armor", armor);
        json.put("hp", hp);
        json.put("str", str);
        json.put("dex", dex);
        json.put("con", con);
        json.put("int", intel);
        json.put("wis", wis);
        json.put("cha", cha);
        return json;
    }

    @Override
    public String toString() {
        if (favourite) {
            return "!!! " + name + " the " + type + " (level: " + level + ") !!!";
        } else {
            return name + " the " + type + " (level: " + level + ")";
        }
    }


    // GETTERS AND SETTERS

    // EFFECT: returns monster's strength
    public int getStr() {
        return str;
    }

    // EFFECT: returns monster's dexterity
    public int getDex() {
        return dex;
    }

    // EFFECT: returns monster's constitution
    public int getCon() {
        return con;
    }

    // EFFECT: returns monster's intelligence
    public int getIntel() {
        return intel;
    }

    // EFFECT: returns monster's wisdom
    public int getWis() {
        return wis;
    }

    // EFFECT: returns monster's charisma
    public int getCha() {
        return cha;
    }

    // EFFECT: returns monster's name
    public String getName() {
        return name;
    }

    // EFFECT: returns monster's level
    public int getLevel() {
        return level;
    }

    // EFFECT: returns monster's armor class
    public int getArmor() {
        return armor;
    }

    // EFFECT: returns monster's health
    public int getHp() {
        return hp;
    }

    public String getType() {
        return type;
    }

    // EFFECT: returns true if the monster is a favourite
    public boolean isFavourite() {
        return favourite;
    }

    // MODIFIES: this
    // EFFECT: changes monster's strength
    public void setStr(int str) {
        this.str = str;
    }

    // MODIFIES: this
    // EFFECT: changes monster's dexterity
    public void setDex(int dex) {
        this.dex = dex;
    }

    // MODIFIES: this
    // EFFECT: changes monster's constitution
    public void setCon(int con) {
        this.con = con;
    }

    // MODIFIES: this
    // EFFECT: changes monster's intelligence
    public void setIntel(int intel) {
        this.intel = intel;
    }

    // MODIFIES: this
    // EFFECT: changes monster's wisdom
    public void setWis(int wis) {
        this.wis = wis;
    }

    // MODIFIES: this
    // EFFECT: changes monster's charisma
    public void setCha(int cha) {
        this.cha = cha;
    }

    // MODIFIES: this
    // EFFECT: changes monster's name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECT: changes monster's level
    public void setLevel(int level) {
        this.level = level;
    }

    // MODIFIES: this
    // EFFECT: changes monster's armor
    public void setArmor(int armor) {
        this.armor = armor;
    }

    // MODIFIES: this
    // EFFECT: changes monster's health
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setType(String type) {
        this.type = type;
    }

    // MODIFIES: this
    // EFFECT: sets the value of favourite
    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }
}
