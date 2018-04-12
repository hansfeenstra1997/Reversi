package com.company.controller;

import com.company.AiPlayerTicTacToe;
import com.company.ManualPlayerTicTacToe;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TicTacToeController extends Controller{

    private int boardsize = 3;

    @Override
    void initBoard() {}
    @Override
    void flipBoard(int x, int y, int selfOrOpponent) {}

    public TicTacToeController(VBox ta, Stage gameStage, String gameMode) {
        super(ta, gameStage);

        makeBoard(boardsize);

        this.gameMode = gameMode;

        switch(gameMode) {
            case "manual":
                player = new ManualPlayerTicTacToe(board);
                break;
            case "ai-easy":
                player = new AiPlayerTicTacToe(board, "easy");
                break;
            case "ai-hard":
                player = new AiPlayerTicTacToe(board, "hard");
                break;
            default:
                player = new ManualPlayerTicTacToe(board);
        }
    }

    @Override
    void setMove(int pos) {
        System.out.println("move " + pos);
        player.doMove(pos);

        updateBoard();
    }

    @Override
    String getGameName(){
        return "Tic-tac-toe";
    }

    @Override
    ArrayList<int[]> getPossibleMoves() {
        return new ArrayList<>();
    }

    @Override
    Image setCellImage(int state) {
        if(state == firstPlayerID){
            return new Image("/X.png", 40, 40, false, false);
        } else if(state == secondPlayerID){
            return new Image("/O.png",  40, 40, false, false);
        }
        return null;
    }
}
