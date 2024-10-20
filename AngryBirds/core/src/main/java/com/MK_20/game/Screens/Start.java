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
    private Texture playButton;

    private float buttonX, buttonY;
    private float buttonWidth, buttonHeight;

    public Start(AngryBirds game) {
        this.game=game;
        background = new Texture("mainBG.png");
        playButton = new Texture("start.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH,AngryBirds.HEIGHT,camera);
        viewport.apply();

        buttonWidth = viewport.getWorldWidth()/8f;
        float aspectRatio = (float) playButton.getHeight() / playButton.getWidth();
        buttonHeight = buttonWidth * aspectRatio;
        buttonX = (viewport.getWorldWidth() - buttonWidth) / 2;
        buttonY = (viewport.getWorldHeight() - buttonHeight) / 2;
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
        game.batch.draw(playButton, buttonX, buttonY, buttonWidth, buttonHeight);
        game.batch.end();
        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        buttonWidth = viewport.getWorldWidth() / 8f; // Recalculate button width based on new viewport size
        float aspectRatio = (float) playButton.getHeight() / playButton.getWidth();
        buttonHeight = buttonWidth * aspectRatio;    // Maintain the button aspect ratio

        // Re-center the play button based on the new viewport size
        buttonX = (viewport.getWorldWidth() - buttonWidth) / 2;
        buttonY = (viewport.getWorldHeight() - buttonHeight) / 2;
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
        if (playButton!=null) playButton.dispose();
    }
}
