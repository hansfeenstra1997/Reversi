package com.company;

public class GameFactory {
    public Runnable makeGame(String game) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController();
        }
        return null;
    }
}
