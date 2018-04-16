package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class AiPlayerReversi extends ManualPlayerReversi {

    private String gameMode;
    private final int GLOBAL_PLAYER = 1;
    private final int GLOBAL_OPPONENT = 2;

    private final int[][] SECOND_WEIGHT = {
            {200,  2,  20,  5,  5, 20,  2,200},
            {  2,  0,   3,  3,  3,  3,  0,  2},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {  2,  0,   3,  3,  3,  3,  0,  2},
            {200,  2,  20,  5,  5, 20,  2,200}};

    private final int[][] SECOND_WEIGHT2 = {
            {200,  2,  20,  5,  5, 20,  2,200},
            {  2,  -20,   3,  3,  3,  3,  -20,  2},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {  2,  -20,   3,  3,  3,  3,  -20,  2},
            {200,  2,  20,  5,  5, 20,  2,200}};

    private final int[][] SECOND_WEIGHT3 = {
            {200,  2,  20,  5,  5, 20,  2,200},
            {  2,  40,   3,  3,  3,  3,  40,  2},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {5,    3,   6,  6,  6,  6,  3,  5},
            {20,   3,  15,  6,  6, 15,  3, 20},
            {  2,  40,   3,  3,  3,  3,  40,  2},
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
            System.out.println(move[0] + ", " + move[1] + " - weight " + SECOND_WEIGHT[move[0]][move[1]]);

            if (SECOND_WEIGHT[move[0]][move[1]] > score) {
                score = SECOND_WEIGHT[move[0]][move[1]];
                y = move[1];
                x = move[0];
            }
        }

        return new int[]{x, y};
    }

    public int[] doMoveDifferent2() {
        int score = -1;
        int x = 0;
        int y = 0;

        ArrayList<int[]> moves = getPossibleMoves(1, board, boardSize);
        for (int[] move : moves) {
            System.out.println(move[0] + ", " + move[1] + " - weight " + SECOND_WEIGHT[move[0]][move[1]]);

            if (SECOND_WEIGHT[move[0]][move[1]] == score) {
                if ((int)(Math.random() * 2 + 1) == 2) {
                    score = SECOND_WEIGHT[move[0]][move[1]];
                }
            }

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

        int oldScore = calculateBoard(this.board, 1);

        ArrayList<int[]> moves = getPossibleMoves(1, this.board, boardSize);
        for (int[] move : moves) {
            Board copyBoard = copyBoard(board);

            flipBoard(move[0], move[1], 1, copyBoard);
            copyBoard.setPosition(1, move[0], move[1]);

            //copyBoard.printBoard();

            int newScore = calculateBoard(copyBoard, 1);
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
    private int calculateBoard(Board board, int player) {
        int total = 0;

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (board.getCellState(x,y) == player) {
                    total += SECOND_WEIGHT2[x][y];
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


    //-------------------------------------------------Minimax which is not used anymore

    private int[] doMinimaxMove() {
        BestMove bestMove = miniMaxReversi(1, this.board, 1);

        return new int[] {bestMove.x, bestMove.y};
    }


    /**
     *
     * @param player int 1 or 2
     * @param board current board
     * @param depth how often the function is called recursive
     * @return the best move
     */
    private BestMove miniMaxReversi(int player, Board board, int depth) {
        if (depth <= 0) {
            int val = calculateBoard(board, player);

            return new BestMove(val, 0, 0);
        }

        int opponent = 2 - player + 1;

        ArrayList<int[]> opponentMoves = getPossibleMoves(opponent, board, boardSize);
        ArrayList<int[]> moves = getPossibleMoves(player, board, boardSize);
        int winner = checkWinner(board);

        if (winner == GLOBAL_PLAYER && moves.isEmpty() && opponentMoves.isEmpty()) {
            //System.out.println("winner is me");
            return new BestMove(Integer.MAX_VALUE - 1, 0, 0);
        } else if (winner == GLOBAL_OPPONENT && moves.isEmpty() && opponentMoves.isEmpty()) {
            //System.out.println("Winner is opp " + ", depth: " + depth);
            return new BestMove(Integer.MIN_VALUE + 1, 0, 0);
        }

        BestMove currentBestMove = null;

//        if(depth == 9) {
//            for(int[] move: moves) {
//                System.out.println(move[0] + " moves " + move[1]);
//            }
//        }

        for(int[] move: moves) {
            Board copyBoard = copyBoard(board);

            flipBoard(move[0], move[1], player, copyBoard);
            copyBoard.setPosition(player, move[0], move[1]);

//
//            Board.Cell[][] tempCell = copyArray(cell);
//
//            setMove(move[0], move[1], player, tempCell);
//            tempCell[move[0]][move[1]].setState(player);

            int val = miniMaxReversi(opponent, copyBoard, --depth).score;

            if (currentBestMove == null || (player == GLOBAL_PLAYER && val > currentBestMove.score) || (player == GLOBAL_OPPONENT && val < currentBestMove.score )) {
                //System.out.println("new best");
                currentBestMove = new BestMove(val, move[0], move[1]);
            }

        }
        if (currentBestMove == null) {
            currentBestMove = new BestMove(0,0,0);
        }
        return currentBestMove;

    }


    int checkWinner(Board board) {
        int playerCounter = 0;
        int opponentCounter = 0;

        for(int x = 0; x < 8; x++) {
            for(int y =0; y < 8; y++) {
                if(board.getCellState(x,y) == GLOBAL_PLAYER) {
                    playerCounter++;
                } else if (board.getCellState(x,y) == GLOBAL_OPPONENT) {
                    opponentCounter++;
                }
            }

        }

        if(opponentCounter > playerCounter) {
            return GLOBAL_OPPONENT;
        } else if(playerCounter > opponentCounter) {
            return GLOBAL_PLAYER;
        }
        return 0;
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
