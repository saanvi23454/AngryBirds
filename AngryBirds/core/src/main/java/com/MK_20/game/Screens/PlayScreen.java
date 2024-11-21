package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Levels.Level;
import com.MK_20.game.Levels.Level1;
import com.MK_20.game.Sprites.Pig;
import com.MK_20.game.Sprites.RedBird;
import com.MK_20.game.Tools.LevelCreator;
import com.MK_20.game.Tools.WorldContactListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

    private AngryBirds game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private TmxMapLoader maploader;
    private TiledMap tiledmap;
    private OrthogonalTiledMapRenderer renderer;
    private ImageButton pauseButton;

    public World world;
    private Box2DDebugRenderer debugRenderer;
    public int index;

    private ImageButton sling;

    Level level;

    private Level loadLevel(int index) {
        switch (index) {
            case 1:
                return new Level1(world);
            case 2:
//                return new Level2(world);
            case 3:
//                return new Level3(world);
        }
        return null;
    }

    public PlayScreen(AngryBirds game, int index) {
        this.index = index;
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);
        stage = new Stage(viewport);
        viewport.apply();


        maploader = new TmxMapLoader();
        tiledmap = maploader.load("levels/Level"+index+"/level"+index+".tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledmap, 0.45f);

//        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.position.set(287.61798f, 250, 0);

        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("pause.png"))));
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("pause button clicked");
                game.setScreen(game.pause);
            }
        });

        sling = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("sling.png"))));

        Table table = new Table();
        table.top().left().padLeft(10).padTop(10);
        table.setFillParent(true);
        table.add(pauseButton).size(50,50);
        table.add(sling).size(60,140);
        stage.addActor(table);

        world = new World(new Vector2(0,-10), true);
        debugRenderer = new Box2DDebugRenderer();

        world.setContactListener(new WorldContactListener());

        new LevelCreator(world, tiledmap);
        level = loadLevel(index);
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.setScreen(game.won);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            game.setScreen(game.failed);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            level.birds.get(1).body.applyLinearImpulse(new Vector2(0,300f),level.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && level.birds.get(1).body.getLinearVelocity().x <= 2){
            level.birds.get(1).body.applyLinearImpulse(new Vector2(300f,0),level.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && level.birds.get(1).body.getLinearVelocity().x >= -2){
            level.birds.get(1).body.applyLinearImpulse(new Vector2(-300f,0),level.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && level.birds.get(1).body.getLinearVelocity().x >= -2){
            level.birds.get(1).body.applyLinearImpulse(new Vector2(0f,-300),level.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.justTouched()){
            System.out.println(level.birds.get(1).body.getPosition().x);
            System.out.println(level.birds.get(1).body.getPosition().y);
            System.out.println("Window width: "+Gdx.graphics.getWidth());
            System.out.println("World width: "+viewport.getWorldWidth());
//            System.out.println(10000*delta);
//            camera.position.x+=(10000*delta);
        }
    }

    public void update(float delta) {
        handleInput(delta);

        world.step(1/60f, 6, 2);
//Update implementation.
        level.update(delta);

        camera.position.set(2*320, 250,0);
//        camera.position.x=p.body.getPosition().x;
//        camera.position.y=p.body.getPosition().y;
        camera.update();
        renderer.setView(camera);
        pauseButton.setPosition(camera.position.x - viewport.getWorldWidth() / 2 + 10, camera.position.y + viewport.getWorldHeight() / 2 - 60);

        sling.setPosition(474.0f, 62.0f);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //rendering game map
        renderer.render();

        //box 2D debug lines.
        debugRenderer.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        //First the stage so that sling comes behind any bird.
        stage.act();
        stage.draw();
        //draw birds and pigs.
        level.drawAll(game.batch);
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
        tiledmap.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        stage.dispose();
    }
}
