package com.MK_20.game;

import com.MK_20.game.Screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    public SpriteBatch batch;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final float PPM = 100; // 100 pixels per meter
    public HomeScreen homeScreen;
    public Settings settings;
    public PauseScreen pause;
    public FailedScreen failed;
    public WonScreen won;
    public PlayScreen currentLevel;
    public int totalLevels;

    @Override
    public void create() {
        totalLevels = 2;
        batch = new SpriteBatch();
        homeScreen = new HomeScreen(this);
        settings = new Settings(this);
        pause = new PauseScreen(this);
        failed = new FailedScreen(this);
        won = new WonScreen(this);
        setScreen(new Start(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
