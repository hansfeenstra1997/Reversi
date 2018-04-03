package com.company;

public class GameFactory {
    public void makeGame(String game) {
        switch(game) {
            case "TicTacToe":
                new TicTacToe();
                break;
        }
    }
}
