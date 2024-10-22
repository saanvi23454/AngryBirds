package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig extends Sprite {
    public World world;
    public Body body;

    public Pig(World world) {
        this.world = world;
        definePig();
    }

//    public void definePig(){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.position.set(128 / AngryBirds.PPM, 128 / AngryBirds.PPM);
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        body = world.createBody(bodyDef);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        CircleShape circleShape = new CircleShape();
//        circleShape.setRadius(10 / AngryBirds.PPM);
//        fixtureDef.shape = circleShape;
//        body.createFixture(fixtureDef);
//    }

    public void definePig(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(892, 182);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(10);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }
}
