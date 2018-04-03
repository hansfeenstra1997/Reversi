package com.company;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public abstract class Controller implements Runnable{

    Connection conn;
    ArrayList<Map.Entry<String, ArrayList<String>>> readerQueue;

    Board board;

    //need to cleanup
    Stage stage;
    BorderPane pane;
    VBox main;
    GridPane grid;

    public Controller() {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().queue;
    }

    abstract void setMove(int pos);

    void readQueue() {
        Map.Entry<String, ArrayList<String>> command = readerQueue.get(readerQueue.size() - 1);
        readerQueue.remove(command);
        queueParser(command);

    }

    void queueParser(Map.Entry<String, ArrayList<String>> command){

        if(command != null){
            System.out.println(command);
            String key = command.getKey();

            ArrayList<String> values = command.getValue();
            if (key == "MOVE") {
                System.out.println(values.get(1));
                int player = 1;
                if(!values.get(0).replace("\"", "").equals(Main.playerName)){
                    player = 2;
                }
                board.setPosition(player, Integer.parseInt(values.get(1).replace("\"", "")));
                updateBoard();
            }
        }

    }

    void updateBoard(){
        Platform.runLater(()->{
            main.getChildren().clear();
            grid.getChildren().clear();

            System.out.println("board size: " + board.getSize());

            for(int y = 0; y < board.getSize(); y++){
                for(int x = 0; x < board.getSize(); x++){
                    Button button = new Button();
                    int state = board.getBoard()[x][y].getState();
                    button.setText(Integer.toString(state));
                    button.setOnAction((event)->{
                        int position = grid.getChildren().indexOf(event.getSource());
                        this.setMove(position);
                        System.out.println(position);
                    });

                    button.setMinSize(50,50);
                    grid.add(button, x, y);
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
