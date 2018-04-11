package com.company;

import com.company.model.Board;

import java.util.ArrayList;

/**
 * This class will calculate the best move to do whilst playing Reversi
 * @author Robert
 */
public class AIReversiMiniMax {

    //variabelen
    private final int GLOBALPLAYER = 1;
    private final int GLOBALOPPONENT = 2;

    private int boardsize = 8;

    private Board board;
    private Board.Cell[][] cell;

    private static final ArrayList<String> directions = new ArrayList<>();
    private ArrayList<int[]> flipList = new ArrayList<>();

    private final int[][] SQUARE_WEIGHTS  =
            {{120, -20, 20,  5,  5, 20,-20,120},
            {-20, -40, -5, -5, -5, -5,-40,-20},
            {20,  -5,  15,  3,  3, 15, -5, 20},
            {5,   -5,   3,  3,  3,  3, -5,  5},
            {5,   -5,   3,  3,  3,  3, -5,  5},
            {20,  -5,  15,  3,  3, 15, -5, 20},
            {-20,-40,  -5, -5, -5, -5,-40,-20},
            {120,-20,  20,  5,  5, 20,-20,120}};

    public AIReversiMiniMax(Board board) {
        this.board = board;
        this.cell = board.getBoard();

        directions.add("leftUpDiagonal");
        directions.add("up");
        directions.add("rightUpDiagonal");
        directions.add("right");
        directions.add("rightDownDiagonal");
        directions.add("down");
        directions.add("leftDownDiagonal");
        directions.add("left");

    }

    public int[] doMove() {
        cell = board.getBoard();
        BestMove best = miniMaxReversi(GLOBALPLAYER, this.cell, 1);

        return new int[]{best.x, best.y};
    }

    /**
     *
     * @param player int 1 or 2
     * @param cell current board
     * @param depth how often the function is called recursive
     * @return the best move
     */
    private BestMove miniMaxReversi(int player, Board.Cell[][] cell, int depth) {
        if (depth <= 0) {
            int val = calculateBoard(cell, player);

            return new BestMove(val, 0, 0);
        }

        int opponent = 2 - player + 1;

        ArrayList<int[]> opponentMoves = getPossibleMoves(opponent, cell);
        ArrayList<int[]> moves = getPossibleMoves(player, cell);
        int winner = checkWinner(cell);

        if (winner == GLOBALPLAYER && moves.isEmpty() && opponentMoves.isEmpty()) {
            System.out.println("winner is me");
            return new BestMove(Integer.MAX_VALUE - 1, 0, 0);
        } else if (winner == GLOBALOPPONENT && moves.isEmpty() && opponentMoves.isEmpty()) {
            System.out.println("Winner is opp " + ", depth: " + depth);
            return new BestMove(Integer.MIN_VALUE + 1, 0, 0);
        }

        BestMove currentBestMove = null;

        if(depth == 8) {
            for(int[] move: moves) {
                System.out.println(move[0] + " moves " + move[1]);
            }
        }

        for(int[] move: moves) {
            Board.Cell[][] tempCell = copyArray(cell);

            setMove(move[0], move[1], player, tempCell);
            tempCell[move[0]][move[1]].setState(player);

            int val = miniMaxReversi(opponent, tempCell, --depth).score;

            if (currentBestMove == null || (player == GLOBALPLAYER && val > currentBestMove.score) || (player == GLOBALOPPONENT && val < currentBestMove.score )) {
                //System.out.println("new best");
                currentBestMove = new BestMove(val, move[0], move[1]);
            }

        }

    return currentBestMove;

    }

    private int calculateBoard(Board.Cell[][] cell, int player) {
        int total = 0;

        int opp = 2 - player + 1;

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++){
                if(cell[x][y].getState() == player) {
                    total += this.SQUARE_WEIGHTS[x][y];
                }
                else if(cell[x][y].getState() == opp) {
                    total -= this.SQUARE_WEIGHTS[x][y];
                }
            }
        }

        return total;
    }

    private Board.Cell[][] copyArray(Board.Cell[][] oldCell) {

        Board board = new Board(8);
        Board.Cell[][] newCell;
        newCell = board.getBoard();

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                newCell[x][y].setState(oldCell[x][y].getState());
            }
        }
        return newCell;
    }


    private int checkWinner(Board.Cell[][] cell) {
        int playerCounter = 0;
        int opponentCounter = 0;

        for(int x = 0; x < 8; x++) {
            for(int y =0; y < 8; y++) {
                if(cell[x][y].getState() == GLOBALPLAYER) {
                    playerCounter++;
                } else if (cell[x][y].getState() == GLOBALOPPONENT) {
                    opponentCounter++;
                }
            }

        }

        if(opponentCounter > playerCounter) {
            return GLOBALOPPONENT;
        } else if(playerCounter > opponentCounter) {
            return GLOBALPLAYER;
        }
        return 0;
    }

    private void setMove(int x, int y, int player, Board.Cell[][] cell) {
        flipBoard(x, y, player, cell);

    }

    private void flipBoard(int x, int y, int player, Board.Cell[][] cell) {
        for(String direction: directions) {
            flip(x, y, direction, player, cell);
        }
    }

    private void flip(int x, int y, String direction, int player, Board.Cell[][] cell) {
        checkDirection(x, y, direction, 0, true, player, cell);
    }

    private ArrayList<int[]> getPossibleMoves(int player, Board.Cell[][] cell) {
        ArrayList<int[]> moves = new ArrayList<>();

        for(int y=0; y<boardsize; y++) {
            for(int x=0; x<boardsize; x++) {
                if(cell[x][y].getState() == 0) {
                    int[] result = checkValidMove(x, y, player, cell);
                    if(result[0] != -1) {
                        moves.add(result);
                    }
                }
            }
        }
        return moves;
    }

    private int[] checkValidMove(int x, int y, int player, Board.Cell[][] cell) {
        int[] move = {-1,-1};
        for(String direction: directions) {
            if(checkDirection(x, y, direction, 0, false, player, cell)) {
                move[0] = x;
                move[1] = y;
            }
        }
        return move;
    }

    private boolean checkDirection(int x, int y, String direction, int flag, boolean flip, int player, Board.Cell[][] cell) {
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

        if(cell[x][y].getState() == player && flag>0) {
            if(flip) {
                if(!flipList.isEmpty()) {
                            for (int[] flipItem : flipList) {
                        cell[flipItem[0]][flipItem[1]].setState(player);
                        //board.setPosition(player, getPos(flipItem[1], flipItem[0]));
                        //System.out.println("Flip " + player + " - " + flipItem[0] + ", " + flipItem[1]);
                    }
                    flipList = new ArrayList<>();
                }
            }
            return true;
        }
        else if((cell[x][y].getState()==player) && flag==0) {
            flipList = new ArrayList<>();
            return false;
        }
        if(cell[x][y].getState() == 0) {
            flipList = new ArrayList<>();
            return false;
        }

        flag++;
        if(flip) {
            int[] res = {x,y};
            flipList.add(res);
        }
        return checkDirection(x, y, direction, flag, flip, player, cell);
    }

    class BestMove {
        int score, x, y;

        BestMove(int score, int x, int y) {
            this.score = score;
            this.x = x;
            this.y = y;
        }
    }
}
