package com.company;

import com.company.connection.Connection;
import com.company.model.Board;

import java.util.ArrayList;

public class ManualPlayerTicTacToe implements Player {

    protected Board board;
    int boardSize;

    /**
     * Constructor Manual Player Tic-tac-toe
     * @param board - reference to Board
     */
    public ManualPlayerTicTacToe(Board board) {
        this.board = board;
        this.boardSize = board.getSize();
    }

    /**
     * Set manual move
     * @param position - position of move
     */
    @Override
    public void doMove(int position) {
        Connection.getInstance().sendCommand("move " + position);
    }

    /**
     * Returns all possible moves
     * @param player - player to check for (1=self, 2=opponent)
     * @param board - reference to Board
     * @param boardSize - board size
     * @return ArrayList of all possible moves
     */
    @Override
    public ArrayList<int[]> getPossibleMoves(int player, Board board, int boardSize) {
        return new ArrayList<>();
    }

    @Override
    public void flipBoard(int x, int y, int player) {}

}
