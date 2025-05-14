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

import java.util.List;

public class LeaderBoard implements Screen {

    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;
    private BitmapFont font50white;

    Texture imgBG;

    Button btnGlobal;
    Button btnClear;
    Button btnBack;

    private boolean isGlobal;
    Player[] players;

    public LeaderBoard(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        font50white = main.font50white;

        imgBG = new Texture("About.jpg");

        btnGlobal = new Button(font, "Local", 1350);
        btnClear = new Button(font, "Clear", 350);
        btnBack = new Button(font, "Back", 150);
    }

    @Override
    public void show() {
        isGlobal = false;
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(btnGlobal.hit(touch)){
                isGlobal = !isGlobal;
                if(isGlobal) {
                    main.screenGame.loadFromDataBase();
                    btnGlobal.setText("Global");
                } else {
                    btnGlobal.setText("Local");
                }
            }
            if(btnClear.hit(touch) && !isGlobal){
                main.screenGame.clearLeaderBoard();
                main.screenGame.saveLeaderBoard();
            }
            if(btnBack.hit(touch)){
                main.setScreen(main.screenMenu);
            }
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch,"Leader Board", 0, 1500, SCR_WIDTH, Align.center, true);
        btnGlobal.font.draw(batch, btnGlobal.text, btnGlobal.x, btnGlobal.y);
        font50white.draw(batch, "score", 450, 1220, 150, Align.right, false);
        font50white.draw(batch, "kills", 600, 1220, 150, Align.right, false);
        if(isGlobal){
            db = main.screenGame.db;
            if(db.size()>0) {
                for (int i = 0; i < main.screenGame.players.length; i++) {
                    font50white.draw(batch, i + 1 + " ", 140, 1150 - i * 80);
                    font50white.draw(batch, db.get(i).name, 200, 1150 - i * 80);
                    font50white.draw(batch, "" + db.get(i).score, 450, 1150 - i * 80, 150, Align.right, false);
                    font50white.draw(batch, "" + db.get(i).kills, 600, 1150 - i * 80, 150, Align.right, false);
                }
            }
        } else {
            for (int i = 0; i < main.screenGame.players.length; i++) {
                font50white.draw(batch, i + 1 + " ", 140, 1150 - i * 80);
                font50white.draw(batch, players[i].name, 200, 1150 - i * 80);
                font50white.draw(batch, "" + players[i].score, 450, 1150 - i * 80, 150, Align.right, false);
                font50white.draw(batch, "" + players[i].kills, 600, 1150 - i * 80, 150, Align.right, false);
            }
        }
        btnClear.font.draw(batch, btnClear.text, btnClear.x, btnClear.y);
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y);
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
        imgBG.dispose();
    }

}
