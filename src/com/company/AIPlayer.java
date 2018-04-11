package com.company;

import com.company.model.Board;

public abstract class AIPlayer {

    abstract int checkWinner(Board.Cell[][] cell);

    abstract int[] doMove();

    class BestMove {
        int score, x, y;

        BestMove(int score, int x, int y) {
            this.score = score;
            this.x = x;
            this.y = y;
        }

    }
}
