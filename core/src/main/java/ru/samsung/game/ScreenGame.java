package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;


public class ScreenGame implements Screen {
    private final Main main;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Vector3 touch;
    private final BitmapFont font;
    private final BitmapFont font50white;
    private long timeLastSpawnEnemy, timeSpawnEnemyInterval = 1500;

    private boolean isGameOver;
    private boolean isGlobal;

    Joystick joystick;

    Texture imgJoystick;
    Texture imgBG;
    Texture imgAseroidAtlas;
    Texture imgAstro;
    TextureRegion[][] imgAster = new TextureRegion[4][12];

    Button btnBack;
    Button btnGlobal;

    Space[] space = new Space[2];
    Astronaut astronaut;
    List<Enemy> enemies = new ArrayList<>();
    Player[] players = new Player[10];

    public ScreenGame(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font50white = main.font50white;
        font = main.font;
        Gdx.input.setInputProcessor(new Processor());
        joystick = new Joystick(360, RIGHT);

        imgBG = new Texture("Game.jpg");
        imgAstro = new Texture("astron.png");
        imgAseroidAtlas = new Texture("Asteroid_Atlas.png");
        btnBack = new Button(font50white, "X", 820, 1575);
        for (int j = 0; j < imgAster.length; j++) {
            for (int i = 0; i < imgAster[j].length; i++) {
                imgAster[j][i] = new TextureRegion(imgAseroidAtlas, (i < 7 ? i : 12 - i) * 400, (j + 1) * 400, 400, 400);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if (btnBack.hit(touch.x, touch.y)) {
                main.setScreen(main.menu);
            }
        }
        for (Space s : space) s.move();
        spawnEnemy();
        for (int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).move();
            if (enemies.get(i).outOfScreen() || enemies.get(i).overlap(astronaut)) {
                enemies.remove(i);
                if (!isGameOver) gameOver();
            }
        }
        if (!isGameOver) {
            astronaut.move();
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Space s : space) batch.draw(imgBG, s.x, s.y, s.width, s.height);
        if (controls == JOYSTICK) {
            batch.draw(imgJoystick, joystick.scrX(), joystick.scrY(), joystick.width, joystick.height);
        }
        for (Enemy e : enemies) {
            batch.draw(imgAster[e.type][e.phase], e.scrX(), e.scrY(), e.width, e.height);
        }
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y);
        batch.end();
    }

    private void spawnEnemy() {
        if (TimeUtils.millis() > timeLastSpawnEnemy + timeSpawnEnemyInterval) {
            timeLastSpawnEnemy = TimeUtils.millis();
            enemies.add(new Enemy());
        }
    }
    private void gameStart(){
        isGameOver = false;
        isGlobal = false;
        astronaut = new Astronaut(SCR_WIDTH/2, 200);
        main.player.clear();
        enemies.clear();
    }

    private void gameOver() {
        isGameOver = true;
        astronaut.x = -10000;
    }

    class Processor implements InputProcessor {

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
            touch.set(screenX, screenY, 0);
            camera.unproject(touch);
            if (controls == JOYSTICK) {
                if (joystick.isTouchInside(touch)) {
                    astronaut.touchJoystick(touch, joystick);
                }
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            astronaut.stop();
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            touch.set(screenX, screenY, 0);
            camera.unproject(touch);
            if (controls == JOYSTICK) {
                if (joystick.isTouchInside(touch)) {
                    astronaut.touchJoystick(touch, joystick);
                }
            }
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
        imgAseroidAtlas.dispose();
        imgJoystick.dispose();
        imgBG.dispose();
    }
}
