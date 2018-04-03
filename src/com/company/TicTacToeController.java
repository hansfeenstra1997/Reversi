package com.company;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToeController extends Controller{

    //Game game;
//    Model model;


    public TicTacToeController(Stage gameStage, BorderPane gamePane, VBox gameMain, GridPane gameGrid) {
        super();
        stage = gameStage;
        pane = gamePane;
        main = gameMain;
        grid = gameGrid;

        makeBoard(3);
    }

    public void makeBoard(int size) {
        board = new Board(size);
        board.printBoard();
    }

    @Override
    void setMove(int pos) {
        //controleren of move kan

        conn.sendCommand("move " + pos);
        //board.setPosition(1,pos);
        updateBoard();

    }



    void updateView(){

    }


}
