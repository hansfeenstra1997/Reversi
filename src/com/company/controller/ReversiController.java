package com.company.controller;

import com.company.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ReversiController extends Controller {

    private int boardsize = 8;
    private static final ArrayList<String> directions = new ArrayList<>();
    private ArrayList<int[]> flipList = new ArrayList<>();


    public ReversiController(VBox ta, Stage gameStage, String gameMode) {
        super(ta, gameStage);

        makeBoard(boardsize);

        this.gameMode = gameMode;

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
    protected void flipBoard(int x, int y, int player) {
        for(String direction: directions) {
            flip(x, y, direction, player);
        }
    }

    private void flip(int x, int y, String direction, int player) {
        checkDirection(x, y, direction, 0, true, player);
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

    public ArrayList<int[]> getPossibleMoves() {
        ArrayList<int[]> moves = new ArrayList<>();

        for(int y=0; y<boardsize; y++) {
            for(int x=0; x<boardsize; x++) {
                if(board.getCellState(y, x) == 0) {
                    int[] result = checkValidMove(x, y, 1);
                    if(result[0] != -1) {
                        moves.add(result);
                    }
                }
            }
        }
        return moves;
    }

    private int[] checkValidMove(int x, int y, int player) {
        int[] move = {-1,-1};
        for(String direction: directions) {
            if(checkDirection(x, y, direction, 0, false, player)) {
                move[0] = x;
                move[1] = y;
                //System.out.println("Hit! : " + x + ", " + y + " in direction " + direction);
            }
        }
        return move;
    }

    private boolean checkDirection(int x, int y, String direction, int flag, boolean flip, int player) {
        switch(direction) {
            case "leftUpDiagonal":
                if(x>0 && y>0) {
                    x--;
                    y--;
                }
                else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "up":
                if(y>0) {
                    y--;
                }
                else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "rightUpDiagonal":
                if(x<boardsize-1 && y>0) {
                    x++;
                    y--;
                }
                else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "right":
                if(x<boardsize-1) {
                    x++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "rightDownDiagonal":
                if(x<boardsize-1 && y<boardsize-1) {
                    x++;
                    y++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "down":
                if(y<boardsize-1) {
                    y++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "leftDownDiagonal":
                if(x>0 && y<boardsize-1) {
                    x--;
                    y++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "left":
                if(x>0) {
                    x--;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
        }

        if(board.getCellState(y,x) == player && flag>0) {
            if(flip) {
                if(!flipList.isEmpty()) {
                    for (int[] flipItem : flipList) {
                        board.setPosition(player, getPos(flipItem[1], flipItem[0]));
                        //ystem.out.println("Flip " + player + " - " + flipItem[0] + ", " + flipItem[1]);
                    }
                    flipList = new ArrayList<>();
                }
            }
            return true;
        }
        else if((board.getCellState(y,x)==player) && flag==0) {
            flipList = new ArrayList<>();
            return false;
        }
        if(board.getCellState(y,x) == 0) {
            flipList = new ArrayList<>();
            return false;
        }

        flag++;
        if(flip) {
            int[] res = {x,y};
            flipList.add(res);
        }
        return checkDirection(x, y, direction, flag, flip, player);
    }

}
