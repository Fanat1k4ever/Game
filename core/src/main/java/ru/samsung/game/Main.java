package ru.samsung.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Main extends Game {
    public static final float SCR_WIDTH = 900;
    public static final float SCR_HEIGHT = 1600;
    public static final int SCREEN = 0, JOYSTICK = 1, ACCELEROMETER = 2;
    public static final boolean LEFT = false, RIGHT = true;
    public static int controls = SCREEN;
    public static final int PERIODICALLY = 0, BY_BUTTON = 1;
    public static boolean isSoundOn = true;

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public Vector3 touch;
    public BitmapFont font;
    public BitmapFont font50white;
    public BitmapFont font100dark;

    public Menu menu;
    public ScreenGame screenGame;
    public Settings settings;
    public LeaderBoard leaderBoard;
    public About about;
    public Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("mss.fnt"));
        font50white = new BitmapFont(Gdx.files.internal("dscrystal50white.fnt"));
        font100dark = new BitmapFont(Gdx.files.internal("font100dark.fnt"));

        player = new Player();
        menu = new Menu(this);
        screenGame = new ScreenGame(this);
        settings = new Settings(this);
        leaderBoard = new LeaderBoard(this);
        about = new About(this);
        setScreen(menu);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
