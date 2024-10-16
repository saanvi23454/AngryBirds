package com.MK_20.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Start extends State{

    private Texture background;
    private Texture playButton;

    private float buttonX, buttonY;
    private float buttonWidth, buttonHeight;

    public Start(GameStateManager gsm) {
        super(gsm);
        background = new Texture("mainBG.png");
        playButton = new Texture("playButton.png");

        buttonWidth = Gdx.graphics.getWidth() / 8f;
        float aspectRatio = (float) playButton.getHeight() / playButton.getWidth();
        buttonHeight = buttonWidth * aspectRatio;

        buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gameState.set(new HomeState(gameState));
            dispose();
        }
    }

    @Override
    protected void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0, 640, 480);
        batch.draw(playButton, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        if (background != null) {
            background.dispose();
        }
        if (playButton != null) {
            playButton.dispose();
        }
    }
}
