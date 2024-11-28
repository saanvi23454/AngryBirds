package com.MK_20.game.Sprites;

import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class ChickenBird extends Bird {

    public ChickenBird() {}

    public ChickenBird(World world, TiledMap tiledMap, Ellipse ellipse, LevelCreator levelCreator) {
        super(world,tiledMap,ellipse, levelCreator, "chickenBird.png");
        this.type=3;
    }

    @Override
    public void specialFeature() {
        if (this.body == null || this.isDestroyed || this.specialFeatureUsed) return;
        specialFeatureUsed = true;

        // Give velocities 0.
        body.setLinearVelocity(0, 0);
    }
}