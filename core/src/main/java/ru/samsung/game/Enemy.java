package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends Object {
    public int phase, nPhases = 27;
    private long timeLastPhase, timePhaseInterval = 40;

    public Enemy() {
        type = MathUtils.random(0, 2);
        settingsEnemy(type);
        x = MathUtils.random(width / 2, SCR_WIDTH - width / 2);
        y = MathUtils.random(SCR_HEIGHT + height, SCR_HEIGHT * 2);
    }

    @Override
    public void move() {
        super.move();
        changePhase();
    }

    public boolean outOfScreen() {
        return y < -height / 2;
    }

    public void changePhase() {
        if (TimeUtils.millis() > timeLastPhase + timePhaseInterval) {
            if (++phase == nPhases) phase = 0;
            timeLastPhase = TimeUtils.millis();
        }
    }
    private void settingsEnemy(int type){
        switch (type){
            case 0:
                width = height = 165;
                vy = MathUtils.random(-7f, -5f);
                break;
            case 1:
                width = height = 185;
                vy = MathUtils.random(-4f, -3f);
                break;
            case 2:
                width = height = 150;
                vy = MathUtils.random(-6f, -4f);
                break;
        }
    }
}
