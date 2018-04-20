package com.company.controller;

import com.company.Player;
import com.company.connection.Connection;
import com.company.model.Board;
import javafx.scene.image.Image;

public abstract class GameController {

    Connection conn;

    Board board;

    //Players need to be refactored
    private String firstPlayer;
    private String secondPlayer;
    int firstPlayerID;
    int secondPlayerID;

    //Player section
    protected Player player;
    String gameMode;

    /**
     * Constructor of GameController
     * Initializes the connection
     */
    GameController(){
        conn = Connection.getInstance();
    }

    abstract void initBoard();
    abstract void setMove(int pos);
    abstract Image setCellImage(int state);
    abstract String getGameName();
    abstract void makePlayer(String gm);

    /**
     * makeBoard in GameController
     * @param size
     * This function fills the Board model with a new instance
     */
    public void makeBoard(int size) {
        board = new Board(size);
    }

    /**
     * setPlayers in GameController
     * @param fp
     * @param sp
     * @param fpID
     * @param spID
     * This function sets the players and their ID for keeping track about which players is which playing color.
     */
    void setPlayers(String fp, String sp, int fpID, int spID){
        firstPlayer = fp;
        secondPlayer = sp;
        firstPlayerID = fpID;
        secondPlayerID = spID;
    }

    /**
     * getFirstPlayer in GameController
     * @return
     * This function returns the private firstPlayer field.
     */
    String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * getSecondPlayer in GameController
     * @return
     * This function returns the private secondPlayer field.
     */
    String getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * getFirstPlayerID in GameController
     * @return
     * This function returns the private firstPlayerID field.
     */
    int getFirstPlayerID() {
        return firstPlayerID;
    }

    public int getSecondPlayerID(){
        return secondPlayerID;
    }

}
