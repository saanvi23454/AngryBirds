
package com.MK_20.game.Sprites;

import com.MK_20.game.Levels.Level;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BlueBird extends Bird {

    public BlueBird(){}

    public BlueBird(World world, TiledMap tiledMap, Ellipse ellipse, LevelCreator levelCreator) {
        super(world,tiledMap,ellipse, levelCreator, "blueBird.png");
        this.type=2;
    }

    @Override
    public void specialFeature() {
        if (this.body == null || this.isDestroyed) return;

        if (specialFeatureUsed){ return; }
        specialFeatureUsed = true;
        // Create two additional birds
        Ellipse ellipse = this.eBounds;
        BlueBird bird1 = new BlueBird(world, this.tiledMap, ellipse, levelCreator);
        BlueBird bird2 = new BlueBird(world, this.tiledMap, ellipse, levelCreator);

        // Set their positions slightly offset from the original bird
        bird1.body.setTransform(this.body.getPosition().x - 0.5f, this.body.getPosition().y+15, 0);
        bird2.body.setTransform(this.body.getPosition().x + 0.5f, this.body.getPosition().y-15, 0);

        // Give them velocities to spread out
        Vector2 originalVelocity = this.body.getLinearVelocity();
        bird1.body.setLinearVelocity(originalVelocity.x - 4, originalVelocity.y + 15);
        bird2.body.setLinearVelocity(originalVelocity.x + 4, originalVelocity.y + -15);

        // Add the new birds to the thrown birds list
        levelCreator.thrownBirds.add(bird1);
        levelCreator.thrownBirds.add(bird2);
    }
}