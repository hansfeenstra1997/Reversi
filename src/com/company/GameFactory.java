package com.company;

import javafx.stage.Stage;

public class GameFactory {
    public Controller makeGame(String game, Stage stage) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController(stage);
            case "Reversi":
                return new ReversiController(stage);
        }
        return null;
    }
}
