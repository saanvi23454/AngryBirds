package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Pig extends InteractiveTileObject {

    public Pig(){}

   public Pig(World world, TiledMap tiledMap, Ellipse bounds, LevelCreator levelCreator, String texture) {
       super(world, tiledMap, bounds, levelCreator);
//       this.health = 100;

       this.sprite = new Sprite(new TextureRegion(new Texture(texture)));
       // Set the size of the sprite to match the physical body
       sprite.setSize(bounds.width / AngryBirds.PPM, bounds.height / AngryBirds.PPM);
       sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);


       // Set the position of the sprite to match the physics body
       sprite.setPosition(bounds.x / AngryBirds.PPM, bounds.y / AngryBirds.PPM);
       BodyDef bodyDef = new BodyDef();
       FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.density = 2.0f;
       fixtureDef.friction = 0.6f;
       fixtureDef.restitution = 0.4f;

//       fixtureDef.filter.categoryBits = AngryBirds.CATEGORY_PIG;
//       fixtureDef.filter.maskBits = AngryBirds.MASK_PIG;

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
       levelCreator.pigs.remove(this);
       isTotallyDestroyed = true;
   }

   private static void helper(Pig b, Pig bi){
       b.health=bi.health;
       b.isDestroyed=bi.isDestroyed;
       b.isTotallyDestroyed=bi.isTotallyDestroyed;
   }

    public static Pig createPig(World world, TiledMap map, Ellipse bounds, LevelCreator levelCreator, Pig pi) {
        Pig p=null;
        switch (pi.type) {
            case 1:
                p=new BabyPig(world,map,bounds,levelCreator);
                helper(p,pi);
                break;
            case 2:
                p=new KingPig(world,map,bounds,levelCreator);
                helper(p,pi);
                break;
            case 3:
                p=new HektorPig(world,map,bounds,levelCreator);
                helper(p,pi);
                break;
        }
        return p;
    }

    // overloaded constructor for testing purpose
    public Pig(World world) {
        super(world);
    }

    // Add the TestPig class for testing
    public static class TestPig extends Pig {
        public TestPig(World world) {
            super(world);
            this.health = 100;  // Default health for test cases
        }
        public void destroySelf() {
            isTotallyDestroyed = true;  // Mark as destroyed for testing
        }
    }
}
