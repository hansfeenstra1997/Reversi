package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class AiPlayerTicTacToe extends ManualPlayerTicTacToe {

    private String gameMode;

    private final int GLOBAL_PLAYER = 1;
    private final int GLOBAL_OPPONENT = 2;
    private Board.Cell[][] cell;

    /**
     * Constructor Ai player Tic-tac-toe
     * @param board - reference to Board
     * @param gameMode - game mode (easy or hard)
     */
    public AiPlayerTicTacToe(Board board, String gameMode) {
        super(board);
        this.gameMode = gameMode;
        this.cell = board.getBoard();
    }

    /**
     * Does a TicTacToe move, can be hard or easy
     */
    @Override
    public void doMove(int position) {
        switch(gameMode) {
            case "hard":
                BestMove best = miniMaxTicTacToe(GLOBAL_PLAYER);
                int pos = best.x * boardSize + best.y;
                Connection.getInstance().sendCommand("move " + pos);
                break;
            case "easy":
                int[] xy = doEasyMove();
                pos = xy[0] * boardSize + xy[1];
                Connection.getInstance().sendCommand("move " + pos);
                break;
            default:
                break;
        }
    }

    /**
     * Does an easy TicTacToe move, this is a random move within all the possible moves
     * @return int[] with x and y position
     */
    private int[] doEasyMove() {
        ArrayList<int[]> availableMoves = new ArrayList<>();

        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if (cell[x][y].getState() == 0) {
                    availableMoves.add(new int[]{x,y});
                }
            }
        }
        int range = (availableMoves.size() -1) + 1;
        int get = (int)(Math.random() * range);

        return availableMoves.get(get);
    }

    /**
     * Does a hard move, the best possible move.
     * This function will never lose a game
     * @param player the player currently in function
     * @return BestMove (check BestMove innerclass)
     */
    private BestMove miniMaxTicTacToe(int player) {
        int winner = checkWinner(cell);
        if(winner == GLOBAL_PLAYER) {
            return new BestMove(3, 0, 0);
        }
        else if (winner == GLOBAL_OPPONENT) {
            return new BestMove(0, 0, 0);
        }
        else if (boardFull()){
            return new BestMove(1, 0, 0);
        }

        BestMove currentBestMove = new BestMove(0, 0, 0);
        int opponent;

        if(player == GLOBAL_PLAYER) {
            currentBestMove.score = 0;
            opponent = GLOBAL_OPPONENT;
        }
        else {
            currentBestMove.score = 3;
            opponent = GLOBAL_PLAYER;
        }


        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if(cell[x][y].getState() == 0) {
                    cell[x][y].setState(player);
                    int val = miniMaxTicTacToe(opponent).score;
                    cell[x][y].setState(0);

                    if ((player == GLOBAL_PLAYER && val > currentBestMove.score) || (player == GLOBAL_OPPONENT && val < currentBestMove.score )) {
                        currentBestMove.score = val;
                        currentBestMove.x = x;
                        currentBestMove.y = y;
                    }
                    //}
                }
            }
        }
        return currentBestMove;
    }

    /**
     * Checks if board is full
     * @return true if full, false if not full
     */
    private boolean boardFull() {
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if(cell[x][y].getState() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a winner. Checks all win directions, for opponent and player
     * @param cell the current playing board
     * @return 0 if there is no winner, 1 is player is winner, 2 if opponent is winner.
     */
    private int checkWinner(Board.Cell[][] cell) {
        int[][][] winPatterns = {{ {0,0},{0,1},{0,2} }, { {0,0},{1,1},{2,2} }, { {0,0},{1,0},{2,0} }, { {2,0},{2,1},{2,2} }, { {0,2},{1,2},{2,2} }, { {1,0},{1,1},{1,2} }, { {2,0},{1,1},{0,2} }, { {0,1},{1,1},{2,1} }};

        for(int i = 0; i < 8; i++) {
            if(cell[winPatterns[i][0][0]][winPatterns[i][0][1]].getState() == GLOBAL_PLAYER && cell[winPatterns[i][1][0]][winPatterns[i][1][1]].getState() == GLOBAL_PLAYER && cell[winPatterns[i][2][0]][winPatterns[i][2][1]].getState() == GLOBAL_PLAYER) {
                return GLOBAL_PLAYER;
            }
            if(cell[winPatterns[i][0][0]][winPatterns[i][0][1]].getState() == GLOBAL_OPPONENT && cell[winPatterns[i][1][0]][winPatterns[i][1][1]].getState() == GLOBAL_OPPONENT && cell[winPatterns[i][2][0]][winPatterns[i][2][1]].getState() == GLOBAL_OPPONENT) {
                return GLOBAL_OPPONENT;
            }
        }
        return 0;
    }

    /**
     * Holds the current BestMove.
     */
    class BestMove {
        int score, x, y;

        BestMove(int score, int x, int y) {
            this.score = score;
            this.x = x;
            this.y = y;
        }
    }
}
