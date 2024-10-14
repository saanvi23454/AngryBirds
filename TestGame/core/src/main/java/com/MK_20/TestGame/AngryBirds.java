package com.MK_20.TestGame;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {

    @Override
    public void create() {
        // Directly setting the screen to the HomeScreen instance
        setScreen(new HomeScreen(this));
    }
}
