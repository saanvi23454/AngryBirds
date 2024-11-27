package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Slingshot{

    private static Slingshot sling;
    private ShapeRenderer shapeRenderer;
    private Vector2 dragPosition;
    public boolean dragging;
    public Vector2 top;

    private Slingshot(float x, float y) {
        top = new Vector2(x/AngryBirds.PPM,y/AngryBirds.PPM);
        dragPosition = new Vector2(top);
        dragging = false;
        shapeRenderer = new ShapeRenderer();
    }

    public static Slingshot getInstance(float x, float y){
        if (sling == null){
            sling = new Slingshot( x, y);
        }
        return sling;
    }

    public void startDrag(float x, float y) {
        dragging = true;
        dragPosition.set(x/AngryBirds.PPM, y/AngryBirds.PPM);
    }

    public void renderTrajectory(Camera camera, Bird currentBird) {
        if (!this.dragging || (currentBird==null)) {
            return;
        }
        float startX=top.x, startY=top.y;
        System.out.println("Top: "+top.x+" "+top.y);
        Vector2 velocity=launchVelocity();
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (float t = 0; t < 5f; t += 0.1f) {
            float x = startX + (velocity.x * t);
            float y = startY + (velocity.y * t) + (0.5f * -9.8f * t * t);

            if (y < 0) break;
            shapeRenderer.circle(x, y, 2f);
        }
        shapeRenderer.end();
    }

    public void updateDrag(float x, float y) {
        if (dragging) {
            dragPosition.set(x/AngryBirds.PPM, y/AngryBirds.PPM);
            //Gdx.app.log("Slingshot", "Dragging at: " + x + ", " + y);
        }
    }

    public void release() {
        dragging = false;
        dragPosition.set(top.x, top.y);
    }

    public Vector2 launchVelocity() {
        Vector2 direction = top.cpy().sub(dragPosition);
        direction.y= -direction.y;
        //direction.y *= (1.5f);
        return direction.scl(1f);
    }

    public void dispose(){};
}