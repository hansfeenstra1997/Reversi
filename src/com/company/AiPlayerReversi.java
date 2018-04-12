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

    public AiPlayerReversi(Board board, String gameMode) {
        super(board);
        this.gameMode = gameMode;
    }

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
                xy = doMovePointsBoard();
                pos = xy[1] * 8 + xy[0];
                Connection.getInstance().sendCommand("move " + pos);
                break;
            default:
                break;
        }
    }

    public int[] doMoveDifferent() {
        int score = -1;
        int x = 0;
        int y = 0;

        ArrayList<int[]> moves = getPossibleMoves(1, board, boardSize);
        for (int[] move : moves) {
            System.out.println(move[0] + ", " + move[1] + " - weight " + SECOND_WEIGHT[move[0]][move[1]]);
            if (SECOND_WEIGHT[move[0]][move[1]] > score) {
                score = SECOND_WEIGHT[move[0]][move[1]];
                y = move[1];
                x = move[0];
            }
        }

        return new int[]{x, y};
    }

    /*
           //calculateboard
        //do move
        //calculate new board
        //check diffence
        //if difference is bigger than other difference, do move

        int old_total = calculateBoard(this.cell);

        this.cell = board.getBoard();
        int score = 0;
        int x = 0;
        int y = 0;

        ArrayList<int[]> moves = getPossibleMoves(1, this.cell);
        for (int[] move : moves) {

            Board.Cell[][] newCell = copyArray(this.cell);

            setMove(move[0], move[1], 1, newCell);
            newCell[move[0]][move[1]].setState(1);

            int new_total = calculateBoard(newCell);
            int difference = new_total - old_total;

            if (difference > score) {
                score = difference;
                y = move[1];
                x = move[0];
            }

//
//            if (SECOND_WEIGHT[move[1]][move[0]] > score) {
//                score = SECOND_WEIGHT[move[1]][move[0]];
//                y = move[1];
//                x = move[0];
//            }
        }

        return new int[]{x, y};
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
                System.out.println("difference " + difference);
                score = difference;
                bestX = move[0];
                bestY = move[1];
            }
        }

        return new int[]{bestX, bestY};
    }

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


    private Board copyBoard(Board oldBoard) {
        Board newBoard = new Board(boardSize);

        oldBoard.setBoard(newBoard);

        return newBoard;
    }

    public int[] doEasyMove() {
        ArrayList<int[]> moves = getPossibleMoves(1, this.board, boardSize);

        int range = (moves.size() -1) + 1;
        int get = (int)(Math.random() * range);

        return moves.get(get);

    }

}
