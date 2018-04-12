package com.company.controller;

import com.company.connection.Connection;
import com.company.model.Board;
import javafx.scene.image.Image;
import java.util.ArrayList;

public abstract class GameController {

    Connection conn;

    Board board;

    //startingPlayer = player with first move
    //nextPlayer = player plays second
    //Players need to be refactored
    protected String firstPlayer;
    protected String secondPlayer;
    protected int firstPlayerID;
    protected int secondPlayerID;

    GameController(){
        conn = Connection.getInstance();
    }

    abstract void initBoard();
    abstract void flipBoard(int x, int y, int selfOrOpponent);
    abstract void setMove(int pos);
    abstract ArrayList<int[]> getPossibleMoves();
    abstract Image setCellImage(int state);
    abstract String getGameName();

    public void makeBoard(int size) {
        board = new Board(size);
    }

    public void setPlayers(String fp, String sp, int fpID, int spID){
        firstPlayer = fp;
        secondPlayer = sp;
        firstPlayerID = fpID;
        secondPlayerID = spID;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public int getFirstPlayerID() {
        return firstPlayerID;
    }

    public int getSecondPlayerID(){
        return secondPlayerID;
    }

}
