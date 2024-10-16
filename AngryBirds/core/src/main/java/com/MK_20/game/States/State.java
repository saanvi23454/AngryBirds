package com.MK_20.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {

    protected OrthographicCamera camera;
    protected Vector2 mouse;
    protected GameStateManager gameState;

    protected State(GameStateManager gsm){
        this.gameState = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector2();
    }

    protected abstract void handleInput();
    protected abstract void update(float delta);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}
