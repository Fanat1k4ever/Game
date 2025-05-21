package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Predicate;

import java.util.prefs.PreferenceChangeEvent;

public class Settings implements Screen {

    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;
    private BitmapFont font70;
    private BitmapFont font70dark;
    private BitmapFont font100dark;


    Texture imgBG;

    Button btnControls;
    Button btnScreen;
    Button btnBack;
    Button btnAccelerometer;
    Button btnSound;

    public Settings(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        font100dark = main.font100dark;
        font70 = main.font70;
        font70dark = main.font70dark;

        imgBG = new Texture("Settings.jpg");
        btnControls = new Button(font, "Controls", 50,1200);
        btnScreen = new Button(font, "Screen", 150, 1100);
        btnAccelerometer = new Button(font70dark, "Accelerometer", 150, 1000);
        btnSound = new Button(font,isSoundOn?"Sound ON" : "Sound OFF", 50, 900);
        btnBack = new Button(font, "EXIT", 150);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(btnScreen.hit(touch)){
                btnScreen.setFont(font);
                btnAccelerometer.setFont(font70dark);
                controls = SCREEN;
            }
            if(btnAccelerometer.hit(touch)){
                btnScreen.setFont(font100dark);
                btnAccelerometer.setFont(font70);
                controls = ACCELEROMETER;
            }
            if(btnSound.hit(touch.x, touch.y)){
                isSoundOn = !isSoundOn;
                btnSound.setText(isSoundOn?"Sound ON" : "Sound OFF");
            }
            if(btnBack.hit(touch.x, touch.y)){
                main.setScreen(main.menu);
            }
        }
        // Отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "SETTINGS", 0, 1500,SCR_WIDTH, Align.center, true);
        btnControls.font.draw(batch, btnControls.text, btnControls.x, btnControls.y);
        btnScreen.font.draw(batch, btnScreen.text, btnScreen.x, btnScreen.y);
        btnSound.font.draw(batch, btnSound.text, btnSound.x, btnSound.y);
        btnAccelerometer.font.draw(batch, btnAccelerometer.text, btnAccelerometer.x, btnAccelerometer.y);
        btnBack.font.draw(batch,btnBack.text, btnBack.x, btnBack.y);
        batch.end();
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
