package com.MK_20.game.Tools;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Sprites.Wood;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class LevelCreator {
//    public LevelCreator(World world, TiledMap tiledmap) {
//        BodyDef bodyDef = new BodyDef();
//        PolygonShape shape = new PolygonShape();
//        FixtureDef fixtureDef = new FixtureDef();
//        Body body;
//
//        //ground body
//        for (MapObject object: tiledmap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rectangle.getX()+(rectangle.getWidth()/2)) / AngryBirds.PPM, (rectangle.getY()+(rectangle.getHeight()/2)) / AngryBirds.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rectangle.getWidth()/2 / AngryBirds.PPM, rectangle.getHeight()/2 / AngryBirds.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
//
//        //wood body
//        for (MapObject object: tiledmap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//
//            new Wood(world, tiledmap, rectangle);
//        }
//    }
//    public LevelCreator(World world, TiledMap tiledmap) {
//        //Will be changed.
//
//        BodyDef bodyDef = new BodyDef();
//        PolygonShape shape = new PolygonShape();
//        FixtureDef fixtureDef = new FixtureDef();
//        Body body;
//
//        //ground body
//        for (MapObject object: tiledmap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rectangle.getX()+(rectangle.getWidth()/2)) / AngryBirds.PPM, (rectangle.getY()+(rectangle.getHeight()/2)) / AngryBirds.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rectangle.getWidth()/2 / AngryBirds.PPM, rectangle.getHeight()/2 / AngryBirds.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
//
//        //wood body
//        for (MapObject object: tiledmap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//            bodyDef.type = BodyDef.BodyType.DynamicBody;
//            bodyDef.position.set((rectangle.getX()+(rectangle.getWidth()/2)) / AngryBirds.PPM, (rectangle.getY()+(rectangle.getHeight()/2)) / AngryBirds.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rectangle.getWidth()/2 / AngryBirds.PPM, rectangle.getHeight()/2 / AngryBirds.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
//    }

    public LevelCreator(World world, TiledMap tiledmap) {
        //Will be changed.

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
            body.createFixture(fixtureDef);
        }

        //wood body
        for (MapObject object: tiledmap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Wood(world,tiledmap,rectangle);
        }
    }
}
