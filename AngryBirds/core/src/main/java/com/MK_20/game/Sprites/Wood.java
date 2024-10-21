package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class Wood extends InteractiveTileObject{

    public Wood(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((bounds.getX()+(bounds.getWidth()/2)) / AngryBirds.PPM, (bounds.getY()+(bounds.getHeight()/2)) / AngryBirds.PPM);

        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2 / AngryBirds.PPM, bounds.getHeight()/2 / AngryBirds.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
