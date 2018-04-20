package com.company.controller;

class GameFactory {
    /**
     * makeGame in GameFactory
     * @param game
     * @return
     * This functions return a new Concrete controller of the GameController
     */
    GameController makeGame(String game) {
        switch(game) {
            case "Tic-tac-toe":
                return new TicTacToeController();
            case "Reversi":
                return new ReversiController();
        }
        return null;
    }
}
