package com.MK_20.game.Sprites;

import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class HektorPig extends Pig {

    public HektorPig() {}

    public HektorPig(World world, TiledMap tiledMap, Ellipse bounds, LevelCreator levelCreator) {
        super(world, tiledMap, bounds, levelCreator,"hektorPig.png");
        this.health = 100;
        this.type=3;
    }
}
