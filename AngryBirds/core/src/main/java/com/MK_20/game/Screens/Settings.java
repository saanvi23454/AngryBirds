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

public class Settings implements Screen {

    AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background,musicOff,musicOn;
    private ImageButton musicState, faq, cross;
    boolean music = true;
    private Stage stage;

    private Table table,crossTable;

    public Settings(AngryBirds game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);

        stage = new Stage(viewport);

        background = new Texture("settingsBG.png");
        musicOn = new Texture("music.png");
        musicOff = new Texture("music_off.png");
        musicState = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("music.png"))));
        musicState.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Music");
                if (music) {
                    musicState.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(musicOff));
                    music = false;
                } else {
                    musicState.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(musicOn));
                    music = true;
                }
            }
        });

        faq = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("faq.png"))));
        cross = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("cross.png"))));
        cross.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("cross");
                game.setScreen(game.homeScreen);
            }
        });

        crossTable = new Table();
        crossTable.setFillParent(true);
        crossTable.add(cross).size(50,50);
        crossTable.top().right().padRight(120).padTop(10);
        stage.addActor(crossTable);

        table = new Table();
        table.setFillParent(true);
        table.add(musicState).size(100,100).pad(20);
        table.add(faq).size(100,100).pad(20);
        table.padTop(20);
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
        if (musicOn!=null){musicOn.dispose();}
        if (musicOff!=null){musicOff.dispose();}
        if (stage!=null){stage.dispose();}
    }
}
