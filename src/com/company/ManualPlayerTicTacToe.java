package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class ManualPlayerTicTacToe implements Player {

    protected Board board;
    protected int boardSize;

    public ManualPlayerTicTacToe(Board board) {
        this.board = board;
        this.boardSize = board.getSize();
    }

    @Override
    public void doMove(int position) {
        System.out.println("test do move manual");
        Connection.getInstance().sendCommand("move " + position);
    }

    @Override
    public ArrayList<int[]> getPossibleMoves(int player, Board board, int boardSize) {
        return new ArrayList<int[]>();
    }

    @Override
    public void flipBoard(int x, int y, int player) {
        ;
    }
}
