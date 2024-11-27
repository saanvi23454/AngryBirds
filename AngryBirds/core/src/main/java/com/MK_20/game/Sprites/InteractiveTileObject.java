package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;

public abstract class InteractiveTileObject implements Json.Serializable {
    protected transient World world;
    protected transient TiledMap tiledMap;
    protected transient TiledMapTile tile;
    protected transient Rectangle bounds;
    protected transient Ellipse eBounds;
    public transient Body body;
    protected transient Sprite sprite;
    protected transient LevelCreator levelCreator;

    public boolean isDestroyed;
    public boolean isTotallyDestroyed;
    public int type;

    public float health;
    public float x,y,width,height;

    public InteractiveTileObject() {}

    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds, LevelCreator levelCreator) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.bounds = bounds;
        this.eBounds = null;
        this.isDestroyed = false;
        this.levelCreator = levelCreator;
        this.isTotallyDestroyed = false;
        width=bounds.width;
        height=bounds.height;
    }

    public InteractiveTileObject(World world, TiledMap tiledMap, Ellipse bounds, LevelCreator levelCreator) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.eBounds = bounds;
        this.bounds = null;
        this.isDestroyed = false;
        this.levelCreator = levelCreator;
        this.isTotallyDestroyed = false;
        width=bounds.width;
        height=bounds.height;
    }

    public void update() {
        // Update the sprite's position based on the body's position
        if (body != null) {
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        }
    }

    public void draw(SpriteBatch batch) {
        if (!isTotallyDestroyed) {
            sprite.draw(batch);
        }
    }

    public float getMass() {
        if (body != null) {
            return body.getMass();
        }
        return 0;
    }

    public float getDensity() {
        if (body != null) {
            return body.getFixtureList().get(0).getDensity();
        }
        return 0;
    }

    public void destroyAfterDelay(float delay) {
        if (!isDestroyed) {
            this.isDestroyed = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    destroySelf();  // Destroy pig after the delay
                }
            }, delay);  // Delay in seconds
        }
    }

    protected void destroySelf(){
        world.destroyBody(body);
        isTotallyDestroyed = true;
    }

    @Override
    public void write(Json json) {
        // Save serializable fields
        System.out.println(body.getPosition().x+" "+body.getPosition().y);
        json.writeValue("x", body.getPosition().x*AngryBirds.PPM);
        json.writeValue("y", body.getPosition().y*AngryBirds.PPM);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("isDestroyed", isDestroyed);
        json.writeValue("isTotallyDestroyed", isTotallyDestroyed);
        json.writeValue("type",type);
        json.writeValue("health",health);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        try {
            // Load serializable fields
            x = json.readValue("x", Float.class, jsonData);
            y = json.readValue("y", Float.class, jsonData);
            width = json.readValue("width", Float.class, jsonData);
            height = json.readValue("height", Float.class, jsonData);
            isDestroyed = json.readValue("isDestroyed", boolean.class, jsonData);
            isTotallyDestroyed = json.readValue("isTotallyDestroyed", boolean.class, jsonData);
            type = json.readValue("type", Integer.class, jsonData);
            health = json.readValue("health", Float.class, jsonData);
        }
        catch (Exception e) {
            System.out.println("Error deserializing bird: " + e.getMessage());
        }
    }

}
