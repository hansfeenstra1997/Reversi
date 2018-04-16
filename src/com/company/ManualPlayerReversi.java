package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class ManualPlayerReversi implements Player {

    protected Board board;
    private static final ArrayList<String> directions = new ArrayList<>();
    private ArrayList<int[]> flipList = new ArrayList<>();
    int boardSize;

    /**
     * Constructor Manual Player Reversi
     * @param board - reference to Board instance
     */
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

    /**
     * Checks if given move is valid, sends move to server if valid
     * @param position - position on board
     */
    @Override
    public void doMove(int position) {
        int[] move = Board.convertPos(position);

        if(checkMove(move[0], move[1], 1)) {
            Connection.getInstance().sendCommand("move " + position);
        }
    }


    /**
     * If position on board is empty, check move. If checkValidMove does not return [-1,-1], return true, else false
     * @param x - x position
     * @param y - y position
     * @param player - check for player (1 = self, 2 = opponent)
     * @return true if move is valid, else false
     */
    private boolean checkMove(int x, int y, int player) {
        if(board.getCellState(y,x) == 0) {
            int[] result = checkValidMove(x, y, player);

            return result[0] != -1;
        }
        return false;
    }

    /**
     * Returns an ArrayList of all possible (valid) moves for a player on the board
     * @param player - 1 = self, 2 = opponent
     * @param board - Board reference
     * @param boardSize - Board size
     * @return ArrayList of coordinates [x,y]
     */
    public ArrayList<int[]> getPossibleMoves(int player, Board board, int boardSize) {
        ArrayList<int[]> moves = new ArrayList<>();

        for(int y=0; y<boardSize; y++) {
            for(int x=0; x<boardSize; x++) {
                if(board.getCellState(y,x) == 0) {
                    //if(cell[x][y].getState() == 0) {
                    int[] result = checkValidMove(x, y, player);
                    if(result[0] != -1) {
                        moves.add(result);
                    }
                }
            }
        }
        return moves;
    }

    /**
     * Checks for all directions (horizontal, diagonal, vertical) for a move if it's valid
     * @param x - x position on board
     * @param y - y position on board
     * @param player - player number
     * @return [-1,-1] if move is not valid, otherwise it will return the given move itself ([x,y])
     */
    int[] checkValidMove(int x, int y, int player) {
        int[] move = {-1,-1};
        for(String direction: directions) {
            if(checkDirection(x, y, direction, 0, false, player, board)) {
                move[0] = x;
                move[1] = y;
            }
        }
        return move;
    }

    /**
     * Checks if move is valid for every given direction.
     * @param x - move
     * @param y - move
     * @param direction - direction
     * @param flag - flag used for checking
     * @param flip - should opponent be flipped if move is valid? If no, only check and give back true. If yes, flip opponent
     * @param player - the player to check for
     * @param board - board reference
     * @return true if move is valid in the direction, false if not valid
     */
    private boolean checkDirection(int x, int y, String direction, int flag, boolean flip, int player, Board board) {
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
            if(flip) {
                if(!flipList.isEmpty()) {
                    for (int[] flipItem : flipList) {
                        board.setPosition(player, getPos(flipItem[1], flipItem[0]));
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
        return checkDirection(x, y, direction, flag, flip, player, board);
    }


    /**
     * Converts row and position to a single position.
     * Example: row 3, position 1 will convert to (3*8)+1 = 28 -> this can be sent to the server
     * @param row - row
     * @param pos - position
     * @return position as int
     */
    private int getPos(int row, int pos) {
        return (row*boardSize)+pos;
    }

    /**
     * Flip the opponent for every given direction where move is valid
     * @param x - x position
     * @param y - y position
     * @param player - player (1 = self, 2 = opponent)
     * @param board - Board reference
     */
    void flipBoard(int x, int y, int player, Board board) {
        flipBoardNew(x, y, player, board);
    }

    /**
     * Flip the opponent for every given direction where move is valid
     * @param x - x position
     * @param y - y position
     * @param player - player (1 = self, 2 = opponent)
     */
    public void flipBoard(int x, int y, int player) {
        flipBoardNew(x, y, player, this.board);
    }

    /**
     * Flip for every direction where possible
     * @param x - x position
     * @param y - y position
     * @param player - player (1 = self, 2 = opponent)
     * @param board - Board reference
     */
    private void flipBoardNew(int x, int y, int player, Board board) {
        for(String direction: directions) {
            flip(x, y, direction, player, board);
        }
    }


    /**
     * Check direction and tell checkDirection to flip if that direction is valid
     * @param x - x position
     * @param y - y position
     * @param direction - direction to check
     * @param player - player (1 = self, 2 = opponent)
     * @param board - Board reference
     */
    private void flip(int x, int y, String direction, int player, Board board) {
        checkDirection(x, y, direction, 0, true, player, board);
    }
}
