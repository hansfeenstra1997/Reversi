package com.company;

import javafx.stage.Stage;

public class TicTacToeController extends Controller{


    public TicTacToeController(Stage gameStage) {
        super(gameStage);

        makeBoard(3);
        ai = new AIPlayerMiniMax(board);
    }

    @Override
    void setMove(int pos) {
        //controleren of move kan

        conn.sendCommand("move " + pos);
        updateBoard();

    }

}
