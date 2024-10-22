package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Sprites.Pig;
import com.MK_20.game.Tools.LevelCreator;
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

    private World world;
    private Box2DDebugRenderer debugRenderer;
    public int index;

    private Pig p;


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
        Table table = new Table();
        table.top().left().padLeft(10).padTop(10);
        table.setFillParent(true);
        table.add(pauseButton).size(50,50);
        stage.addActor(table);

        world = new World(new Vector2(0,-10), true);
        debugRenderer = new Box2DDebugRenderer();

        new LevelCreator(world, tiledmap);

        p = new Pig(world);
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.setScreen(game.won);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            game.setScreen(game.failed);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            p.body.applyLinearImpulse(new Vector2(0,50f),p.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && p.body.getLinearVelocity().x <= 2){
            p.body.applyLinearImpulse(new Vector2(50f,0),p.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && p.body.getLinearVelocity().x >= -2){
            p.body.applyLinearImpulse(new Vector2(-50f,0),p.body.getWorldCenter(), true);
        }
        if (Gdx.input.justTouched()){
            System.out.println(p.body.getPosition().x);
            System.out.println(p.body.getPosition().y);
            System.out.println("Window width: "+Gdx.graphics.getWidth());
            System.out.println("World width: "+viewport.getWorldWidth());
//            System.out.println(10000*delta);
//            camera.position.x+=(10000*delta);
        }
    }

    public void update(float delta) {
        handleInput(delta);

        world.step(1/60f, 6, 2);
        camera.position.set(2*320, 250,0);
//        camera.position.x=p.body.getPosition().x;
//        camera.position.y=p.body.getPosition().y;
        camera.update();
        renderer.setView(camera);
        pauseButton.setPosition(camera.position.x - viewport.getWorldWidth() / 2 + 10, camera.position.y + viewport.getWorldHeight() / 2 - 60);

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

        //pause button.
        game.batch.setProjectionMatrix(camera.combined);
//        stage.act();
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
        tiledmap.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        stage.dispose();
    }
}
