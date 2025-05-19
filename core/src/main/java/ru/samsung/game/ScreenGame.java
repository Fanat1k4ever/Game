package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScreenGame implements Screen {

    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;
    private boolean isGameOver;

    Texture currentLivesTexture;
    Texture imgBG;
    Texture imgH3;
    Texture imgH2;
    Texture imgH1;
    Texture imgH0;
    Texture imgAstro;
    Texture imgEnemyAtlas;
    TextureRegion[][] imgEnemy = new TextureRegion[3][27];

    Button btnBack;

    Sound sndHitAlien;
    Sound sndHitCan;
    Sound sndHit;
    Music music;
    Sound sndDead;

    Space[] space = new Space[2];
    Astron astron;
    List<Enemy> enemies = new ArrayList<>();

    private long timeLastSpawnEnemy, timeSpawnEnemyInterval = 100;

    public ScreenGame(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        Gdx.input.setInputProcessor(new Processor());

        imgBG = new Texture("Game.jpg");
        imgH3 = new Texture("h3.png");
        imgH2 = new Texture("h2.png");
        imgH1 = new Texture("h1.png");
        imgH0 = new Texture("h0.png");
        currentLivesTexture = imgH3;
        imgAstro = new Texture("astron.png");
        imgEnemyAtlas = new Texture("enemy_atlas.png");
        for (int j = 0; j < imgEnemy.length; j++) {
            for (int i = 0; i < imgEnemy[j].length; i++) {
                imgEnemy[j][i] = new TextureRegion(imgEnemyAtlas, (i<15?i:27-i)*36, (j+1)*36, 36, 36);
            }
        }
        sndHit = Gdx.audio.newSound(Gdx.files.internal("classic_hurt.mp3"));
        sndHitAlien = Gdx.audio.newSound(Gdx.files.internal("alien.mp3"));
        sndHitCan = Gdx.audio.newSound(Gdx.files.internal("garbagecan.mp3"));
        sndDead = Gdx.audio.newSound(Gdx.files.internal("dead.mp3"));
        btnBack = new Button(font, "X", 820,1575);
        space[0] =new Space(0,0);
        space[1] = new Space(0, SCR_HEIGHT);
        astron = new Astron(SCR_WIDTH/2, 200);
    }

    @Override
    public void show() {
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
        spawnEnemy();
        for (int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).move();
            if (enemies.get(i).outOfScreen() || enemies.get(i).overlap(astron)) {
                enemies.remove(i);
                sndDead.play();
                if (!isGameOver) gameOver();
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
        if(isGameOver){
            font.draw(batch, "GAME OVER", 0, 1300, SCR_WIDTH, Align.center, true);
        }
        /*for (int i = 0; i < ship.hp; i++) {
            batch.draw(imgShip, SCR_WIDTH-i*70-140, 1600-70, 60, 60);
        }*/
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y);
        batch.end();
    }
    private void updateLivesTexture() {
        switch (astron.getLives()) {
            case 3:
                currentLivesTexture = imgH3;
                batch.draw(imgH3,0,1200);
                break;
            case 2:
                currentLivesTexture = imgH2;
                batch.draw(imgH2,0,1200);
                break;
            case 1:
                currentLivesTexture = imgH1;
                batch.draw(imgH1,0,1200);
                break;
            case 0:
                currentLivesTexture = imgH0;
                batch.draw(imgH0,0,1200);
                break;
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
        if (imgH0 != null) imgH0.dispose();
        if (imgAstro != null) imgAstro.dispose();
        if (imgBG != null) imgBG.dispose();
        if (imgH1 != null) imgH1.dispose();
        if (imgEnemyAtlas != null) imgEnemyAtlas.dispose();
        if (imgH2 != null) imgH2.dispose();
        if (imgH3 != null) imgH3.dispose();
        if (sndHit != null) sndHit.dispose();
        if (sndHitAlien != null) sndHitAlien.dispose();
        if (sndHitCan != null) sndHitCan.dispose();
        if (music != null) music.dispose();
    }
}
