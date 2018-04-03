package com.company;

public class GameFactory {
    public Controller makeGame(String game) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController();
        }
        return null;
    }
}
