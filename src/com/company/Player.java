package com.company;

public abstract class Player {

    String playerName;

    public Player(String name){
        playerName = name;
    }

    abstract void doMove(int position);
}
