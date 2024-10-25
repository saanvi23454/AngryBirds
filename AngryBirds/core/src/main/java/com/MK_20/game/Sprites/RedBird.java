package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class RedBird extends Bird {
    public RedBird(World world, float x, float y, float width, float height, float radius) {
        super(world,x,y, width, height, radius, new Texture("redBird.png"));
        setTexture(this.bird_texture);
    }
}
