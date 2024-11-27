package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Start implements Screen {

    private AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background;

    public Start(AngryBirds game) {
        this.game=game;
        background = new Texture("mainBG3.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH,AngryBirds.HEIGHT,camera);
        viewport.apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0,viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();
        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void handleInput() {
        if (Gdx.input.justTouched()){
            this.dispose();
            game.setScreen(game.homeScreen);
        }
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
        if (background!=null) background.dispose();
    }
}
