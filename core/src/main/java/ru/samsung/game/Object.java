package ru.samsung.game;

public class Object {
    public float x, y;
    public float width, height;
    public float vx, vy;
    public int type;

    public Object(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Object() {
    }

    public void move(){
        x += vx;
        y += vy;
    }

    public float scrX() {
        return x;
    }

    public float scrY() {
        return y;
    }

    public boolean overlap(Object o){
        return Math.abs(x-o.x) < width/2+o.width/2 && Math.abs(y-o.y) < height/4+o.height/4;
    }
}
