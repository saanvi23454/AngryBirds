package com.MK_20.game.Levels;

import com.MK_20.game.Sprites.Pig;
import com.MK_20.game.Sprites.RedBird;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Level1 extends Level {

    public Pig p,p1,p2;
    public RedBird rb1,rb2,rb3,rb4;

    public Level1(World world) {
        p = new Pig(world,855f,172f,25,25,12);
        p1 = new Pig(world,889f,201f,25,25,12);
        p2 = new Pig(world,920f,172f,25,25,12);
        pigs.add(p);
        pigs.add(p1);
        pigs.add(p2);

        rb1 = new RedBird(world, 507f,167f,30,30,11);
        rb1.body.setType(BodyDef.BodyType.StaticBody);                              //Change this later.
        rb2 = new RedBird(world, 415f,85f,30,30,11);
        rb3 = new RedBird(world, 440f,85f,30,30,11);
        rb4 = new RedBird(world, 465f,85f,30,30,11);
        birds.add(rb1);
        birds.add(rb2);
        birds.add(rb3);
        birds.add(rb4);
    }
}
