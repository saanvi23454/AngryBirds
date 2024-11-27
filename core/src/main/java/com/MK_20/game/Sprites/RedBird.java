package com.MK_20.game.Sprites;

import com.MK_20.game.Levels.Level;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.*;

public class RedBird extends Bird {

    public RedBird(){}

    public RedBird(World world, TiledMap tiledMap, Ellipse ellipse, LevelCreator levelCreator) {
        super(world,tiledMap,ellipse, levelCreator, "redBird.png");
        this.type=1;
    }
}
