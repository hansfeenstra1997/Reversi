package com.company;

import java.util.ArrayList;

public class AIReversiMiniMax {

    //variabelen
    final int GLOBALPLAYER = 1;
    final int GLOBALOPPONENT = 2;

    private int boardsize = 8;

    Board board;
    Board.Cell[][] cell;

    private static final ArrayList<String> directions = new ArrayList<>();
    private ArrayList<int[]> flipList = new ArrayList<>();

    private final int[][] SQUARE_WEIGHTS = {
            {200,   -60,  20,  10,  10,  20,  -60,  200},
            { -60, -100,  -5,  -5,  -5,  -5, -100,  -60},
            {  20,   -5,  15,   5,   5,  15,   -5,   20},
            {  10,   -5,   5,   5,   5,   5,   -5,   10},
            {  10,   -5,   5,   5,   5,   5,   -5,   10},
            {  20,   -5,  15,   5,   5,  15,   -5,   20},
            { -60, -100,  -5,  -5,  -5,  -5, -100,  -60},
            { 200,  -60,  20,  10,  10,  20,  -60,  200}};

    public AIReversiMiniMax(Board board) {
        this.board = board;
        this.cell = board.getBoard();

        //moet misschien snders

        directions.add("leftUpDiagonal");
        directions.add("up");
        directions.add("rightUpDiagonal");
        directions.add("right");
        directions.add("rightDownDiagonal");
        directions.add("down");
        directions.add("leftDownDiagonal");
        directions.add("left");

        //get possible moves
        //for loop door possible moves, en tegenstander doorgeven aan minimax
        //puntentelling bijhouden

    }

    public int[] doMove() {
        cell = board.getBoard();
        BestMove best = miniMaxReversi(GLOBALPLAYER, this.cell, 8);


        int[] xy = {best.x, best.y};

        return xy;
    }


    public BestMove miniMaxReversi(int player, Board.Cell[][] cell, int depth) {
        //System.out.println(depth-- + "--");
        if(depth == 0) {
            int val = calculateBoard(cell);
            if(player == GLOBALPLAYER) {
                //tempArray[0] = val;
                return new BestMove(val, 0, 0);
            }
            else {
                //tempArray[0] = -val;
                return new BestMove(-val, 0, 0);
            }
        }


        int opponent = GLOBALPLAYER;
//        int[] tempArray = new int[3];
//        tempArray[1] = 0;
//        tempArray[2] = 0;
        System.out.println(depth);
        if(player == GLOBALPLAYER) {
            opponent = GLOBALOPPONENT;
        }

        ArrayList<int[]> opponentMoves = getPossibleMoves(opponent, cell);
        ArrayList<int[]> moves = getPossibleMoves(player, cell);
        int winner = checkWinner(cell);

        if (winner == GLOBALPLAYER && moves.isEmpty() && opponentMoves.isEmpty()) {
            //tempArray[0] = Integer.MAX_VALUE;
            return new BestMove(Integer.MAX_VALUE, 0, 0);
        } else if(winner == GLOBALOPPONENT && moves.isEmpty() && opponentMoves.isEmpty()) {
            //tempArray[0] = Integer.MIN_VALUE;
            return new BestMove(Integer.MIN_VALUE, 0, 0);
        }



        BestMove currentBestMove = new BestMove(0, 0, 0);

        if(player == GLOBALPLAYER) {
            currentBestMove.score = Integer.MIN_VALUE;
        }
        else {
            currentBestMove.score = Integer.MAX_VALUE;
        }


        for(int[] move: moves) {
            //setmove functie aanroepen
            //tempCell[move[0]][move[1]].setState(player);

            //copy array
            //setmove
            //copy array meesturen
            //moet misschien beter
            //kopieren van board, zodat die weer meegestuurd kan worden aan volgende minimax

            //Board.Cell[][] tempCell = this.cell.clone();
            Board.Cell[][] tempCell = copyArray(cell);

            setMove(move[0], move[1], player, tempCell);
            tempCell[move[0]][move[1]].setState(player);

            if(depth == 0) {
                return currentBestMove;
            }

            int val = miniMaxReversi(opponent, tempCell, --depth).score;



            if ((player == GLOBALPLAYER && val > currentBestMove.score) || (player == GLOBALOPPONENT && val < currentBestMove.score )) {
                System.out.println("new best");
                currentBestMove.score = val;
                currentBestMove.x = move[0];
                currentBestMove.y = move[1];
            }

        }

    return currentBestMove;

    }


    public int calculateBoard(Board.Cell[][] cell) {
        int total = 0;
        //calculatefield -> alle posities waar player een zet heeft bij optellen, alle posities waar tegenstander een zet heeft ervan afhalen
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++){
                if(cell[x][y].getState() == GLOBALPLAYER) {
                    total += this.SQUARE_WEIGHTS[x][y];
                }
                else if(cell[x][y].getState() == GLOBALOPPONENT) {
                    total -= this.SQUARE_WEIGHTS[x][y];
                }
            }
        }

        return total;
    }

    public Board.Cell[][] copyArray(Board.Cell[][] oldCell) {

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


    public int checkWinner(Board.Cell[][] cell) {
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

    void setMove(int x, int y, int player, Board.Cell[][] cell) {
        flipBoard(x, y, player, cell);

    }

    protected void flipBoard(int x, int y, int player, Board.Cell[][] cell) {
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
                        cell[flipItem[1]][flipItem[0]].setState(player);
                        //board.setPosition(player, getPos(flipItem[1], flipItem[0]));
                        //ystem.out.println("Flip " + player + " - " + flipItem[0] + ", " + flipItem[1]);
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

