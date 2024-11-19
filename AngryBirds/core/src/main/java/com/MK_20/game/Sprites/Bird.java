package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class Bird extends Sprite implements Json.Serializable{

    //Transient so that it does not serialize
    public transient World world;
    public transient Body body;
    public transient Texture bird_texture;

    public float x, y, width, height, radius; // Serializable fields
    public String texturePath;

    public Bird(){}

    public Bird(World world, float x, float y, float width, float height, float radius, Texture bird_texture, String texturePath) {
        super(bird_texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.texturePath=texturePath;
        this.bird_texture = bird_texture;
        this.world = world;
        defineBird(x, y, radius);
        setSize(width,height);
    }

    public static Bird createBird(World world, Bird b) {
        if (b.texturePath.equals("redBird.png")) {
            return new RedBird(world, b.x, b.y, b.width, b.height, b.radius); // RedBird subclass
        }
        // Add other conditions for different bird types (BlueBird, YellowBird, etc.)
        // For now, return a RedBird if texturePath doesn't match
        return new RedBird(world, b.x, b.y, b.width, b.height, b.radius);
    }

    public void update(float delta) {
        this.x=body.getPosition().x;
        this.y=body.getPosition().y;
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
    }

    public void defineBird(float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }


    @Override
    public void write(Json json) {
        // Save serializable fields
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("radius", radius);
        json.writeValue("texturePath", texturePath);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        try {
            // Load serializable fields
            x = json.readValue("x", Float.class, jsonData);
            y = json.readValue("y", Float.class, jsonData);
            width = json.readValue("width", Float.class, jsonData);
            height = json.readValue("height", Float.class, jsonData);
            radius = json.readValue("radius", Float.class, jsonData);
            texturePath = json.readValue("texturePath", String.class, jsonData);
        }
        catch (Exception e) {
            System.out.println("Error deserializing bird: " + e.getMessage());
        }
    }
}