package com.MK_20.game.Levels;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Pig;
import com.MK_20.game.Sprites.Slingshot;
import com.MK_20.game.Sprites.Wood;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class Level {
    public Bird currentBird,thrownBird;
    public LevelCreator levelCreator;
    private boolean notYetEnded;


    public Level(LevelCreator l) {
        levelCreator = l;
        setBird(Slingshot.getInstance(0,0));
        thrownBird=null;
        notYetEnded = true;
    }

    public void update(float delta){
        for (int i=0; i<levelCreator.birds.size(); i++){
            levelCreator.birds.get(i).update();
        }
        for (int i=0; i<levelCreator.thrownBirds.size(); i++){
            levelCreator.thrownBirds.get(i).update();
        }
        for (int i=0; i<levelCreator.boxes.size(); i++){
            levelCreator.boxes.get(i).update();
        }
        for (int i=0; i<levelCreator.pigs.size(); i++){
            levelCreator.pigs.get(i).update();
        }

        if (notYetEnded && (levelCreator.birds.isEmpty() || levelCreator.pigs.isEmpty()) ) {
            levelCreator.playScreen.endLevelButton();
            notYetEnded = false;
        }
    }

    public void releaseBird(Vector2 velocity, Slingshot slingshot) {
        if (currentBird != null) {
            Gdx.app.log("Release", "Bird velocity: " + velocity);
            currentBird.body.setType(BodyDef.BodyType.DynamicBody);
            currentBird.body.setLinearVelocity(velocity);
            levelCreator.birds.remove(currentBird);
//            if (!levelCreator.thrownBirds.isEmpty()) {
//                levelCreator.thrownBirds.get(0).destroyAfterDelay(10);
//            }
            levelCreator.thrownBirds.add(currentBird);
            thrownBird=currentBird;
            currentBird = null; //modify to make it the next bird
            if (!levelCreator.birds.isEmpty()) {
                setBirdAfterDelay(slingshot);
            }
        }
    }

    public void handleSpecialFeature(){
        if (thrownBird == null){
            return;
        }
        thrownBird.specialFeature();
    }

    public void setBirdAfterDelay(Slingshot slingshot){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setBird(slingshot);
            }
        }, 1.5f);
    }

    public void setBird(Slingshot slingshot){

        this.currentBird = levelCreator.birds.get(0);
        // Update bird's physics body position
        this.currentBird.body.setTransform(levelCreator.slingshot.top.x, levelCreator.slingshot.top.y, 0);
        // Set bird's body type to static (so it doesn't fall)
        this.currentBird.body.setType(BodyDef.BodyType.StaticBody);
    }

    public void afterSaving(){
        if (levelCreator.birds.isEmpty()){
            currentBird = null;
        }
        else {
            setBird(Slingshot.getInstance(0,0));
        }
        if (levelCreator.thrownBirds.isEmpty()){
            thrownBird = null;
        }
        else {
            thrownBird = levelCreator.thrownBirds.get(levelCreator.thrownBirds.size()-1);
        }
    }

    public void drawAll(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < levelCreator.pigs.size(); i++) {
            levelCreator.pigs.get(i).draw(batch);
        }
        for (int i = 0; i < levelCreator.birds.size(); i++) {
            levelCreator.birds.get(i).draw(batch);
        }
        for (int i = 0; i < levelCreator.thrownBirds.size(); i++) {
            levelCreator.thrownBirds.get(i).draw(batch);
        }
        for (int i = 0; i < levelCreator.boxes.size(); i++) {
            levelCreator.boxes.get(i).draw(batch);
        }

        batch.end();
    }
}
