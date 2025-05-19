package ru.samsung.game;

import java.util.Arrays;

public class Player {
    String name = "Noname";
    int score;
    int[] MPassed = new int[4];
    int kills;

    public void clear(){
        score = 0;
        Arrays.fill(MPassed, 0);
        kills = 0;
    }

    public void clone(Player p) {
        name = p.name;
        score = p.score;
        for (int i = 0; i < MPassed.length; i++) {
            MPassed[i] = p.MPassed[i];
        }
        kills = p.kills;
    }
}
