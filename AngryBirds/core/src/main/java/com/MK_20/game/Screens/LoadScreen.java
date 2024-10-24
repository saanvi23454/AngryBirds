package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class LoadScreen implements Screen {

    AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background,loadFromPrev,restart;
    private ImageButton loadButton, restartButton;
    private Stage stage;

    private Table table,crossTable;

    public LoadScreen(AngryBirds game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);

        stage = new Stage(viewport);

        background = new Texture("loadScreenBG.png");
//        loadFromPrev = new Texture("loadButton.png");
//        restart = new Texture("restartButton.png");
        loadButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("loadButton.png"))));
        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("restartButton.png"))));



        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restart button clicked");
                game.currentLevel= new PlayScreen(game,game.currentLevelIndex); //change level index on clicking next in implementation
                game.setScreen(game.currentLevel);
            }
        });

        //right now, load button will also restart the game
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("load button clicked");
                game.currentLevel= new PlayScreen(game,game.currentLevelIndex);
                game.setScreen(game.currentLevel);
            }
        });


        table = new Table();
        table.setFillParent(true);
        table.align(com.badlogic.gdx.utils.Align.left);
//        table.defaults().space(3);  // Set 5 units of space between rows
        table.add(loadButton).size(150,150).padTop(-20);
        table.row();
        table.add(restartButton).size(150,150).padTop(-40);
        table.padTop(11);
        table.padLeft(160);
        stage.addActor(table);
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
