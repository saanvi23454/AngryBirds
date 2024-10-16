package com.MK_20.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private final Stack<State> states;
    public GameStateManager() {
        states = new Stack<State>();
    }

    public void pushState(State state) {
        states.push(state);
    }

    public void popState() {
        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float delta) {
        states.peek().update(delta);
    }

    public void render(SpriteBatch batch) {
        states.peek().render(batch);
    }
}
