package ru.samsung.game;

import static ru.samsung.game.Main.SCR_HEIGHT;
import static ru.samsung.game.Main.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Astron extends Object{

    public Astron(float x, float y){
        super(x,y);
        width = 150;
        height = 170;
    }

    public float scrX(){
        return x-width/2;
    }

    public float scrY(){
        return y-height/2;
    }

    @Override
    public void move() {
        super.move();
        OutOfScreen();
    }
    public void touch(float tx, float ty){
        vx = (tx - x)/20;
        vy = (ty - y)/20;
    }
    public void touch(Vector3 t){
        vx = (t.x - x)/20;
        vy = (t.y - y)/20;
    }
    private void OutOfScreen(){
        if(x<width/2){
            vx =0;
            x = width/2;
        }
        if(x>SCR_WIDTH-width/2){
            vx = 0;
            x = SCR_WIDTH-width/2;
        }
        if(y<height/2){
            vy =0;
            y = height/2;
        }
        if(y>SCR_HEIGHT-height/2){
            vy = 0;
            y = SCR_HEIGHT-height/2;
        }
    }

    public void stop(){
        vx = 0;
        vy = 0;
    }
}
