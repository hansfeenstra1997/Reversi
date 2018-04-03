package com.company;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameFactory {
    public Runnable makeGame(String game, Stage stage) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController(stage);
            case "Reversi":
                return new ReversiController(stage);
        }
        return null;
    }
}
