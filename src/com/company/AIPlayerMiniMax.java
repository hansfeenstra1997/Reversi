package com.company;

import com.company.model.Board;

import java.util.ArrayList;

/**
 * This class will calculate the best move to do whilst playing Tic-Tac-Toe
 * @author Robert
 */
public class AIPlayerMiniMax extends AIPlayer{
        private Board board;
        private Board.Cell[][] cell;


        public AIPlayerMiniMax(Board board) {
            this.board = board;
            cell = board.getBoard();
        }

        //variabelen

        private final int GLOBAL_PLAYER = 1;
        private final int GLOBAL_OPPONENT = 2;

        // 0 is leeg, 1 is computer, 2 is speler


        public int[] doMove() {
            BestMove best = miniMaxTicTacToe(GLOBAL_PLAYER);

            return new int[]{best.x, best.y};
        }


        public int[] doEasyMove() {
            ArrayList<int[]> avaiableMoves = new ArrayList<>();

            for(int x = 0; x < 3; x++) {
                for(int y = 0; y < 3; y++) {
                    if (cell[x][y].getState() == 0) {
                        avaiableMoves.add(new int[]{x,y});
                    }
                }
            }
            int range = (avaiableMoves.size() -1) + 1;
            int get = (int)(Math.random() * range);

            return avaiableMoves.get(get);
        }

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

        int checkWinner(Board.Cell[][] cell) {
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

    class BestMove {
        int score, x, y;

        BestMove(int score, int x, int y) {
            this.score = score;
            this.x = x;
            this.y = y;
        }

    }

}
