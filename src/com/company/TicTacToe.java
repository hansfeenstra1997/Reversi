package com.company;

public class TicTacToe implements Game {
    private Board board;

    public TicTacToe() {
        makeBoard(3);
    }

    public void makeBoard(int size) {
        board = new Board(size);
        board.printBoard();
    }

}
