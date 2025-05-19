package ru.samsung.game;

import static ru.samsung.game.Main.*;

public class Space extends Object{

    public Space(float x, float y) {
        super(x, y);
        width = SCR_WIDTH;
        height = SCR_HEIGHT+3;
        vy = -2;
    }

    public void move(){
        super.move();
        if(y<-SCR_HEIGHT) y = SCR_HEIGHT;
    }
}
