package com.company;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Map;

public abstract class Controller implements Runnable{

    Connection conn;
    ArrayList<Map.Entry<String, ArrayList<String>>> readerQueue;

    Board board;

    AIPlayerMiniMax ai;

    //need to cleanup
    Stage stage;
    BorderPane pane;
    VBox main;
    GridPane grid;

    public Controller(Stage gameStage) {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().queue;

        stage = gameStage;
    }

    public void makeBoard(int size) {
        board = new Board(size);
        //board.printBoard();
    }

    void setupFX(){
        pane = (BorderPane) stage.getScene().getRoot();
        main = (VBox) pane.getCenter();
        grid = new GridPane();
    }

    abstract void setMove(int pos);

    void readQueue() {
        Map.Entry<String, ArrayList<String>> command = readerQueue.get(0);
        readerQueue.remove(command);
        queueParser(command);

    }

    void queueParser(Map.Entry<String, ArrayList<String>> command){

        if(command != null){
            String key = command.getKey();

            ArrayList<String> values = command.getValue();
            if(key == "MATCH"){
                //playertomove is beginner
                //dus moet kruisje zijn
                //dit is het statement als er een nieuwe match is

                //makeGridpane
                updateBoard();
            }


            if (key == "MOVE") {
                int player = 1;
                if(!values.get(0).equals(Main.playerName)){
                    player = 2;
                }

                board.setPosition(player, Integer.parseInt(values.get(1)));
                System.out.println("Updated board:");
                board.printBoard();
                updateBoard();
            }

            if (key == "YOURTURN"){
                if (Main.playerMode == 1){


                    Integer[] xy = ai.printHashMap();
                    int pos = xy[0] * 3 + xy[1];
                    //System.out.println(xy);
                    //System.out.println(pos);

                    conn.sendCommand("move " + pos);
                    //board.setPosition(1, pos);
                    //updateBoard();
                }
            }
        }

    }

    void makeBoard(){

    }

    void updateBoard(){

        Platform.runLater(()->{
            main.getChildren().clear();

            grid.getChildren().clear();

            for(int x = 0; x < board.getSize(); x++){
                for(int y = 0; y < board.getSize(); y++){
                    Button button = new Button();
                    int state = board.getBoard()[x][y].getState();
                    button.setText(Integer.toString(state));
                    button.setOnAction((event)->{
                        int position = grid.getChildren().indexOf(event.getSource());
                        this.setMove(position);
                        //System.out.println(position);
                    });

                    button.setMinSize(50,50);
                    grid.add(button, y, x);
                }
            }


            main.getChildren().add(grid);

            stage.show();
        });

    }

    @Override
    public void run() {
        while(true){
            try{
                if (!readerQueue.isEmpty()){
                    readQueue();
                }
                Thread.sleep(500);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

        }

    }

}
