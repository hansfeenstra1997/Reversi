package com.company;

public class TicTacToeController extends Controller{

    //Game game;
//    Model model;
//    View view;



    public TicTacToeController() {
        super();
        makeBoard(3);
    }

    public void makeBoard(int size) {
        board = new Board(size);
        board.printBoard();
    }

    @Override
    void setMove(int pos) {
        //controleren of move kan


        conn.sendCommand("move " + pos);
    }

    //of deze functie's moeten in Controller
    void updateBoard(){

    }

    void updateView(){

    }


}
