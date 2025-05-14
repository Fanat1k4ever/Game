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

public class Settings implements Screen {

    Main main;
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;
    BitmapFont fontDarkGreen;
    BitmapFont font50white;

    InputKeyboard keyboard;

    Texture imgBG;

    Button btnPlayerName;
    Button btnControls;
    Button btnScreen;
    Button btnJoystick;
    Button btnByButton;
    Button btnBack;

    public Settings(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        fontDarkGreen = main.font100dark;
        font50white = main.font50white;

        keyboard = new InputKeyboard(font50white, SCR_WIDTH, SCR_HEIGHT/2, 9);

        imgBG = new Texture("Settings.jpg");

        loadSettings();

        btnPlayerName = new Button(font, "Name: "+main.player.name, 100, 1250);
        btnControls = new Button(font, "Controls", 100, 1100);
        btnScreen = new Button(controls==SCREEN? font :fontDarkGreen, "Screen", 200, 1000);
        btnJoystick = new Button(controls==JOYSTICK? font :fontDarkGreen, joystickText(), 200, 900);
        btnBack = new Button(font, "Back", 150);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(keyboard.isKeyboardShow) {
                if (keyboard.touch(touch)) {
                    main.player.name = keyboard.getText();
                    btnPlayerName.setText("Name: " + main.player.name);
                }
            } else {
                if (btnPlayerName.hit(touch)) {
                    keyboard.start();
                }
                if (btnScreen.hit(touch)) {
                    btnScreen.setFont(font);
                    btnJoystick.setFont(fontDarkGreen);
                    controls = SCREEN;
                }
                if (btnJoystick.hit(touch)) {
                    btnScreen.setFont(fontDarkGreen);
                    btnJoystick.setFont(font);
                    if (controls == JOYSTICK) {
                        main.screenGame.joystick.setSide(!main.screenGame.joystick.side);
                        btnJoystick.setText(joystickText());
                    } else {
                        controls = JOYSTICK;
                    }
                }
                if (btnBack.hit(touch.x, touch.y)) {
                    main.setScreen(main.menu);
                }
            }
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch,"Settings", 0, 1500, SCR_WIDTH, Align.center, true);
        btnPlayerName.font.draw(batch, btnPlayerName.text, btnPlayerName.x, btnPlayerName.y);
        btnControls.font.draw(batch, btnControls.text, btnControls.x, btnControls.y);
        btnScreen.font.draw(batch, btnScreen.text, btnScreen.x, btnScreen.y);
        btnJoystick.font.draw(batch, btnJoystick.text, btnJoystick.x, btnJoystick.y);
        btnByButton.font.draw(batch, btnByButton.text, btnByButton.x, btnByButton.y);
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y);
        keyboard.draw(batch);
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
        saveSettings();
    }

    @Override
    public void dispose() {
        imgBG.dispose();
        keyboard.dispose();
    }

    private String joystickText() {
        return main.screenGame.joystick.side == RIGHT ? "Joystick RIGHT" : "Joystick LEFT";
    }

    private String soundText() {
        return isSoundOn ? "Sound On" : "Sound Off";
    }

    private void saveSettings() {
        Preferences prefs = Gdx.app.getPreferences("Settings");
        prefs.putString("Name", main.player.name);
        prefs.putInteger("Controls", controls);
        prefs.putBoolean("Joystick", main.screenGame.joystick.side);
        prefs.flush();
    }

    private void loadSettings() {
        Preferences prefs = Gdx.app.getPreferences("Settings");
        main.player.name = prefs.getString("Name", "NONAME");
        controls = prefs.getInteger("Controls", SCREEN);
        main.screenGame.joystick.setSide(prefs.getBoolean("Joystick", RIGHT));
    }
}
