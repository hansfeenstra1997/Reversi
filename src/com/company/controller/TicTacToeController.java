package com.company.controller;

import com.company.AIPlayerMiniMax;
import com.company.model.Board;
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
    String getGameName(){
        return "Tic-tac-toe";
    }

    @Override
    ArrayList<int[]> getPossibleMoves() {
        return new ArrayList<>();
    }

    //needs to return Image
    @Override
    Image setCellImage(int state) {
        if(state == firstPlayerID){
            //return "X";
        } else if(state == secondPlayerID){
            //return "O";
        }
        //return "";
        return null;
    }

    void makeAI(Board board) {

    }

}
