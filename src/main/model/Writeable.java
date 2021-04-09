package model;

import org.json.JSONObject;

// a class that can be written to a JSON file
public interface Writeable {
    // EFFECTS: returns a JSONObject that is a representation of the object
    public JSONObject toJson();
}
