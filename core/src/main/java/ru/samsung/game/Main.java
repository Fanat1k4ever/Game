package ru.samsung.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Main extends Game {
    public static final float SCR_WIDTH = 900;
    public static final float SCR_HEIGHT = 1600;
    public static final int SCREEN = 0, ACCELEROMETER = 2;
    public static int controls = SCREEN;
    public static boolean isSoundOn = true;
    public Player[] players;

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public Vector3 touch;
    public BitmapFont font;
    public BitmapFont font100dark;
    public BitmapFont font70dark;
    public BitmapFont font70;
    public BitmapFont fontnew;

    public Menu menu;
    public ScreenGame screenGame;
    public Settings settings;
    public About about;
    Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("mss.fnt"));
        font100dark = new BitmapFont(Gdx.files.internal("font100dark.fnt"));
        font70dark = new BitmapFont(Gdx.files.internal("font70dark.fnt"));
        font70 = new BitmapFont(Gdx.files.internal("font70.fnt"));
        fontnew = new BitmapFont(Gdx.files.internal("fntg.fnt"));

        player = new Player();
        menu = new Menu(this);
        screenGame = new ScreenGame(this);
        settings = new Settings(this);
        about = new About(this);
        setScreen(menu);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        font70.dispose();
        font70dark.dispose();
    }
}
