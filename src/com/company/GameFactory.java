package com.company;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameFactory {
    public Controller makeGame(String game, VBox ta, Stage stage) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController(ta, stage);
            case "Reversi":
                return new ReversiController(ta, stage);
        }
        return null;
    }
}
