package com.yanick.nicolas.eric.memory;

/**
 * Created by ysevigny on 2/8/2015.
 */
public class Player {
    public String Name;
    public int Score = 0;

    public Player(String name){
        Name = name;
    }

    public Player(String name, int score){
        Name = name;
        Score = score;
    }
}
