package com.company.model;

import javafx.scene.control.Cell;

public class Board {
    private Cell[][] board;
    private static int size;

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
        for(int x=0; x<board.length; x++) {
            for(int y=0; y<board[1].length; y++) {
                board[x][y] = new Cell();
            }
        }
        return board;
    }

    public int getCellState(int row, int pos) {
        return this.getBoard()[row][pos].getState();
    }

    public static int[] convertPos(int pos) {
        int[] result = new int[2];
        int row = pos%size;
        int position = pos/size;
        result[0] = row;
        result[1] = position;

        return result;
    }

    public void setPosition(int selfOrOpponent, int pos) {
        int x = (pos/size);       // 0/3=0 1/3=0.33 2/3=0.66 etc.. 8/3=2.66 (2)
        int y = (pos%size);  // 0%3=0 2%3=2
        board[x][y].setState(selfOrOpponent);
    }

    public void printBoard() {
        for(int x=0; x<size; x++) {
            for(int y=0; y<size; y++) {
                System.out.print(board[x][y].getState() + " ");
            }
            System.out.println();
        }
    }

    public void clearBoard() {
        int pos = (size*size)-1;
        while(pos>=0) {
            setPosition(0, pos);
            pos--;
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
