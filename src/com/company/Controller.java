package com.company;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Map;

public abstract class Controller implements Runnable{

    Connection conn;
    ArrayList<Map.Entry<String, ArrayList<String>>> readerQueue;

    Board board;

    //Player section
    Player player;
    AIPlayerMiniMax ai;

    //need to cleanup
    Stage stage;
    BorderPane pane;
    VBox main;
    GridPane grid;
    Text turnText;

    //??
    VBox top;

    //startingPlayer = player with first move
    //nextPlayer = player plays second
    //Players need to be refactored
    String firstPlayer;
    String secondPlayer;
    int firstPlayerID;
    int secondPlayerID;


    public Controller(Stage gameStage) {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().queue;

        stage = gameStage;
    }

    public void makeBoard(int size) {
        board = new Board(size);
    }

    void setupFX(){
        pane = (BorderPane) stage.getScene().getRoot();
        main = (VBox) pane.getCenter();
        turnText = (Text) pane.getBottom();

        top = (VBox) pane.getTop();
    }

    abstract void initBoard();
    abstract void flipBoard(int x, int y, int selfOrOpponent);
    abstract void setMove(int pos);

    //needs to be image
    abstract String setCellImage(int state);

    void makePlayer(int playerMode){
        if (playerMode == 0) {
            player = new ManualPlayer(Main.playerName);
        } else if(playerMode == 1){
            //player = makeAI();
        }
    }

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
                String opponent = values.get(2);

                if(Main.playerName.equals(values.get(0))){
                    firstPlayer = Main.playerName;
                    secondPlayer = values.get(2);
                    firstPlayerID = 1;
                    secondPlayerID = 2;
                } else {
                    firstPlayer = values.get(2);
                    secondPlayer = Main.playerName;
                    firstPlayerID = 2;
                    secondPlayerID = 1;
                }

                initBoard();

                //Show playernames on screen
                Platform.runLater(() -> {
                    HBox playerInfo = (HBox) top.getChildren().get(0);
                    HBox playColor = (HBox) top.getChildren().get(1);

                    Label playerName = (Label) playerInfo.getChildren().get(1);
                    playerName.setText(player.playerName);
                    Label opponentName = (Label) playerInfo.getChildren().get(3);
                    opponentName.setText(opponent);

                    Label blackPlayer = (Label) playColor.getChildren().get(1);
                    blackPlayer.setText(firstPlayer);
                    Label whitePlayer = (Label) playColor.getChildren().get(3);
                    whitePlayer.setText(secondPlayer);
                });

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
                int move = Integer.parseInt(values.get(1));

                int[] pos = Board.convertPos(move);
                flipBoard(pos[0], pos[1], player);

                if(move>=0 && move<=((board.getSize()*board.getSize())-1) && !values.get(2).equals("Illegal move"))
                    board.setPosition(player, move);

                updateBoard();
                if(player==1) {
                    disableBoard();
                    Platform.runLater(() -> turnText.setText("Opponent's turn"));
                }
                else {
                    Platform.runLater(() -> turnText.setText("Your turn"));
                }
            }

            if (key == "YOURTURN"){
                //AI mode;
                //Set 0 zero for manual
                if (Main.playerMode == 1){
                    int[] xy = ai.doMove();
                    int pos = xy[0] * 3 + xy[1];

                    conn.sendCommand("move " + pos);
                }
            }

            if(key.equals("WIN")) {
                Platform.runLater(() -> {
                    turnText.setText("You're the winner! Congratulations!");
                    turnText.setFill(Color.GREEN);
                });
                disableBoard();
            }

            if(key.equals("LOSS")) {
                Platform.runLater(() -> {
                    turnText.setText("You lost the game :(");
                    turnText.setFill(Color.RED);
                });
                disableBoard();
            }
        }
    }

    private void disableBoard() {
        Platform.runLater(() -> {
            for(Node node: grid.getChildren()) {
                node.setDisable(true);
            }
        });
    }

    void updateBoard(){

        grid = new GridPane();

        for(int x = 0; x < board.getSize(); x++){
            for(int y = 0; y < board.getSize(); y++){
                Button button = new Button();
                int state = board.getBoard()[x][y].getState();

                //call to ConcreteController
                //Puts the right characters on the screen
                //TTT: X or O
                //Rev: Black or White

                //needs to be image
                String image = this.setCellImage(state);

                button.setText(image);
                button.setOnAction((event)->{
                    int position = grid.getChildren().indexOf(event.getSource());
                    this.setMove(position);
                });

                button.setMinSize(50,50);
                grid.add(button, y, x);
            }
        }

        Platform.runLater(()->{
            main.getChildren().clear();
            main.getChildren().add(grid);

            stage.sizeToScene();
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
