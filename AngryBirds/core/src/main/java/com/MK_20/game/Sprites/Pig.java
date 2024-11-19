package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Pig extends Sprite implements Json.Serializable {
    //Transient so that it does not serialize
    public transient World world;
    public transient Body body;
    public transient Texture texture;

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
        definePig(x, y, radius);
        setSize(width,height);
    }

    public void update(float delta) {
        this.x=body.getPosition().x;
        this.y=body.getPosition().y;
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
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
        body.createFixture(fixtureDef);
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("radius", radius);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        try {
            x = json.readValue("x", Float.class, jsonData);
            y = json.readValue("y", Float.class, jsonData);
            width = json.readValue("width", Float.class, jsonData);
            height = json.readValue("height", Float.class, jsonData);
            radius = json.readValue("radius", Float.class, jsonData);
        }
        catch (Exception e) {
            System.out.println("Error deserializing pig: "+e.getMessage());
        }
    }
}
