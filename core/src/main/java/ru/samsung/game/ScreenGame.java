package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ScreenGame implements Screen {

    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;
    private BitmapFont fontnew;
    private boolean isGameOver;
    private long timeStartGame, timeCurrent;

    Texture currentLivesTexture;
    Texture imgBG;
    Texture imgH3;
    Texture imgH2;
    Texture imgH1;
    Texture imgH0;
    Texture imgAstro;
    Texture imgEnemyAtlas;
    TextureRegion[][] imgEnemy = new TextureRegion[4][27];

    Button btnBack;

    Sound sndHit;
    Music music;
    Sound sndDead;

    Space[] space = new Space[2];
    Astron astron;
    List<Enemy> enemies = new ArrayList<>();
    Player[] players = new Player[10];

    private long timeLastSpawnEnemy, timeSpawnEnemyInterval = 1500;

    public ScreenGame(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        fontnew = main.fontnew;
        Gdx.input.setInputProcessor(new Processor());
        timeCurrent = 0;

        imgBG = new Texture("Game.jpg");
        currentLivesTexture = imgH3;
        imgAstro = new Texture("astron.png");
        imgEnemyAtlas = new Texture("enemy_atlas.png");
        for (int j = 0; j < imgEnemy.length; j++) {
            for (int i = 0; i < imgEnemy[j].length; i++) {
                imgEnemy[j][i] = new TextureRegion(imgEnemyAtlas, (i<15?i:27-i)*36, (j+1)*36, 36, 36);
            }
        }
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        sndHit = Gdx.audio.newSound(Gdx.files.internal("classic_hurt.mp3"));
        sndDead = Gdx.audio.newSound(Gdx.files.internal("dead.mp3"));
        btnBack = new Button(font, "X", 820,1575);
        space[0] =new Space(0,0);
        space[1] = new Space(0, SCR_HEIGHT);
        astron = new Astron(SCR_WIDTH/2, 200);
    }

    @Override
    public void show() {
        timeStartGame = TimeUtils.millis();
        isGameOver = false;
        astron = new Astron(SCR_WIDTH/2, 200);
        main.player.clear();
        enemies.clear();
    }

    @Override
    public void render(float delta) {
        // касания
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if (btnBack.hit(touch)) {
                main.setScreen(main.menu);
            }
        }
        if (controls == ACCELEROMETER) {
            astron.vx = -Gdx.input.getAccelerometerX() * 5;
            astron.vy = -Gdx.input.getAccelerometerY() * 5;
        }
        // события
        for (Space s : space) s.move();
        timeCurrent = TimeUtils.millis() - timeStartGame;
        spawnEnemy();
        for (int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).move();
            if (enemies.get(i).overlap(astron)) {
                enemies.remove(i);
                if (isSoundOn) sndDead.play();
                if (!isGameOver) gameOver();
            }
            if (enemies.get(i).outOfScreen()) {
                enemies.remove(i);
            }
        }
        if (!isGameOver) {
            astron.move();
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Space s: space) batch.draw(imgBG, s.x, s.y, s.width, s.height);
        for (Enemy e: enemies) {
            batch.draw(imgEnemy[e.type][e.phase], e.scrX(), e.scrY(), e.width, e.height);
        }
        batch.draw(imgAstro, astron.scrX(), astron.scrY(), astron.width, astron.height);
        fontnew.draw(batch, "Score: "+ showTime(timeCurrent), 10, 1580);
        if(isGameOver){
            font.draw(batch, "GAME OVER", 0, 1300, SCR_WIDTH, Align.center, true);
            timeStartGame = TimeUtils.millis();
        }
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y);
        batch.end();
    }

    private void gameOver(String name) {
        players[players.length-1].name = name;
        players[players.length-1].time = timeCurrent;
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
    String showTime(long time){
        long msec = (time % 1000) / 10;
        long sec = (time / 1000) % 60;
        long min = (time / (1000 * 60)) % 60;
        return String.format("%02d:%02d:%02d", min, sec, msec);
    }

    private void spawnEnemy() {
        if(TimeUtils.millis() > timeLastSpawnEnemy + timeSpawnEnemyInterval) {
            Enemy newEnemy = new Enemy();
            enemies.add(newEnemy);
            timeLastSpawnEnemy = TimeUtils.millis();
        }
    }

    private void gameOver(){
        if (isSoundOn) sndHit.play();
        isGameOver = true;
        astron.x = -10000;
    }

    class Processor implements InputProcessor{

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
            if(controls == SCREEN) {
                touch.set(screenX, screenY, 0);
                camera.unproject(touch);
                astron.touch(touch);
            }
                return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            astron.stop();
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if(controls == SCREEN) {
                touch.set(screenX, screenY, 0);
                camera.unproject(touch);
                astron.touch(touch);
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
    public void dispose() {
        if (batch != null) batch.dispose();
        if (imgAstro != null) imgAstro.dispose();
        if (imgBG != null) imgBG.dispose();
        if (imgEnemyAtlas != null) imgEnemyAtlas.dispose();
        if (sndHit != null) sndHit.dispose();
        if (music != null) music.dispose();
    }
}
