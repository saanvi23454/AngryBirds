package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class ChickenBird extends Bird {

    public ChickenBird(){}

    public ChickenBird(World world, float x, float y, float width, float height, float radius) {
        super(world,x,y, width, height, radius, new Texture("chickenBird.png"), "chickenBird.png");
        setTexture(this.bird_texture);
        setRegion(bird_texture);
    }
}
