package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class ManualPlayerReversi implements Player {

    protected Board board;
    private static final ArrayList<String> directions = new ArrayList<>();
    private ArrayList<int[]> flipList = new ArrayList<>();
    protected int boardSize;

    public ManualPlayerReversi(Board board) {
        this.board = board;
        this.boardSize = board.getSize();

        directions.add("leftUpDiagonal");
        directions.add("up");
        directions.add("rightUpDiagonal");
        directions.add("right");
        directions.add("rightDownDiagonal");
        directions.add("down");
        directions.add("leftDownDiagonal");
        directions.add("left");
    }

    @Override
    public void doMove(int position) {
        int[] move = Board.convertPos(position);

        if(checkMove(move[0], move[1], 1)) {
            Connection.getInstance().sendCommand("move " + position);
        }
    }

    private boolean checkMove(int x, int y, int player) {
        if(board.getCellState(y,x) == 0) {
            int[] result = checkValidMove(x, y, player);
            if (result[0] != -1)
                return true;
        }
        return false;
    }

    protected int[] checkValidMove(int x, int y, int player) {
        int[] move = {-1,-1};
        for(String direction: directions) {
            if(checkDirection(x, y, direction, 0, false, player)) {
                move[0] = x;
                move[1] = y;
            }
        }
        return move;
    }

    public boolean checkDirection(int x, int y, String direction, int flag, boolean flip, int player) {
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
                if(x<boardSize-1 && y>0) {
                    x++;
                    y--;
                }
                else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "right":
                if(x<boardSize-1) {
                    x++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "rightDownDiagonal":
                if(x<boardSize-1 && y<boardSize-1) {
                    x++;
                    y++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "down":
                if(y<boardSize-1) {
                    y++;
                } else {
                    flipList = new ArrayList<>();
                    return false;
                }
                break;
            case "leftDownDiagonal":
                if(x>0 && y<boardSize-1) {
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

        if(board.getCellState(y, x) == player && flag>0) {
        //if(cell[x][y].getState() == player && flag>0) {
            if(flip) {
                if(!flipList.isEmpty()) {
                    for (int[] flipItem : flipList) {
                        board.setPosition(player, getPos(flipItem[1], flipItem[0]));
                        //cell[flipItem[0]][flipItem[1]].setState(player);
                        //board.setPosition(player, getPos(flipItem[1], flipItem[0]));
                        //System.out.println("Flip " + player + " - " + flipItem[0] + ", " + flipItem[1]);
                    }
                    flipList = new ArrayList<>();
                }
            }
            return true;
        }
        //else if((cell[x][y].getState()==player) && flag==0) {
        else if((board.getCellState(y,x)==player) && flag==0) {
            flipList = new ArrayList<>();
            return false;
        }
        //if(cell[x][y].getState() == 0) {
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

    private int getPos(int row, int pos) {
        return (row*boardSize)+pos;
    }
}
