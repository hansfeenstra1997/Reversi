package com.company;

import com.company.model.Board;

import java.util.ArrayList;

public interface Player {

    void doMove(int position);

    ArrayList<int[]> getPossibleMoves(int player, Board board, int boardSize);
    void flipBoard(int x, int y, int player);

}
