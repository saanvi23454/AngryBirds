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

public class WonScreen implements Screen {

    private AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Texture background;
    private ImageButton restartButton,homeButton,nextButton;

    public WonScreen(AngryBirds game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);
        stage = new Stage(viewport);

        background = new Texture("wonBG.png");
        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("restart.png"))));
        homeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("homeButton.png"))));
        nextButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("nextButton.png"))));
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restart button clicked");
                game.currentLevel= new PlayScreen(game,game.currentLevel.index);
                game.setScreen(game.currentLevel);
            }
        });
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("home button clicked");
                game.setScreen(game.homeScreen);
            }
        });
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("next button clicked");
                if (game.totalLevels==game.currentLevel.index || game.currentLevel.index==1){       //only for now the or statement.
                    System.out.println("You have cleared the last level.");
                    game.setScreen(game.homeScreen);
                }
                else{
                    game.currentLevelIndex += 1; //CHECK
                    game.currentLevel = new PlayScreen(game,game.currentLevel.index+1);
                    game.setScreen(game.currentLevel);
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(restartButton).size(90,90);
        table.right().padRight(200).padBottom(150);
        stage.addActor(table);

        Table table2 = new Table();
        table2.setFillParent(true);
        table2.add(nextButton).size(90,90);
        table2.right().padRight(170).padTop(275);
        stage.addActor(table2);

        Table table3 = new Table();
        table3.setFillParent(true);
        table3.add(homeButton).size(90,90);
        table3.right().padRight(60).padTop(40);
        stage.addActor(table3);
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
