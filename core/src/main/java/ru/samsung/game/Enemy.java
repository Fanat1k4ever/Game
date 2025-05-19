package ru.samsung.game;

import static ru.samsung.game.Main.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends Object {
    public int hp;
    public int phase, nPhases = 27;
    private long timeLastPhase, timePhaseInterval = 40;

    public Enemy() {
        type = MathUtils.random(0, 2);
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
}
