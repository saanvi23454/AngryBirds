package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class WoodLog extends Box{

    public WoodLog(){}

    public WoodLog(World world, TiledMap tiledMap, Rectangle bounds, LevelCreator levelCreator) {
        super(world, tiledMap, bounds, levelCreator,"vertical_wood.png");
        this.health = 90;
        this.type=2;
    }
}
