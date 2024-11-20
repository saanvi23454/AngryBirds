package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class SteelBox extends Box {

    public SteelBox() {}

    public SteelBox(World world, float x, float y, float width, float height, float side) {
        super(world, x, y, width, height, side, new Texture("steelBox.png"), "steelBox.png");
        setTexture(this.boxTexture); 
        setRegion(boxTexture);      
    }
}
