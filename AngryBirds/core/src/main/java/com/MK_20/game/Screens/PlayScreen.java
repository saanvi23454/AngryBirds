package com.MK_20.game.Screens;

import com.MK_20.game.AngryBirds;
import com.MK_20.game.Levels.Level;
import com.MK_20.game.Sprites.Slingshot;
import com.MK_20.game.Tools.LevelCreator;
import com.MK_20.game.Tools.WorldContactListener;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.gdxUnBox2d.Box2dWorldContactListener;

public class PlayScreen implements Screen, InputProcessor {

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

    public Level level;

    public TiledMap getTiledmap() {
        return tiledmap;
    }

    public PlayScreen(AngryBirds game, int index) {
        this.index = index;
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.WIDTH, AngryBirds.HEIGHT, camera);
        stage = new Stage(viewport);
        viewport.apply();

        maploader = new TmxMapLoader();
        tiledmap = maploader.load("levels/Level1/level"+index+".tmx");
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

//        sling = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("sling.png"))));

        Table table = new Table();
        table.top().left().padLeft(10).padTop(10);
        table.setFillParent(true);
        table.add(pauseButton).size(50,50);
//        table.add(sling).size(60,140);
        stage.addActor(table);

        world = new World(new Vector2(0,-10), true);
//        for (int i = 0; i < 4; i++) {
//            world.step(1 / 60f, 6, 2); // Default values are 6 velocity and 2 position iterations
//        }
//        world.setGravity(new Vector2(0,-20f));

        debugRenderer = new Box2DDebugRenderer();

//        slingshot = new Slingshot(world, 330f, 85f);
        LevelCreator l = new LevelCreator(world, tiledmap);
        level = new Level(l);
//        level.setBird(slingshot);
//        level.addWoods(l.getWoods());
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.setScreen(game.won);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            game.setScreen(game.failed);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            level.levelCreator.birds.get(1).body.applyLinearImpulse(new Vector2(0,50f),level.levelCreator.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && level.levelCreator.birds.get(1).body.getLinearVelocity().x <= 2){
            level.levelCreator.birds.get(1).body.applyLinearImpulse(new Vector2(50f,0),level.levelCreator.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && level.levelCreator.birds.get(1).body.getLinearVelocity().x >= -2){
            level.levelCreator.birds.get(1).body.applyLinearImpulse(new Vector2(-50f,0),level.levelCreator.birds.get(1).body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            level.handleSpecialFeature();
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

//        sling.setPosition(474.0f, 62.0f);
        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this); // Add PlayScreen as the first processor
        multiplexer.addProcessor(stage); // Add stage as the second processor
        Gdx.input.setInputProcessor(multiplexer);
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


//slingshot
        level.levelCreator.slingshot.renderTrajectory(camera, level.currentBird);

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
    Vector2 coords = viewport.unproject(new Vector2(x, y));

        if (coords.y > 900/AngryBirds.PPM) {
            return false;
        }
        Vector2 velocity = level.levelCreator.slingshot.launchVelocity();
        level.releaseBird(velocity, level.levelCreator.slingshot);
        level.levelCreator.slingshot.release();
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        Gdx.app.log("Input", "Dragging");
        level.levelCreator.slingshot.startDrag(x,y);
        level.levelCreator.slingshot.updateDrag(x, y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
