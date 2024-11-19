package com.MK_20.game.Levels;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Pig;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class Level {
    public ArrayList<Pig> pigs;
    public ArrayList<Bird> birds;

    public Level() {
        pigs = new ArrayList<>();
        birds = new ArrayList<>();
    }

    public void update(float delta){
        for (int i=0; i<pigs.size(); i++){
            pigs.get(i).update(delta);
        }
        for (int i=0; i<birds.size(); i++){
            birds.get(i).update(delta);
        }
    }

    public void drawAll(SpriteBatch batch){
        batch.begin();
        for (int i=0; i<pigs.size(); i++){
            pigs.get(i).draw(batch);
        }
        for (int i=0; i<birds.size(); i++){
            birds.get(i).draw(batch);
        }
        batch.end();
    }
}
