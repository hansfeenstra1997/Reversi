package com.company;


    public class AIPlayerMiniMax {
        Board board;
        Board.Cell[][] cell;

        AIPlayerMiniMax(Board board) {
            this.board = board;
            cell = board.getBoard();
        }

        //variabelen
        private boolean computerMove = false;
        // private int[][] ocupiedCells = {{0,0,0},{0,0,0},{0,0,0}};

        private final int GLOBALPLAYER = 1;
        private final int GLOBALOPPONENT = 2;

        // 0 is leeg, 1 is computer, 2 is speler


        public Integer[] printHashMap() {

            int[] best = miniMaxTicTacToe(1);


            Integer[] xy = {best[1], best[2]};
            System.out.println(BestMove.x  + " " + BestMove.y);
//            BestMove.score = 0;
//            BestMove.x = 0;
//            BestMove.y = 0;
            return xy;
        }




        public int[] miniMaxTicTacToe(int player) {
            int winner = checkWinner();
            if(winner == GLOBALPLAYER) {
                int[] returnArray1 = {3, 0, 0};
                return returnArray1;
            }
            else if (winner == GLOBALOPPONENT) {
                int[] returnArray2 = {0, 0, 0};
                return returnArray2;
            }
            else if (boardFull()){
                int[] returnArray3 = {1, 0, 0};
                return returnArray3;
            }

            BestMove.x = 0;
            BestMove.y = 0;

            if(player == GLOBALPLAYER) {
                BestMove.score = 0;
            }
            else {
                BestMove.score = 3;
            }


            for(int y = 0; y < 3; y++) {
                for(int x = 0; x < 3; x++) {
                    if(cell[x][y].getState() == 0) {
                        cell[x][y].setState(player);
                        int[] temp = {0, 0, 0};
                        if (player == GLOBALPLAYER) {
                            temp = miniMaxTicTacToe(GLOBALOPPONENT);
                        } else if(player == GLOBALOPPONENT) {
                            temp = miniMaxTicTacToe(GLOBALPLAYER);
                        }

                        cell[x][y].setState(0);

                        //System.out.println(temp[0]);
                        //if (depth == 0) {
                        if ((player == GLOBALPLAYER && temp[0] > BestMove.score) || (player == GLOBALOPPONENT && temp[0] < BestMove.score )) {
                            BestMove.score = temp[0];
                            BestMove.x = x;
                            BestMove.y = y;
                        }
                        //}
                    }
                }
            }
            int[] returnarray = {BestMove.score, BestMove.x, BestMove.y};
            return returnarray;
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
    static int score = 0;
    static int x = 0;
    static int y = 0;
}
