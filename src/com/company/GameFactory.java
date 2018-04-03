package com.company;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameFactory {
    public Runnable makeGame(String game, Stage stage, BorderPane pane, VBox main, GridPane grid) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController(stage, pane, main, grid);
        }
        return null;
    }
}
