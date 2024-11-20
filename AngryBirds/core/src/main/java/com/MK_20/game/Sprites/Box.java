package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
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
        body.createFixture(fixtureDef);

        shape.dispose(); // Clean up the shape
    }

    public abstract float getDensity(); // Abstract method to define density per box type

    public void update(float delta) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
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
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        x = json.readValue("x", Float.class, jsonData);
        y = json.readValue("y", Float.class, jsonData);
        width = json.readValue("width", Float.class, jsonData);
        height = json.readValue("height", Float.class, jsonData);
        side = json.readValue("side", Float.class, jsonData);
        texturePath = json.readValue("texturePath", String.class, jsonData);
    }
}
