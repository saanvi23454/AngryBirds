package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Levels.Level;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class Bird extends InteractiveTileObject{

    protected boolean specialFeatureUsed;

    public Bird(){}

    public Bird(World world, TiledMap tiledmap, Ellipse bounds, LevelCreator levelCreator, String s) {
        super(world, tiledmap, bounds, levelCreator);

        this.health = 50;
        this.specialFeatureUsed = false;

        this.sprite = new Sprite(new TextureRegion(new Texture(s)));
        // Set the size of the sprite to match the physical body
        sprite.setSize(bounds.width / AngryBirds.PPM, bounds.height / AngryBirds.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        // Set the position of the sprite to match the physics body
        sprite.setPosition(bounds.x / AngryBirds.PPM, bounds.y / AngryBirds.PPM);
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

//        fixtureDef.filter.categoryBits = AngryBirds.CATEGORY_BIRD;
//        fixtureDef.filter.maskBits = AngryBirds.MASK_BIRD;

        CircleShape shape = new CircleShape();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((bounds.x+(bounds.width/2)) / AngryBirds.PPM, (bounds.y+(bounds.height/2)) / AngryBirds.PPM);

        body = world.createBody(bodyDef);
        shape.setRadius(bounds.width/2 / AngryBirds.PPM);
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    @Override
    protected void destroySelf(){
        world.destroyBody(body);
        levelCreator.thrownBirds.remove(this);
        isTotallyDestroyed = true;
    }

    public void specialFeature() {
        specialFeatureUsed = true;
    }

    public boolean isSpecialFeatureUsed() {
        return specialFeatureUsed;
    }

    private static void helper(Bird b, Bird bi){
        b.health=bi.health;
        b.isDestroyed=bi.isDestroyed;
        b.isTotallyDestroyed=bi.isTotallyDestroyed;
    }

    public static Bird createBird(World world, TiledMap map, Ellipse ellipse,LevelCreator levelCreator, Bird bi) {
        Bird b=null;
        switch (bi.type) {
            case 1:
                b=new RedBird(world,map,ellipse,levelCreator);
                helper(b,bi);
                break;
            case 2:
                b=new BlueBird(world,map,ellipse,levelCreator);
                helper(b,bi);
                break;
            case 3:
                b=new ChickenBird(world,map,ellipse, levelCreator);
                helper(b,bi);
                break;
            case 4:
                b=new ToucanetBird(world,map, ellipse, levelCreator);
                helper(b,bi);
                break;
        }
        return b;
    }

    // overloaded constructor for testing
    public Bird(World world){
        super(world);
    }

    // Add the TestBird inner class for testing
    public static class TestBird extends Bird {

        public TestBird(World world) {
            super(world);
            this.health = 50;  // Default health for test cases
            this.specialFeatureUsed = false;  // Default to false for testing
        }
        public void destroySelf() {
            isTotallyDestroyed = true;  // Mark as destroyed for testing
        }
    }
}