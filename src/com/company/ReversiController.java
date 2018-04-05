package com.company;

import javafx.stage.Stage;

public class ReversiController extends Controller{


    public ReversiController(Stage gameStage) {
        super(gameStage);

        makeBoard(8);
        ai = new AIPlayerMiniMax(board);
    }

    @Override
    void setMove(int pos) {
        //controlerne of move mogelijk is

        conn.sendCommand("move " + pos);
        updateBoard();
    }

    @Override
    String setCellImage(int state) {
        if(state == firstPlayerID){
            return "B";
        } else if(state == secondPlayerID){
            return "W";
        }
        return "";
    }
}
