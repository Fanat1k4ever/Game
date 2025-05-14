package ru.samsung.game;

import java.util.Arrays;

public class Player {
    String name = "Noname";
    int score;

    public void clear(){
        score = 0;
    }

    public void clone(Player p) {
        name = p.name;
        score = p.score;
    }
}
