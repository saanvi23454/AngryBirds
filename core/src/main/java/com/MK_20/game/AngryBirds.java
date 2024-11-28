package com.MK_20.game;

import com.MK_20.game.Screens.*;
import com.MK_20.game.Tools.Data;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public static int maxLevels;

    @Override
    public void create() {
        try {
            String savePath = AngryBirds.SAVEPATH;
            Json json = new Json();
            Data data = json.fromJson(Data.class, Gdx.files.local(savePath).readString());
            totalLevels = data.totalLevelsTillNow;
        }
        catch (Exception e) {
            totalLevels = 1;
        }

        maxLevels = 3;
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
