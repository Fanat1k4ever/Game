package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

public class Menu implements Screen {

    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;

    Texture imgBG;
    Button btnPlay;
    Button btnSettings;
    Button btnLeaderboard;
    Button btnAbout;
    Button btnExit;

    public Menu(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;

        imgBG = new Texture("MenuScreen.jpg");
        btnPlay = new Button(font, "PLAY", 50,1100);
        btnSettings = new Button(font, "SETTINGS", 50,950);
        btnAbout = new Button(font, "ABOUT", 50,800);
        btnExit = new Button(font, "EXIT", 50,650);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(btnPlay.hit(touch.x, touch.y)){
                main.setScreen(main.screenGame);
            }
            if(btnSettings.hit(touch.x, touch.y)){
                main.setScreen(main.settings);
            }
            if(btnAbout.hit(touch.x, touch.y)){
                main.setScreen(main.about);
            }
            if(btnExit.hit(touch.x, touch.y)){
                Gdx.app.exit();
            }
        }
        // Отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, " A LONG FLY", 0, 1500,SCR_WIDTH, Align.center, true);
        btnPlay.font.draw(batch,btnPlay.text, btnPlay.x, btnPlay.y);
        btnSettings.font.draw(batch,btnSettings.text, btnSettings.x, btnSettings.y);
        btnAbout.font.draw(batch,btnAbout.text, btnAbout.x, btnAbout.y);
        btnExit.font.draw(batch,btnExit.text, btnExit.x, btnExit.y);
        batch.end();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    }
}
