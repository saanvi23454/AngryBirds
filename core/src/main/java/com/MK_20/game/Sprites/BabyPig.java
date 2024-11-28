package com.MK_20.game.Sprites;

import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class BabyPig extends Pig {

    public BabyPig(){}

    public BabyPig(World world, TiledMap tiledMap, Ellipse bounds, LevelCreator levelCreator) {
        super(world, tiledMap, bounds, levelCreator,"babyPig.png");
        this.health = 80;
        this.type=1;
    }
}
