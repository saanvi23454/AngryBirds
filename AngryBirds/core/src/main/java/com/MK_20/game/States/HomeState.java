package com.MK_20.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomeState extends State{

    private Texture background;

    public HomeState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("home.jpg");
//        camera.setToOrtho(false, 640, 300);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
//        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0,640,480);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
