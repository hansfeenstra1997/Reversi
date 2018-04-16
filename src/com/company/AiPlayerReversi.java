package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class AiPlayerReversi extends ManualPlayerReversi {

    private String gameMode;

    private final int[][] SECOND_WEIGHT = {
            {200,  2,  20,  5,  5, 20,  2,200},
            {  2,  0,   3,  3,  3,  3,  0,  2},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {  2,  0,   3,  3,  3,  3,  0,  2},
            {200,  2,  20,  5,  5, 20,  2,200}};

    /**
     * Constructor for Ai Player Reversi
     * @param board - reference to Board
     * @param gameMode - game mode (easy or hard)
     */
    public AiPlayerReversi(Board board, String gameMode) {
        super(board);
        this.gameMode = gameMode;
    }

    /**
     * Calculate and set move based on game mode.
     * @param position - position on board
     */
    @Override
    public void doMove(int position) {
        int[] xy;
        int pos;
        switch(gameMode) {
            case "easy":
                xy = doEasyMove();
                pos = xy[1] * 8 + xy[0];
                Connection.getInstance().sendCommand("move " + pos);
                break;
            case "hard":
                xy = doMoveDifferent();
                pos = xy[1] * 8 + xy[0];
                Connection.getInstance().sendCommand("move " + pos);
                break;
            default:
                break;
        }
    }

    /**
     * Calculates which move is the best based on weight of the cells on the board.
     * @return calculated move for AI
     */
    private int[] doMoveDifferent() {
        int score = -1;
        int x = 0;
        int y = 0;

        ArrayList<int[]> moves = getPossibleMoves(1, board, boardSize);
        for (int[] move : moves) {
            if (SECOND_WEIGHT[move[0]][move[1]] > score) {
                score = SECOND_WEIGHT[move[0]][move[1]];
                y = move[1];
                x = move[0];
            }
        }

        return new int[]{x, y};
    }

    /**
     * Calculates which move is the best based on weight of the cells, also looks at the result when setting a move
     * @return calculated move for AI
     */
    public int[] doMovePointsBoard() {
        int score = -1;
        int bestX = 0;
        int bestY = 0;

        int oldScore = calculateBoard(this.board);

        ArrayList<int[]> moves = getPossibleMoves(1, this.board, boardSize);
        for (int[] move : moves) {
            Board copyBoard = copyBoard(board);

            flipBoard(move[0], move[1], 1, copyBoard);
            copyBoard.setPosition(1, move[0], move[1]);

            int newScore = calculateBoard(copyBoard);
            int difference = newScore - oldScore;

            if (difference > score) {
                score = difference;
                bestX = move[0];
                bestY = move[1];
            }
        }

        return new int[]{bestX, bestY};
    }

    /**
     * Calculates total weight for all cells in the board
     * @param board - reference to Board
     * @return total weight
     */
    private int calculateBoard(Board board) {
        int total = 0;

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (board.getCellState(x,y) == 1) {
                    total += SECOND_WEIGHT[x][y];
                }
            }
        }

        return total;
    }

    /**
     * Gives back a copy of the current board
     * @param oldBoard - reference to current Board
     * @return copy of current Board
     */
    private Board copyBoard(Board oldBoard) {
        Board newBoard = new Board(boardSize);

        oldBoard.setBoard(newBoard);

        return newBoard;
    }

    /**
     * Gets a random move out of all the valid moves currently possible
     * @return calculated move for AI
     */
    private int[] doEasyMove() {
        ArrayList<int[]> moves = getPossibleMoves(1, this.board, boardSize);

        int range = (moves.size() -1) + 1;
        int get = (int)(Math.random() * range);

        return moves.get(get);

    }

}
