package com.MK_20.game;

import com.MK_20.game.Screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    public SpriteBatch batch;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final float PPM = 2.22f; // 100 pixels per meter
    public HomeScreen homeScreen;
    public LoadScreen loadScreen;
    public Settings settings;
    public PauseScreen pause;
    public FailedScreen failed;
    public WonScreen won;
    public PlayScreen currentLevel;
    public SaveScreen saveScreen;
    public int totalLevels;
    public int currentLevelIndex;

    @Override
    public void create() {
        totalLevels = 7;
        batch = new SpriteBatch();
        homeScreen = new HomeScreen(this);
        loadScreen = new LoadScreen(this);
        settings = new Settings(this);
        pause = new PauseScreen(this);
        failed = new FailedScreen(this);
        won = new WonScreen(this);
        saveScreen = new SaveScreen(this);
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
