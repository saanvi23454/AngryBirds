package com.MK_20.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap tiledMap;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.bounds = bounds;
    }
}
