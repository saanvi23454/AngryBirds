package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.Gdx;
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
    public Vector2 startPosition;
    private float thickness, dx, dy, length, angle;
    boolean x_fixed, y_fixed;
    float x_fixed_drag, y_fixed_drag;

    private Slingshot(float x, float y) {
        top = new Vector2(x/AngryBirds.PPM,y/AngryBirds.PPM);
        dragPosition = new Vector2(top);
        startPosition = new Vector2(top);
        dragging = false;
        shapeRenderer = new ShapeRenderer();
        thickness = 2f;
        x_fixed = false;
        y_fixed = false;
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
        startPosition.set(dragPosition);
    }

    public Vector2 getDragPosition() {
        return dragPosition;
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
        startPosition.set(top.x, top.y);
    }

    public Vector2 launchVelocity() {
        Vector2 direction = startPosition.cpy().sub(dragPosition);
        direction.y= -direction.y;

        if (direction.x > 60f){
            direction.x = 60f;
            if (!x_fixed) {
                x_fixed = true;
                x_fixed_drag = dragPosition.x;
            }
        }
        else{
            x_fixed = false;
        }
        if (direction.y > 40f){
            direction.y = 40f;
            if (!y_fixed){
                y_fixed = true;
                y_fixed_drag = dragPosition.y;
            }
        }
        else{
            y_fixed = false;
        }

        float x, y;

        if (x_fixed){
            x = top.x - (startPosition.x - x_fixed_drag);
        } else{
            x = top.x - (startPosition.x - dragPosition.x);
        }

        if (y_fixed){
            y = top.y - (y_fixed_drag - startPosition.y);
        } else{
            y = top.y - (dragPosition.y - startPosition.y);
        }

        Bird bird = AngryBirds.game.currentLevel.level.currentBird;
        if (bird != null){
            bird.body.setTransform(x,y,0);
        }

        return direction.scl(2f);
    }

    public void renderTrajectory(Camera camera, Bird currentBird) {
        if (!this.dragging || (currentBird==null)) {
            return;
        }
        Bird bird = AngryBirds.game.currentLevel.level.currentBird;
        Vector2 birdPosition = bird.body.getPosition();

        float startX=birdPosition.x, startY=birdPosition.y;

        Vector2 velocity=launchVelocity();
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (float t = 0; t < 4f; t += 0.2f) {
            float x = startX + (velocity.x * t);
            float y = startY + (velocity.y * t) + (0.5f * -9.8f * t * t);

            if (y < 0) break;
            shapeRenderer.circle(x, y, 2f);
        }

        Gdx.gl.glLineWidth(10);

        // for first line of sling
        dx = birdPosition.x - (top.x-10);
        dy = birdPosition.y - top.y;
        length = (float) Math.sqrt(dx * dx + dy * dy);
        angle = (float) Math.atan2(dy, dx);
        shapeRenderer.rect(top.x-10, top.y, 0, -thickness / 2, length, thickness, 1, 1, angle * 180f / (float) Math.PI);


        //for second line of sling
        dx = birdPosition.x - (top.x+10);
        dy = birdPosition.y - (top.y+10);
        length = (float) Math.sqrt(dx * dx + dy * dy);
        angle = (float) Math.atan2(dy, dx);
        shapeRenderer.rect(top.x + 10, top.y + 10, 0, -thickness / 2, length, thickness, 1, 1, angle * 180f / (float) Math.PI);

        shapeRenderer.end();
    }

    public void dispose(){};


    //for testing purpose
    private Slingshot() {
        top = new Vector2(100,200);
        dragPosition = new Vector2(top);
        startPosition = new Vector2(top);
        dragging = false;
    }
    public static Slingshot getInstance(){
        if (sling == null){
            sling = new Slingshot();
        }
        return sling;
    }
    public void startDrag() {
        dragging = true;
        dragPosition.set(50f,50f);
    }
}
