package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird extends Sprite {

    public World world;
    public Body body;
    public Texture bird_texture;

    public Bird(World world, float x, float y, float width, float height, float radius, Texture bird_texture) {
        super(bird_texture);
        this.bird_texture = bird_texture;
        this.world = world;
        defineBird(x, y, radius);
        setSize(width,height);
    }

    public void update(float delta) {
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

}
