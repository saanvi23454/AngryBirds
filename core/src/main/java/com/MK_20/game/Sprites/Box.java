package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class Box extends InteractiveTileObject{

    public Box(){}

    public Box(World world, TiledMap tiledMap, Rectangle bounds, LevelCreator levelCreator, String texture) {
        super(world, tiledMap, bounds, levelCreator);

//        this.health = 100;

        this.sprite = new Sprite(new TextureRegion(new Texture(texture)));  // Example texture file
        // Set the size of the sprite to match the physical body
        sprite.setSize(bounds.getWidth() / AngryBirds.PPM, bounds.getHeight() / AngryBirds.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);


        // Set the position of the sprite to match the physics body
        sprite.setPosition(bounds.getX() / AngryBirds.PPM, bounds.getY() / AngryBirds.PPM);
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.1f;

        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((bounds.getX()+(bounds.getWidth()/2)) / AngryBirds.PPM, (bounds.getY()+(bounds.getHeight()/2)) / AngryBirds.PPM);

        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2 / AngryBirds.PPM, bounds.getHeight()/2 / AngryBirds.PPM);
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    private static void helper(Box b, Box bi){
        b.health=bi.health;
        b.isDestroyed=bi.isDestroyed;
        b.isTotallyDestroyed=bi.isTotallyDestroyed;
        b.body.setTransform(b.body.getPosition(),bi.angle);
    }

    public static Box createBox(World world, TiledMap map, Rectangle bounds, LevelCreator levelCreator, Box bo) {
        Box b=null;
        switch (bo.type) {
            case 1:
                b=new Wood(world,map,bounds,levelCreator);
                helper(b,bo);
                break;
            case 2:
                b=new WoodLog(world,map,bounds,levelCreator);
                helper(b,bo);
                break;
            case 3:
                b=new Glass(world,map,bounds,levelCreator);
                helper(b,bo);
                break;
        }
        return b;
    }

    @Override
    protected void destroySelf(){
        world.destroyBody(body);
        levelCreator.boxes.remove(this);
        isTotallyDestroyed = true;
    }

    // overloaded constructor for testing purposes
    public Box(World world) {
        super(world);
    }

    // Add the TestBox class for testing
    public static class TestBox extends Box {
        public TestBox(World world) {
            super(world);
            this.health = 100;  // Default health for test cases
        }
        public void destroySelf() {
            isTotallyDestroyed = true;  // Mark as destroyed for testing
        }
    }
}
