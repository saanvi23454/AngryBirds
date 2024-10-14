package io.github.Screens;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {

    public static void main(String[] args) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "AngryBirds";
        config.useGL30 = true;
        config.width = 800;
        config.height = 600;
        config.resizable = true;

        new LwjglApplication(new AngryBirds(), config);
    }


    public HomeScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new HomeScreen(this);
        setScreen(gameScreen);
    }
}
