package com.company;

public class Board {
    private Cell[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        board = setup(size);
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getBoard(){
        return board;
    }

    public Cell[][] setup(int size) {
        Cell[][] board = new Cell[size][size];
        for(int i=0; i<board.length; i++) {
            for(int y=0; y<board[1].length; y++) {
                board[i][y] = new Cell();
            }
        }
        return board;
    }

    public void setPosition(int selfOrOpponent, int pos) {
        int position = (pos%size);  // 0%3=0 2%3=2
        int row = (pos/size);       // 0/3=0 1/3=0.33 2/3=0.66 etc.. 8/3=2.66 (2)
        board[position][row].setState(selfOrOpponent);
    }

    public void printBoard() {
        for(int i=0; i<size; i++) {
            for(int y=0; y<size; y++) {
                System.out.print(board[i][y].getState() + " ");
            }
            System.out.println();
        }
    }

    public class Cell {
        // State 0 = empty
        // State 1 = self (white)
        // State 2 = opponent (black)
        private int state;

        private Cell() {
            state = 0;
        }

        public void setState(int state) {
            if(state >= 0 && state <= 2) {
                this.state = state;
            }
        }

        public int getState() {
            return this.state;
        }
    }
}
