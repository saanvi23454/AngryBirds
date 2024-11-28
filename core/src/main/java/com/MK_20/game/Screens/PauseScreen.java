package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseScreen implements Screen {

    private AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Texture background;
    private ImageButton playButton,restartButton,homeButton;

    public PauseScreen(AngryBirds game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);
        stage = new Stage(viewport);

        background = new Texture("pauseBG2.png");
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("playButton.png"))));
        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("replayButton.png"))));
        homeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("homeButton2.png"))));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("play button clicked");
                 game.setScreen(game.currentLevel);
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restart button clicked");
                game.currentLevel= new PlayScreen(game,game.currentLevel.index);
                game.setScreen(game.currentLevel);
            }
        });
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("home button clicked");
                game.setScreen(game.saveScreen);
            }
        });
        Table table = new Table();
        table.setFillParent(true);
        table.add(playButton).size(75,75);
        table.left().padLeft(75).padBottom(150);
        stage.addActor(table);

        Table table3 = new Table();
        table3.setFillParent(true);
        table3.add(homeButton).size(80,80);
        table3.left().padLeft(75).padTop(60);
        stage.addActor(table3);

        Table table2 = new Table();
        table2.setFillParent(true);
        table2.add(restartButton).size(75,75);
        table2.left().padLeft(75).padTop(270);
        stage.addActor(table2);

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
        if (background!=null){background.dispose();}
        if (stage!=null){stage.dispose();}
    }
}
