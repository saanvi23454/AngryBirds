package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.logging.FileHandler;

public class HomeScreen implements Screen {

    private AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background;
    private BitmapFont font;
    private Stage stage;
    private Texture levelBg;

    private ImageButton settings;
    private ArrayList<TextButton> levelButtons;

    public HomeScreen(AngryBirds game) {
        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH,AngryBirds.HEIGHT,camera);

        stage = new Stage(viewport);

        background = new Texture("home.jpg");
        font = new BitmapFont();
        font.getData().setScale(2);

        levelBg = new Texture("img.png");
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(levelBg));
        style.font = font;

        Table levelsTable = new Table();
        levelsTable.top();
        levelsTable.setFillParent(true);
        levelsTable.padLeft(20);
        levelsTable.padTop(50);

        levelButtons = new ArrayList<>();
        System.out.println("Total Levels: "+game.totalLevels);
        for (int i = 1; i <= game.totalLevels; i++) {
            final int index = i;
            TextButton levelButton = new TextButton(""+i,style);
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Level "+index+" Clicked");
                    if (index!=1){
                        return;
                    }
                    game.currentLevelIndex = index; //relatively FINAL as variable is accessed from inner class
                    game.setScreen(game.loadScreen);
                }
            });
            levelButtons.add(levelButton);
            levelsTable.add(levelButton).size(50,50).expandX().padTop(10);
            if (i%3==0){
                levelsTable.row();
            }
        }
        stage.addActor(levelsTable);


        settings = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("settings.png"))));
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("settings");
                game.setScreen(game.settings);
            }
        });

        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        optionsTable.padLeft(20);
        optionsTable.padBottom(20);
        optionsTable.bottom().left();
        optionsTable.add(settings).size(40,40).padTop(50).expandX().bottom().left();  // Add the settings button in the bottom-left corner
        stage.addActor(optionsTable);

    }

    @Override
    public void show() {
        viewport.apply();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0,viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (background != null) {background.dispose();}
        if (levelBg != null) {levelBg.dispose();}
        if (font != null) {font.dispose();}
        if (stage != null) {stage.dispose();}
    }
}
