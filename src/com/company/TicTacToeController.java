package com.company;

import javafx.scene.control.TextArea;
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

    public TicTacToeController(VBox ta, Stage gameStage) {
        super(ta, gameStage);

        makeBoard(boardsize);
        ai = new AIPlayerMiniMax(board);
    }

    @Override
    void setMove(int pos) {
        //controleren of move kan

        conn.sendCommand("move " + pos);
        updateBoard();
    }

    @Override
    ArrayList<int[]> getPossibleMoves() {
        return null;
    }

    //needs to return Image
    @Override
    Image setCellImage(int state) {
        if(state == firstPlayerID){
            //return "X";
            return new Image("");
        } else if(state == secondPlayerID){
            //return "O";
            return new Image("");
        }
        //return "";
        return null;
    }

    void makeAI(Board board) {

    }

}
