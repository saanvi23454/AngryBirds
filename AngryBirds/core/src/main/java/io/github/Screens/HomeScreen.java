package io.github.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class HomeScreen implements Screen {

    private AngryBirds game;
    private Texture background;
    private Texture playButton;
    private SpriteBatch batch;

    private float buttonX, buttonY;
    private float buttonWidth, buttonHeight;

    public HomeScreen(AngryBirds game){
        this.game = game;

        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("mainBG.png"));
        playButton = new Texture(Gdx.files.internal("playButton.png"));

        buttonWidth = Gdx.graphics.getWidth() / 8f;
        float aspectRatio = (float) playButton.getHeight() / playButton.getWidth();
        buttonHeight = buttonWidth * aspectRatio;

        buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;
    }

    @Override
    public void show() {
        if (!Gdx.files.internal("mainBG.png").exists()) {
            Gdx.app.error("HomeScreen", "Background image 'mainBG.png' not found!");
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playButton, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        if (background != null) background.dispose();
        if (playButton != null) playButton.dispose();
    }

}
