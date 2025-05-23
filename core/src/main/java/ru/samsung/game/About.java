package ru.samsung.game;

import static ru.samsung.game.Main.SCR_HEIGHT;
import static ru.samsung.game.Main.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

public class About implements Screen {

    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;

    Texture imgBG;
    Button btnBack;

    public About(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;

        imgBG = new Texture("About.jpg");
        btnBack = new Button(font, "EXIT", 20,200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(btnBack.hit(touch.x, touch.y)){
                main.setScreen(main.menu);
            }
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "ABOUT", 0, 1500,SCR_WIDTH, Align.center, true);
        font.draw(batch, "GAME", 0, 1200,SCR_WIDTH, Align.center, true);
        font.draw(batch, "MADE", 0, 1100,SCR_WIDTH, Align.center, true);
        font.draw(batch, "BY", 0, 1000,SCR_WIDTH, Align.center, true);
        font.draw(batch, "KIRILL DENISOV", 0, 900 ,SCR_WIDTH, Align.center, true);
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
