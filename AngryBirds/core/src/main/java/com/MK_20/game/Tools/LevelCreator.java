package com.MK_20.game.Tools;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Levels.Level;
import com.MK_20.game.Sprites.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Random;

public class LevelCreator {
    public ArrayList<Box> boxes;
    public ArrayList<Bird> birds;
    public ArrayList<Pig> pigs;
    public ArrayList<Bird> thrownBirds;
    public Slingshot slingshot;

    public LevelCreator(World world, TiledMap tiledmap) {
        //Will be changed.
        boxes = new ArrayList<>();
        birds = new ArrayList<>();
        pigs = new ArrayList<>();
        thrownBirds = new ArrayList<>();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //ground body
        for (MapObject object: tiledmap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+(rectangle.getWidth()/2))/ AngryBirds.PPM, (rectangle.getY()+(rectangle.getHeight()/2))/ AngryBirds.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2/ AngryBirds.PPM, rectangle.getHeight()/2/ AngryBirds.PPM);
            fixtureDef.shape = shape;
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData("GROUND");
        }


        //wood body
        for (MapObject object: tiledmap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            boxes.add(new Wood(world,tiledmap,rectangle, this));
        }

        for (MapObject object: tiledmap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            boxes.add(new Glass(world, tiledmap, rectangle, this));
        }

        for (MapObject object: tiledmap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            boxes.add(new WoodLog(world, tiledmap, rectangle, this));
        }

        for (MapObject object: tiledmap.getLayers().get(6).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            birds.add(chooseBird(world, tiledmap, ellipse));
        }

        for (MapObject object: tiledmap.getLayers().get(7).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            pigs.add(choosePig(world, tiledmap, ellipse));
        }

        for (MapObject object: tiledmap.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            slingshot= Slingshot.getInstance(rect.getX(), rect.getY());
        }
    }

    public Bird chooseBird(World world, TiledMap tiledmap, Ellipse ellipse) {
        Random random = new Random();

        Bird bird=null;

        // Instantiate the chosen bird based on the random value
        switch (random.nextInt(4)) {
            case 0:
                bird = new RedBird(world, tiledmap, ellipse, this);
                break;
            case 1:
                bird = new BlueBird(world, tiledmap, ellipse, this);
                break;
            case 2:
                bird = new ChickenBird(world, tiledmap, ellipse, this);
                break;
            case 3:
                bird = new ToucanetBird(world, tiledmap, ellipse, this);
                break;
        }
        return bird;
    }

    public Pig choosePig(World world, TiledMap tiledmap, Ellipse ellipse) {
        Random random = new Random();

        Pig pig=null;

        // Instantiate the chosen bird based on the random value
        switch (random.nextInt(3)) {
            case 0:
                pig = new BabyPig(world, tiledmap, ellipse, this);
                break;
            case 1:
                pig = new HektorPig(world, tiledmap, ellipse, this);
                break;
            case 2:
                pig = new KingPig(world, tiledmap, ellipse, this);
                break;
        }
        return pig;
    }
}