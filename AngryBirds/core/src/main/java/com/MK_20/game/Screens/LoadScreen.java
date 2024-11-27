package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Box;
import com.MK_20.game.Sprites.Pig;
import com.MK_20.game.Tools.Data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadScreen implements Screen {

    AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background;
    private ImageButton loadButton, restartButton;
    private Stage stage;

    private Table table,crossTable;

    public LoadScreen(AngryBirds game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);

        stage = new Stage(viewport);

        background = new Texture("loadScreenBG.png");
        loadButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("loadButton.png"))));
        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("restartButton.png"))));

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restart button clicked");
                try {
                    // Delete the save file
                    String savePath = AngryBirds.SAVEPATH;
                    Gdx.files.local(savePath).delete();

                    // Restart the game
                    System.out.println("restart button clicked");
                    game.currentLevel = new PlayScreen(game, game.currentLevelIndex);
                    game.setScreen(game.currentLevel);
                } catch (Exception e) {
                    System.out.println("Error deleting save file: " + e.getMessage());
                }
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    String savePath = AngryBirds.SAVEPATH;
                    Json json = new Json();
                    Data data = json.fromJson(Data.class, Gdx.files.local(savePath).readString());

                    // Reconstruct the level
                    PlayScreen playScreen = new PlayScreen(game, data.levelIndex);
                    game.currentLevel = playScreen;

                    //clearing the boxes.
                    for (Box b: playScreen.level.levelCreator.boxes){
                        playScreen.world.destroyBody(b.body);
                    }
                    playScreen.level.levelCreator.boxes.clear();

                    //adding the boxes.
                    System.out.println("boxes: "+data.boxes.size());
                    for (Box b: data.boxes){
                        Rectangle rectangle=new Rectangle(b.x,b.y,b.width,b.height);
                        playScreen.level.levelCreator.boxes.add(Box.createBox(playScreen.world,playScreen.getTiledmap(),rectangle,playScreen.level.levelCreator, b));
                    }

                    //clearing the birds.
                    for (Bird b: playScreen.level.levelCreator.birds){
                        playScreen.world.destroyBody(b.body);
                    }
                    playScreen.level.levelCreator.birds.clear();
                    //adding the birds.
                    for (Bird b: data.birds){
                        System.out.println("x: "+b.x+" y:"+b.y);
                        Ellipse ellipse=new Ellipse(b.x, b.y, b.width, b.height);
                        playScreen.level.levelCreator.birds.add(Bird.createBird(playScreen.world,playScreen.getTiledmap(),ellipse,playScreen.level.levelCreator,b));
                    }

                    for (Bird b: playScreen.level.levelCreator.thrownBirds){
                        playScreen.world.destroyBody(b.body);
                    }
                    playScreen.level.levelCreator.thrownBirds.clear();
                    //adding the birds.
                    for (Bird b: data.thrownBirds){
                        System.out.println("x: "+b.x+" y:"+b.y);
                        Ellipse ellipse=new Ellipse(b.x, b.y, b.width, b.height);
                        playScreen.level.levelCreator.thrownBirds.add(Bird.createBird(playScreen.world,playScreen.getTiledmap(),ellipse,playScreen.level.levelCreator,b));
                    }

                    //clearing out the pigs.
                    for (Pig p: playScreen.level.levelCreator.pigs) {
                        playScreen.world.destroyBody(p.body);
                    }
                    playScreen.level.levelCreator.pigs.clear();
                    //adding the pigs.
                    for (Pig p:data.pigs) {
                        //change implementation
                        Ellipse e=new Ellipse(p.x, p.y, p.width, p.height);
                        playScreen.level.levelCreator.pigs.add(Pig.createPig(playScreen.world, playScreen.getTiledmap(), e,playScreen.level.levelCreator, p));
                    }

                    playScreen.level.afterSaving();

                    game.setScreen(game.currentLevel);
                } catch (Exception e) {
                    System.out.println("Error loading game: " + e.getMessage());
                    game.currentLevel = new PlayScreen(game, game.currentLevelIndex);
                    game.setScreen(game.currentLevel);
                }
            }
        });


        table = new Table();
        table.setFillParent(true);
        table.align(com.badlogic.gdx.utils.Align.left);
//        table.defaults().space(3);  // Set 5 units of space between rows
        table.add(loadButton).size(150,150).padTop(-20);
        table.row();
        table.add(restartButton).size(150,150).padTop(-40);
        table.padTop(11);
        table.padLeft(160);
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
        if (stage!=null){stage.dispose();}
    }
}
