package com.company;

public abstract class Player {

    private String playerName;

    public Player(String name){
        playerName = name;
    }

    abstract void doMove(int position);

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
