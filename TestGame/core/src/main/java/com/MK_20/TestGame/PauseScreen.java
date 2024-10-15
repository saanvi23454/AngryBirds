package com.MK_20.TestGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class PauseScreen implements Screen {

    private AngryBirds game;
    private Texture background;
    private Texture panel;
    private Texture resume, restart, exit;
    private Stage stage;
    private TextButton Resume, Restart, Exit;
    private BitmapFont font;

    public PauseScreen(AngryBirds g){

        game = g;

        background = new Texture(Gdx.files.internal("bg2.png"));
        panel = new Texture(Gdx.files.internal("foreground.png"));
        resume = new Texture(Gdx.files.internal("Resume.png"));
        restart = new Texture(Gdx.files.internal("Restart.png"));
        exit = new Texture(Gdx.files.internal("Exit.png"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();

        Image bg = new Image(background);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg.setPosition(0, 0);
        stage.addActor(bg);

        Image leftPanel = new Image(panel);
        leftPanel.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight());
        leftPanel.setPosition(0, 0);
        stage.addActor(leftPanel);

        float buttonHeight = Gdx.graphics.getHeight() / 10f;
        float centerX = Gdx.graphics.getWidth() / 10f;
        float startY = Gdx.graphics.getHeight() / 2f + buttonHeight;

        Resume = createButton(resume, buttonHeight);
        Restart = createButton(restart, buttonHeight);
        Exit = createButton(exit, buttonHeight);

        Resume.setPosition(centerX, startY);
        Restart.setPosition(centerX, startY - buttonHeight - 10);
        Exit.setPosition(centerX, startY - 2 * (buttonHeight + 10));

        stage.addActor(Resume);
        stage.addActor(Restart);
        stage.addActor(Exit);
    }

    private TextButton createButton(Texture texture, float height) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.font = font;
        style.up = drawable;
        style.down = drawable;

        float aspectRatio = (float) texture.getWidth() / texture.getHeight();
        float width = height * aspectRatio;

        TextButton button = new TextButton("", style);
        button.setSize(width, height);
        return button;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.app.log("PauseScreen", "Rendering");
        ScreenUtils.clear(1, 1, 1, 1);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        background.dispose();
        panel.dispose();
        resume.dispose();
        restart.dispose();
        exit.dispose();
        stage.dispose();
    }

}