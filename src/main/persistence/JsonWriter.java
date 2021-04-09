package persistence;

import model.MonsterGroup;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of Monster to a file
/*  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    This class was adapted from the JsonSerializationDemo provided by UBC, see URL
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of MonsterManager to file
    public void write(MonsterGroup mg) {
        JSONObject json = mg.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of MonsterManager to file
    public void writeFavourites(MonsterGroup mg) {
        JSONObject json = mg.favouritesToJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}