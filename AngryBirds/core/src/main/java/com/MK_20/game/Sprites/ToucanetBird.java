package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class ToucanetBird extends Bird {
    public ToucanetBird(World world, float x, float y, float width, float height, float radius) {
        super(world,x,y, width, height, radius, new Texture("toucanetBird.png"));
        setTexture(this.bird_texture);
    }
}
