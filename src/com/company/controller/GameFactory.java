package com.company.controller;

import com.company.controller.Controller;
import com.company.controller.ReversiController;
import com.company.controller.TicTacToeController;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameFactory {
    public Controller makeGame(String game, VBox ta, Stage stage, String gameMode) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController(ta, stage, gameMode);
            case "Reversi":
                return new ReversiController(ta, stage, gameMode);
        }
        return null;
    }
}
