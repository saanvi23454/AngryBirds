package com.MK_20.game.Tools;

import com.MK_20.game.Sprites.Box;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.*;

import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Pig;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //extracting out the fixtures that are colliding
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        float relativeVelocity = calculateRelativeVelocity(contact);
        handleCollision(fixtureA, fixtureB, relativeVelocity);
    }

    @Override
    public void endContact(Contact contact) {
        // Called when two fixtures stop colliding
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before the physics solver resolves the collision
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after the collision has been resolved
    }

    private void handleCollision(Fixture fixtureA, Fixture fixtureB, float relativeVelocity) {
        // Check if the fixtures belong to a Bird and a Pig
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();

        if (userDataA instanceof Bird && userDataB instanceof Pig) {
            onBirdHitsPig((Bird) userDataA, (Pig) userDataB);
        } else if (userDataA instanceof Pig && userDataB instanceof Bird) {
            onBirdHitsPig((Bird) userDataB, (Pig) userDataA);
        }

        else if (userDataA instanceof Pig && userDataB instanceof Box){
            onBoxHitsPig((Box) userDataB, (Pig) userDataA, relativeVelocity);
        }
        else if (userDataA instanceof Box && userDataB instanceof Pig){
            onBoxHitsPig((Box) userDataA, (Pig) userDataB, relativeVelocity);
        }

        else if (userDataA instanceof Bird && userDataB instanceof Box){
            onBirdHitsBox((Bird) userDataA, (Box) userDataB, relativeVelocity);
        }
        else if (userDataB instanceof Bird && userDataA instanceof Box){
            onBirdHitsBox((Bird) userDataB, (Box) userDataA, relativeVelocity);
        }
    }

    private void onBirdHitsBox(Bird bird, Box box, float relativeVelocity) {
        float boxDensity = bird.getWeight();
        float damage = calculateDamage(relativeVelocity, boxDensity)*0.01f;

        box.health -= damage;
        if (box.health <= 0 && !box.isDestroyed){
            box.markAsDestroyed();}
    }

    private void onBirdHitsPig(Bird bird, Pig pig) {
        // Define what happens when a bird hits a pig
        System.out.println("Bird collided with Pig!");
        pig.markAsDestroyed();
    }

    private void onBoxHitsPig(Box box, Pig pig, float relativeVelocity){
        float boxDensity = box.getDensity();
        float damage = calculateDamage(relativeVelocity, boxDensity);
        pig.health -= damage;
        box.health -= damage;
        System.out.println("Pig health: " + pig.health);

        if (pig.health <= 0 && !pig.isDestroyed) {
            pig.markAsDestroyed();}
        if (box.health <= 0 && !box.isDestroyed){
            box.markAsDestroyed();}
    }

    private float calculateRelativeVelocity(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        return bodyA.getLinearVelocity().sub(bodyB.getLinearVelocity()).len();
    }

    private float calculateDamage(float velocity, float density) {
        return velocity * density * 1f;
    }
}
