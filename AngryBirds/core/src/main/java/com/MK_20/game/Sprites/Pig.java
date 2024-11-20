package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Pig extends Sprite implements Json.Serializable {
    //Transient so that it does not serialize
    public transient World world;
    public transient Body body;
    public transient Texture texture;

    public boolean isDestroyed = false;
    public float destroyTime = 0f;
    public float stateTime = 0f;
    public float currentAlpha = 1f;

    public boolean totallyDestroyed = false;
    public float health;

    public float x, y, width, height, radius; // Serializable fields

    public Pig(){}

    public Pig(World world, Pig p){
        super(new Texture("pig.png"));
        this.x = p.x;
        this.y = p.y;
        this.width = p.width;
        this.height = p.height;
        this.radius = p.radius;
        this.world = world;
        this.totallyDestroyed = p.totallyDestroyed;
        this.health = p.health;
        definePig(x, y, radius);
        setSize(width,height);

    }

    public Pig(World world, float x, float y, float width, float height, float radius) {
        super(new Texture("pig.png"));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.world = world;
        this.health = 4;
        definePig(x, y, radius);
        setSize(width,height);
    }

    public void definePig(float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        fixtureDef.shape = circleShape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        circleShape.dispose(); // Clean up after using the shape
    }

    public void markAsDestroyed() {
        if (isDestroyed) return;
        isDestroyed = true;  // Mark the pig as destroyed
        destroyTime = 0.01f;
    }

    public void update(float delta) {
        if (totallyDestroyed){
            return;
        }
        if (isDestroyed) {
            stateTime += delta;
            System.out.println("State Time: " + stateTime);  // Debugging output to track stateTime
            if (stateTime >= destroyTime) {
                world.destroyBody(body);
                body = null;
                totallyDestroyed = true;
            }
        }
        else {
            this.x = body.getPosition().x;
            this.y = body.getPosition().y;
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (totallyDestroyed){
            return;
        }
        if (isDestroyed) {
            //fade effect
            float alpha = Math.max(0, 1 - stateTime / destroyTime);
            setColor(1f, 1f, 1f, alpha);  // set the alpha value to the sprite's color
            System.out.println("Alpha: " + alpha);  // Debugging output to check alpha value

        } else {
            super.draw(batch);
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("radius", radius);
        json.writeValue("isDestroyed", isDestroyed);
        json.writeValue("destroyTime", destroyTime);
        json.writeValue("stateTime", stateTime);
        json.writeValue("currentAlpha", currentAlpha);
        json.writeValue("totallyDestroyed", totallyDestroyed);
        json.writeValue("health", health);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        try {
            x = json.readValue("x", Float.class, jsonData);
            y = json.readValue("y", Float.class, jsonData);
            width = json.readValue("width", Float.class, jsonData);
            height = json.readValue("height", Float.class, jsonData);
            radius = json.readValue("radius", Float.class, jsonData);
            isDestroyed = json.readValue("isDestroyed", boolean.class, jsonData);
            destroyTime = json.readValue("destroyTime", Float.class, jsonData);
            stateTime = json.readValue("stateTime", Float.class, jsonData);
            currentAlpha = json.readValue("currentAlpha", Float.class, jsonData);
            totallyDestroyed = json.readValue("totallyDestroyed", boolean.class, jsonData);
            health = json.readValue("health", Float.class, jsonData);
        }
        catch (Exception e) {
            System.out.println("Error deserializing pig: "+e.getMessage());
        }
    }
}
