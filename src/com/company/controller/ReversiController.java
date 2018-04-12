package com.company.controller;

import com.company.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ReversiController extends GameController{

    private int boardsize = 8;
    private static final ArrayList<String> directions = new ArrayList<>();
    private ArrayList<int[]> flipList = new ArrayList<>();


    public ReversiController() {
        super();

        makeBoard(boardsize);

        directions.add("leftUpDiagonal");
        directions.add("up");
        directions.add("rightUpDiagonal");
        directions.add("right");
        directions.add("rightDownDiagonal");
        directions.add("down");
        directions.add("leftDownDiagonal");
        directions.add("left");

        //ai = new AIPlayerMiniMax(board);\

        //ai = new AIReversiMiniMax(board);



    }

    public void makePlayer(String gm){

        this.gameMode = gm;

        switch(gameMode) {
            case "manual":
                player = new ManualPlayerReversi(board);
                break;
            case "ai-easy":
                player = new AiPlayerReversi(board, "easy");
                break;
            case "ai-hard":
                player = new AiPlayerReversi(board, "hard");
                break;
            default:
                player = new ManualPlayerReversi(board);
        }
    }

    @Override
    void initBoard() {
        setStartPosition();
    }

    @Override
    String getGameName(){
        return "Reversi";
    }

    private void setStartPosition() {
        int row = (boardsize -1)/2;
        int pos = (boardsize -1)/2;

        board.setPosition(secondPlayerID, getPos(row, pos));
        board.setPosition(firstPlayerID, getPos(row, pos+1));
        board.setPosition(firstPlayerID, getPos(row+1, pos));
        board.setPosition(secondPlayerID, getPos(row+1, pos+1));
    }

    private int getPos(int row, int pos) {
        return (row*boardsize)+pos;
    }

    @Override
    void setMove(int pos) {
        player.doMove(pos);
    }

    @Override
    Image setCellImage(int state) {
        if(state == firstPlayerID){
            return new Image("/black.png", 50, 50, false, false);
            //return "B";
        } else if(state == secondPlayerID){
            return new Image("/white.png", 50, 50, false, false);
            //return "W";
        }
        return null;
    }
}
