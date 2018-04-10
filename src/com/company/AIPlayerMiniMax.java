package com.company;


    public class AIPlayerMiniMax {
        Board board;
        Board.Cell[][] cell;

        AIPlayerMiniMax(Board board) {
            this.board = board;
            cell = board.getBoard();
        }

        //variabelen

        private final int GLOBALPLAYER = 1;
        private final int GLOBALOPPONENT = 2;

        // 0 is leeg, 1 is computer, 2 is speler


        public int[] doMove() {

            BestMove best = miniMaxTicTacToe(GLOBALPLAYER);


            int[] xy = {best.x, best.y};

            return xy;
        }




        public BestMove miniMaxTicTacToe(int player) {
            int winner = checkWinner();
            if(winner == GLOBALPLAYER) {
                return new BestMove(3, 0, 0);
            }
            else if (winner == GLOBALOPPONENT) {
                return new BestMove(0, 0, 0);
            }
            else if (boardFull()){
                return new BestMove(1, 0, 0);
            }

            BestMove currentBestMove = new BestMove(0, 0, 0);
            int opponent = 0;

            if(player == GLOBALPLAYER) {
                currentBestMove.score = 0;
                opponent = GLOBALOPPONENT;
            }
            else {
                currentBestMove.score = 3;
                opponent = GLOBALPLAYER;
            }


            for(int x = 0; x < 3; x++) {
                for(int y = 0; y < 3; y++) {
                    if(cell[x][y].getState() == 0) {
                        cell[x][y].setState(player);
                        int val = miniMaxTicTacToe(opponent).score;
                        cell[x][y].setState(0);

                        if ((player == GLOBALPLAYER && val > currentBestMove.score) || (player == GLOBALOPPONENT && val < currentBestMove.score )) {
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

        public int checkWinner() {
            int[][][] winPatterns = {{ {0,0},{0,1},{0,2} }, { {0,0},{1,1},{2,2} }, { {0,0},{1,0},{2,0} }, { {2,0},{2,1},{2,2} }, { {0,2},{1,2},{2,2} }, { {1,0},{1,1},{1,2} }, { {2,0},{1,1},{0,2} }, { {0,1},{1,1},{2,1} }};

            for(int i = 0; i < 8; i++) {
                if(cell[winPatterns[i][0][0]][winPatterns[i][0][1]].getState() == GLOBALPLAYER && cell[winPatterns[i][1][0]][winPatterns[i][1][1]].getState() == GLOBALPLAYER && cell[winPatterns[i][2][0]][winPatterns[i][2][1]].getState() == GLOBALPLAYER) {
                    return GLOBALPLAYER;
                }
                if(cell[winPatterns[i][0][0]][winPatterns[i][0][1]].getState() == GLOBALOPPONENT && cell[winPatterns[i][1][0]][winPatterns[i][1][1]].getState() == GLOBALOPPONENT && cell[winPatterns[i][2][0]][winPatterns[i][2][1]].getState() == GLOBALOPPONENT) {
                    return GLOBALOPPONENT;
                }
            }
            return 0;



            //possible ways to win:
            //0,0 0,1 0,2
            //0,0 1,1 2,2
            //0,0 1,0 2,0
            //2,0 2,1 2,2
            //0,2 1,2 2,2
            //1,0 1,1 1,2
            //2,0 1,1 0,2
            //0,1 1,1 2,1



            // implementatie om hele bord te checken of er een winnaar is.
        }


    }

class BestMove {
    int score, x, y;

    BestMove(int score, int x, int y) {
        this.score = score;
        this.x = x;
        this.y = y;
    }
}
