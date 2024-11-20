package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class WoodBox extends Box {

    public WoodBox() {}

    public WoodBox(World world, float x, float y, float width, float height, float side) {
        super(world, x, y, width, height, side, new Texture("woodBox.png"), "woodBox.png");
        setTexture(this.boxTexture); 
        setRegion(boxTexture);      
    }

    @Override
    public float getDensity() {
        return 1.0f;
    }
}
