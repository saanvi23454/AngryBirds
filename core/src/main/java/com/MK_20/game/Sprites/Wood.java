package com.MK_20.game.Sprites;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class Wood extends Box{

    public Wood(){}

    public Wood(World world, TiledMap tiledMap, Rectangle bounds, LevelCreator levelCreator) {
        super(world, tiledMap, bounds, levelCreator,"woodBox.png");
        this.health = 100;
        this.type=1;
    }
}
