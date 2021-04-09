package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.Monster;
import model.MonsterGroup;
import org.json.*;

// Represents a reader that reads MonsterGroup from JSON data stored in file
/*  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    This class was adapted from the JsonSerializationDemo provided by UBC, see URL
 */
public class JsonReader {
    private String source;
    private ArrayList<String> nameList;
    private ArrayList<String> typeList;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(ArrayList<String> nameInput, ArrayList<String> typeInput, String source) {
        nameList = nameInput;
        typeList =  typeInput;
        this.source = source;
    }

    // EFFECTS: reads MonsterGroup from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MonsterGroup read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMonsterGroup(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MonsterGroup from JSON object and returns it
    private MonsterGroup parseMonsterGroup(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MonsterGroup mg = new MonsterGroup(name);
        addMonsters(mg, jsonObject);
        return mg;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addMonsters(MonsterGroup mg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Monsters");
        for (Object json : jsonArray) {
            JSONObject nextMonster = (JSONObject) json;
            addMonster(mg, nextMonster);
        }
    }

    // MODIFIES: mg
    // EFFECTS: parses Monster from JSON object and adds it to MonsterGroup
    private void addMonster(MonsterGroup mg, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int armor  = jsonObject.getInt("armor");
        int hp  = jsonObject.getInt("hp");
        int level  = jsonObject.getInt("level");
        int str  = jsonObject.getInt("str");
        int dex = jsonObject.getInt("dex");
        int con = jsonObject.getInt("con");
        int intel = jsonObject.getInt("int");
        int wis = jsonObject.getInt("wis");
        int cha = jsonObject.getInt("cha");
        Monster mon = new Monster(nameList, typeList, level, type, name, armor, hp, str, dex, con, intel, wis, cha);
        mg.addMonster(mon);
    }
}
