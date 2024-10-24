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

public class SaveScreen implements Screen {

    AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background,loadFromPrev,restart;
    private ImageButton saveButton, quitButton;
    private Stage stage;

    private Table table,crossTable;

    public SaveScreen(AngryBirds game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);

        stage = new Stage(viewport);

        background = new Texture("loadScreenBG.png");
//        loadFromPrev = new Texture("loadButton.png");
//        restart = new Texture("restartButton.png");
        saveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("saveGameButton.png"))));
        quitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("quitButton.png"))));



        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("quit button clicked");
                //functionality ?
                game.setScreen(game.homeScreen);
            }
        });

        //right now, load button will also restart the game
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("save button clicked");
                //functionality for serialization
                game.setScreen(game.homeScreen);
            }
        });


        table = new Table();
        table.setFillParent(true);
        table.align(com.badlogic.gdx.utils.Align.left);
//        table.defaults().space(3);  // Set 5 units of space between rows
        table.add(saveButton).size(150,150).padTop(-20);
        table.row();
        table.add(quitButton).size(150,150).padTop(-40);
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
