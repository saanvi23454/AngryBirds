package com.MK_20.game.Tools;

import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Pig;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class SavedData implements Json.Serializable {
    private static final long serialVersionUID = 1L;

    // Fields to save game state
    public ArrayList<Pig> pigs;
    public ArrayList<Bird> birds;
    public int levelIndex;

    // Default constructor (required for Json)
    public SavedData() {
        pigs=new ArrayList<>();
        birds=new ArrayList<>();
    }

    // Json.Serializable methods for custom serialization
    @Override
    public void write(Json json) {
        json.writeValue("pigs", pigs);
        json.writeValue("birds", birds);
        json.writeValue("levelIndex", levelIndex);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        pigs = json.readValue("pigs", ArrayList.class, Pig.class, jsonData);
        birds = json.readValue("birds", ArrayList.class, Bird.class, jsonData);
        levelIndex = json.readValue("levelIndex", int.class, 0, jsonData);
    }
}
