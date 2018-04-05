package com.company;

import javafx.stage.Stage;

public class TicTacToeController extends Controller{

    private int boardsize = 3;

    @Override
    void initBoard() {}

    public TicTacToeController(Stage gameStage) {
        super(gameStage);

        makeBoard(boardsize);
        ai = new AIPlayerMiniMax(board);
    }

    @Override
    void setMove(int pos) {
        //controleren of move kan

        conn.sendCommand("move " + pos);
        updateBoard();
    }

    //needs to return Image
    @Override
    String setCellImage(int state) {
        if(state == firstPlayerID){
            return "X";
        } else if(state == secondPlayerID){
            return "O";
        }
        return "";
    }

    void makeAI(Board board) {

    }

}
