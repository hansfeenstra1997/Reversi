package com.company.controller;

import com.company.controller.Controller;
import com.company.controller.ReversiController;
import com.company.controller.TicTacToeController;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameFactory {
    public GameController makeGame(String game) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController();
            case "Reversi":
                return new ReversiController();
        }
        return null;
    }
}
