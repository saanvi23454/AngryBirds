package com.MK_20.game.Tools;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Levels.Level;
import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Box;
import com.MK_20.game.Sprites.Pig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class Data implements Json.Serializable {
    private static final long serialVersionUID = 1L;

    // Fields to save game state
    public ArrayList<Box> boxes;
    public ArrayList<Bird> birds;
    public ArrayList<Pig> pigs;
    public ArrayList<Bird> thrownBirds;
    public int levelIndex;
    public int totalLevelsTillNow;

    // Default constructor (required for Json)
    public Data(){
        pigs=new ArrayList<>();
        boxes=new ArrayList<>();
        birds=new ArrayList<>();
        thrownBirds=new ArrayList<>();
    }

    public Data(Level level) {
        pigs=level.levelCreator.pigs;
        birds=level.levelCreator.birds;
        boxes=level.levelCreator.boxes;
        thrownBirds=level.levelCreator.thrownBirds;
        levelIndex=AngryBirds.game.currentLevelIndex;
        totalLevelsTillNow=AngryBirds.game.totalLevels;
        try{
            Json json = new Json();
            String savePath = AngryBirds.SAVEPATH;
            Gdx.files.local(savePath).writeString(json.toJson(this), false);
            System.out.println("game saved in: " + savePath);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    // Json.Serializable methods for custom serialization
    @Override
    public void write(Json json) {
        json.writeValue("pigs", pigs);
        json.writeValue("birds", birds);
        json.writeValue("boxes", boxes);
        json.writeValue("thrownBirds", thrownBirds);
        json.writeValue("levelIndex", levelIndex);
        json.writeValue("totalLevelsTillNow", totalLevelsTillNow);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        pigs = json.readValue("pigs", ArrayList.class, Pig.class, jsonData);
        birds = json.readValue("birds", ArrayList.class, Bird.class, jsonData);
        boxes=json.readValue("boxes", ArrayList.class,Box.class, jsonData);
        thrownBirds = json.readValue("thrownBirds", ArrayList.class, Bird.class, jsonData);
        levelIndex = json.readValue("levelIndex", int.class, 0, jsonData);
        totalLevelsTillNow = json.readValue("totalLevelsTillNow", int.class, 0, jsonData);
    }
}
