package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class Box extends Sprite implements Json.Serializable {

    public transient World world;
    public transient Body body;
    public transient Texture boxTexture;

    public float x, y, width, height, side; // Serializable fields
    public String texturePath;

    public boolean isDestroyed = false;
    public float destroyTime = 0f;
    public float stateTime = 0f;
    public float currentAlpha = 1f;

    public boolean totallyDestroyed = false;
    public float health;

    public Box() {}

    public Box(World world, float x, float y, float width, float height, float side, Texture boxTexture, String texturePath) {
        super(boxTexture);
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.side = side;
        this.texturePath = texturePath;
        this.boxTexture = boxTexture;
        this.health = 3;

        defineBox(x,y,side);
        setSize(width, height);
        setPosition(x - width / 2, y - height / 2); // Center the sprite on the position
    }

    public void defineBox(float x, float y, float side) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(side / 2, side / 2); // Box centered around (x, y)

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = getDensity(); // Density specific to the box type
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f; // Bounciness
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        //check if needed and when ??
        shape.dispose(); // Clean up after using the shape
    }

    public static Box createBox(World world, Box box) {
        if (box.texturePath.equals("glassBox.png")) {
            return new GlassBox(world, box.x, box.y, box.width, box.height, box.side);
        } else if (box.texturePath.equals("woodBox.png")) {
            return new WoodBox(world, box.x, box.y, box.width, box.height, box.side);
        } else if (box.texturePath.equals("steelBox.png")) {
            return new SteelBox(world, box.x, box.y, box.width, box.height, box.side);
        }
        // Default to a WoodBox if texturePath doesn't match
        return new WoodBox(world, box.x, box.y, box.width, box.height, box.side);
    }

    public abstract float getDensity(); // Abstract method to define density per box type

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


    //see where to use the dispose method to dispose the body from the world ??
    public void dispose() {
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
        if (boxTexture != null) {
            boxTexture.dispose();
            boxTexture = null;
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("side", side);
        json.writeValue("texturePath", texturePath);
        json.writeValue("isDestroyed", isDestroyed);
        json.writeValue("destroyTime", destroyTime);
        json.writeValue("stateTime", stateTime);
        json.writeValue("currentAlpha", currentAlpha);
        json.writeValue("totallyDestroyed", totallyDestroyed);
        json.writeValue("health", health);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        x = json.readValue("x", Float.class, jsonData);
        y = json.readValue("y", Float.class, jsonData);
        width = json.readValue("width", Float.class, jsonData);
        height = json.readValue("height", Float.class, jsonData);
        side = json.readValue("side", Float.class, jsonData);
        texturePath = json.readValue("texturePath", String.class, jsonData);
        isDestroyed = json.readValue("isDestroyed", boolean.class, jsonData);
        destroyTime = json.readValue("destroyTime", Float.class, jsonData);
        stateTime = json.readValue("stateTime", Float.class, jsonData);
        currentAlpha = json.readValue("currentAlpha", Float.class, jsonData);
        totallyDestroyed = json.readValue("totallyDestroyed", boolean.class, jsonData);
        health = json.readValue("health", Float.class, jsonData);
    }
}
