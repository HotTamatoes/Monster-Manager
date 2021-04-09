package model;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a collection of monsters
public class MonsterGroup implements Writeable {
    private LinkedList<Monster> monsters;
    private String name;

    // MODIFIES: this
    // EFFECTS: creates an empty MonsterGroup with given name
    public MonsterGroup(String name) {
        monsters = new LinkedList<Monster>();
        this.name = name;
    }

    public void addMonster(Monster mon) {
        monsters.add(mon);
    }

    public Monster getMonster(int index) {
        return monsters.get(index);
    }

    // MODIFIES: this
    // EFFECTS: removes the Monster from the internal list
    public void removeMonster(Monster mon) {
        monsters.remove(mon);
    }

    // MODIFIES: Monster m
    // EFFECTS: calls m's toggleFavourite method
    public void toggleFavourite(Monster m) {
        m.toggleFavourite();
    }

    // EFFECTS: produces a list of all the Monsters in the MonsterGroup
    public LinkedList<Monster> getAllMonsters() {
        return monsters;
    }

    // EFFECTS: produces a list with only the monsters that are made favourite by the user.
    // All other monsters are added as null objects to preserve index
    public LinkedList<Monster> getFavourites() throws NoFavouriteMonstersException {
        LinkedList<Monster> favourites = new LinkedList<Monster>();
        int succeed = 0;
        for (Monster mon: monsters) {
            if (mon.isFavourite()) {
                favourites.add(mon);
                succeed = 1;
            } else {
                favourites.add(null);
            }
        }
        if (succeed == 1) {
            return favourites;
        } else {
            throw new NoFavouriteMonstersException();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all monsters from mon to the monsters list
    public void addMonsterGroup(MonsterGroup mon) {
        monsters.addAll(mon.getAllMonsters());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Monsters", monstersToJson(monsters));
        return json;
    }

    // EFFECTS: generates a JSON Object that is
    public JSONObject favouritesToJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        try {
            json.put("Monsters", monstersToJson(getFavourites()));
        } catch (NoFavouriteMonstersException nfm) {
            json.put("Monsters", new JSONArray());
        }
        return json;
    }

    // EFFECTS: returns Monsters in this MonsterGroup as a JSON array
    private JSONArray monstersToJson(LinkedList<Monster> monsterList) {
        JSONArray jsonArray = new JSONArray();

        for (Monster mon: monsterList) {
            try {
                jsonArray.put(mon.toJson());
            } catch (NullPointerException npe) {
                // Do nothing
            }
        }

        return jsonArray;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
