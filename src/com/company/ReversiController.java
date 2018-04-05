package com.company;

import javafx.stage.Stage;

import java.util.ArrayList;

public class ReversiController extends Controller{

    private int boardsize = 8;
    private static final ArrayList<String> directions = new ArrayList<>();


    public ReversiController(Stage gameStage) {
        super(gameStage);

        directions.add("leftUpDiagonal");
        directions.add("up");
        directions.add("rightUpDiagonal");
        directions.add("right");
        directions.add("rightDownDiagonal");
        directions.add("down");
        directions.add("leftDownDiagonal");
        directions.add("left");

        makeBoard(boardsize);
        //ai = new AIPlayerMiniMax(board);
    }

    @Override
    void initBoard() {
        setStartPosition();
    }

    private void setStartPosition() {
        int row = (boardsize -1)/2;
        int pos = (boardsize -1)/2;

        board.setPosition(secondPlayerID, getPos(row, pos));
        board.setPosition(firstPlayerID, getPos(row, pos+1));
        board.setPosition(firstPlayerID, getPos(row+1, pos));
        board.setPosition(secondPlayerID, getPos(row+1, pos+1));

        ArrayList<int[]> moves = getPossibleMoves();
        for(int[] move: moves) {
            System.out.println(move[0] + " " + move[1]);
        }
    }

    private int getPos(int row, int pos) {
        return (row* boardsize)+pos;
    }

    @Override
    void setMove(int pos) {
        //controlerne of move mogelijk is
        int[] move = board.convertPos(pos);
        System.out.println("Check move " + move[0] + ", " + move[1] + " - position " + pos);
        if(checkMove(move[0], move[1])) {
            conn.sendCommand("move " + pos);
            updateBoard();
        }
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

    private ArrayList<int[]> getPossibleMoves() {
        ArrayList<int[]> moves = new ArrayList<>();

        for(int y=0; y<boardsize; y++) {
            for(int x=0; x<boardsize; x++) {
                if(board.getCellState(y, x) == 0) {
                //if(board[y][x] == 0) {
                    int[] result = checkValidMove(x, y);
                    if(result[0] != -1) {
                        moves.add(result);
                    }
                }
            }
        }
        return moves;
    }

    private boolean checkMove(int x, int y) {
        int[] result = checkValidMove(x, y);
        if(result[0] != -1)
            return true;
        return false;
    }

    private int[] checkValidMove(int x, int y) {
        int[] move = {-1,-1};
        for(String direction: directions) {
            if(checkDirection(x, y, direction, 0)) {
                move[0] = x;
                move[1] = y;
                //System.out.println("Hit! : " + x + ", " + y + " in direction " + direction);
            }
        }
        return move;
    }

    private boolean checkDirection(int x, int y, String direction, int flag) {
        switch(direction) {
            case "leftUpDiagonal":
                if(x>0 && y>0) {
                    x--;
                    y--;
                }
                else {
                    return false;
                }
                break;
            case "up":
                if(y>0) {
                    y--;
                }
                else {
                    return false;
                }
                break;
            case "rightUpDiagonal":
                if(x<boardsize-1 && y>0) {
                    x++;
                    y--;
                }
                else {
                    return false;
                }
                break;
            case "right":
                if(x<boardsize-1) {
                    x++;
                } else {
                    return false;
                }
                break;
            case "rightDownDiagonal":
                if(x<boardsize-1 && y<boardsize-1) {
                    x++;
                    y++;
                    //System.out.println("new x, y " + x + " " + y + " - value: " + board[x][y]);
                } else {
                    return false;
                }
                break;
            case "down":
                if(y<boardsize-1) {
                    y++;
                } else {
                    return false;
                }
                break;
            case "leftDownDiagonal":
                if(x>0 && y<boardsize-1) {
                    x--;
                    y++;
                } else {
                    return false;
                }
                break;
            case "left":
                if(x>0) {
                    x--;
                } else {
                    return false;
                }
                break;
        }

        //if(board[y][x] == 1 && flag>0) {
        if(board.getCellState(y,x) == 1 && flag>0) {
            return true;
        }
        //else if((board[y][x] == 1) && flag==0) {
        else if((board.getCellState(y,x)==1) && flag==0) {
            return false;
        }
        //if(board[y][x] == 0) return false;
        if(board.getCellState(y,x) == 0) return false;

        flag++;
        return checkDirection(x, y, direction, flag);
    }

}
