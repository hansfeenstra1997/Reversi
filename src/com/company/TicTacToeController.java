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
    Stage stage;
    BorderPane pane;
    VBox main;
    GridPane grid;

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

        updateBoard();


        conn.sendCommand("move " + pos);
    }

    //of deze functie's moeten in Controller
    void updateBoard(){
        main.getChildren().clear();
        grid.getChildren().clear();

        System.out.println("board size: " + board.getSize());

        for(int y = 0; y < board.getSize(); y++){
            for(int x = 0; x < board.getSize(); x++){
                Button button = new Button();
                int state = board.getBoard()[x][y].getState();
                button.setText(Integer.toString(state));
                button.setOnAction((event)->{
                    int position = grid.getChildren().indexOf(event.getSource());
                    this.setMove(position);
                    System.out.println(position);
                });

                button.setMinSize(50,50);
                grid.add(button, x, y);
            }
        }

        main.getChildren().add(grid);

        stage.show();
    }

    void updateView(){

    }


}
