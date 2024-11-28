package com.MK_20.game;

import com.MK_20.game.Screens.*;
import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Box;
import com.MK_20.game.Sprites.Pig;
import com.MK_20.game.Tools.Data;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    public static final String SAVEPATH="save.json";
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
    public static AngryBirds game;
    public static Music backgroundMusic;

//    public static short CATEGORY_BIRD = 0x0001;
//    public static short CATEGORY_PIG = 0x0002;
//    public static short CATEGORY_WOOD = 0x0004;
//    public static short MASK_BIRD = (short) (CATEGORY_PIG | CATEGORY_WOOD); // birds collide with pigs and wood
//    public static short MASK_PIG = CATEGORY_BIRD; // pigs only collide with birds
//    public static short MASK_WOOD = CATEGORY_BIRD; // wood only collides with birds

    @Override
    public void create() {
        totalLevels = 3;
        batch = new SpriteBatch();
        homeScreen = new HomeScreen(this);
        loadScreen = new LoadScreen(this);
        settings = new Settings(this);
        pause = new PauseScreen(this);
        failed = new FailedScreen(this);
        won = new WonScreen(this);
        saveScreen = new SaveScreen(this);
        setScreen(new Start(this));
        game = this;

        backgroundMusic=Gdx.audio.newMusic(Gdx.files.internal("music/angry_birds_piano.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
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
