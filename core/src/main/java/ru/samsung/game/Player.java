package ru.samsung.game;

public class Player {
    String name = "Noname";
    long time;

    public void clear(){
        time = 0;
    }

    public Player() {
    }

    public Player(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public void set(String name, long time) {
        this.name = name;
        this.time = time;
    }
}
