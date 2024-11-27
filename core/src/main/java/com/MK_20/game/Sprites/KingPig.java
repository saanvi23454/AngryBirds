package com.MK_20.game.Sprites;

import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig {

    public KingPig(){}

    public KingPig(World world, TiledMap tiledMap, Ellipse bounds, LevelCreator levelCreator) {
        super(world, tiledMap, bounds, levelCreator,"kingPig.png");
        this.health = 120;
        this.type=2;
    }
}
