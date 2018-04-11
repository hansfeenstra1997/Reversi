package com.company.controller;

import com.company.*;
import com.company.connection.Connection;
import com.company.model.Board;
import com.company.view.LoadIcon;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import java.util.*;

public abstract class Controller implements Runnable{

    private static final int RESPONSETIME = 9;
    Connection conn;
    private ArrayList<Map.Entry<String, ArrayList<String>>> readerQueue;

    Board board;

    //Player section
    private Player player;
    AIReversiMiniMax ai;

    //need to cleanup
    private Stage stage;
    private BorderPane pane;
    private VBox main;
    private GridPane grid;
    private Text statusText;

    //??
    private VBox top;
    private VBox right;
    private BorderPane bottom;
    private Label timerText;
    private Label lastMove;

    //startingPlayer = player with first move
    //nextPlayer = player plays second
    //Players need to be refactored
    private String firstPlayer;
    private String secondPlayer;
    int firstPlayerID;
    int secondPlayerID;

    private VBox players;
    private Timer timer;
    private boolean timerRunning = false;
    private boolean activeGame = false;

    //reafcatoring needed
    public Controller(VBox playerList, Stage gameStage) {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().getQueue();

        stage = gameStage;
        players = playerList;
    }

    public void makeBoard(int size) {
        board = new Board(size);
    }

    public void setupFX(){
        pane = (BorderPane) stage.getScene().getRoot();
        main = (VBox) pane.getCenter();
        bottom = (BorderPane) pane.getBottom();
        statusText = (Text) bottom.getLeft();
        lastMove = (Label) bottom.getCenter();

        top = (VBox) pane.getTop();
        right = (VBox) pane.getRight();
        timerText = (Label) right.getChildren().get(0);

        main.setAlignment(Pos.CENTER);
        Label waitText = new Label("Waiting for match...");
        main.getChildren().add(waitText);

        stage.setTitle("Game");

        LoadIcon loadIconView = new LoadIcon();
        try {
            Image loadIcon = new Image(new FileInputStream("src/com/company/load.png"));
            loadIconView.setImage(loadIcon);
        }
        catch(FileNotFoundException e) {
            System.out.println("Load icon not found");
        }

        Platform.runLater(() -> main.getChildren().add(loadIconView));
    }

    abstract void initBoard();
    abstract void flipBoard(int x, int y, int selfOrOpponent);
    abstract void setMove(int pos);
    abstract ArrayList<int[]> getPossibleMoves();
    abstract Image setCellImage(int state);
    abstract String getGameName();

    public void makePlayer(int playerMode){
        if (playerMode == 0) {
            player = new ManualPlayer(Main.getPlayerName());
        } else if(playerMode == 1){
            //player = makeAI();
        }
    }

    private void readQueue() {
        Map.Entry<String, ArrayList<String>> command = readerQueue.get(0);
        System.out.println(command);
        readerQueue.remove(command);
        queueParser(command);
    }

    private void queueParser(Map.Entry<String, ArrayList<String>> command){

        if(command != null){
            String key = command.getKey();

            ArrayList<String> values = command.getValue();

            if(key.equals("PLAYERS")){
                Platform.runLater(()->{
                    players.getChildren().clear();
//
                    for(String value:values){
                        if (!value.equals(Main.getPlayerName())){
                            System.out.println(value);
                            Button button = new Button("Challenge: " + value);
                            button.setOnAction((event)->{
                                //int position = grid.getChildren().indexOf(event.getSource());
                                conn.sendCommand("challenge \"" + value + "\" \"" + this.getGameName() + "\"");
                                        //challenge accept 0
                            });
                            players.getChildren().add(button);
                        }
                    }
                });
            }

            if(key.equals("CHALLENGE")){
                Platform.runLater(()-> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Challenge Offered");
                    alert.setHeaderText(values.get(0) + " offered you to play the game: " + values.get(2));
                    alert.setContentText("Would you like to play this game?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        //send confurmation
                        conn.sendCommand("challenge accept " + values.get(1));
                        stage.show();
                        System.out.println("OK");
                    } else {
                        System.out.println("Cancel");
                    }
                });
            }

            if(key.equals("MATCH")){
                board.clearBoard();

                Platform.runLater(() -> {
                    stage.show();
                    statusText.setText("Opponent's turn");
                });
                activeGame = true;
                startTimer();
                String opponent = values.get(2);

                if(Main.getPlayerName().equals(values.get(0))){
                    firstPlayer = Main.getPlayerName();
                    secondPlayer = values.get(2);
                    firstPlayerID = 1;
                    secondPlayerID = 2;
                } else {
                    firstPlayer = values.get(2);
                    secondPlayer = Main.getPlayerName();
                    firstPlayerID = 2;
                    secondPlayerID = 1;
                }

                initBoard();

                //Show playernames on screen
                Platform.runLater(() -> {
                    HBox playerInfo = (HBox) top.getChildren().get(0);
                    HBox playColor = (HBox) top.getChildren().get(1);

                    Label playerName = (Label) playerInfo.getChildren().get(1);

                    playerName.setText(Main.getPlayerName() + " - ");
                    Label opponentName = (Label) playerInfo.getChildren().get(3);
                    opponentName.setText(opponent);

                    Label blackPlayer = (Label) playColor.getChildren().get(1);
                    blackPlayer.setText(firstPlayer + " - ");
                    Label whitePlayer = (Label) playColor.getChildren().get(3);
                    whitePlayer.setText(secondPlayer);
                });

                //playertomove is beginner
                //dus moet kruisje zijn
                //dit is het statement als er een nieuwe match is

                //makeGridpane
                updateBoard();
                if(values.get(0).equals(firstPlayer) && firstPlayerID==2) {
                    disableBoard();
                }
            }


            if (key.equals("MOVE")) {
                startTimer();
                int player = 1;
                if(!values.get(0).equals(Main.getPlayerName())){
                    player = 2;
                }
                int move = Integer.parseInt(values.get(1));

                if(move>=0 && move<=((board.getSize()*board.getSize())-1) && !values.get(2).equals("Illegal move")) {
                    int[] pos = Board.convertPos(move);
                    String playerName;
                    if(firstPlayerID == player) {
                        playerName = firstPlayer;
                    }
                    else {
                        playerName = secondPlayer;
                    }
                    flipBoard(pos[0], pos[1], player);
                    board.setPosition(player, move);

                    Platform.runLater(() -> lastMove.setText("Last move: " + playerName + " at " + (pos[0]+1) + ", "+ (pos[1]+1)));
                }

                updateBoard();
                if(player==1) {
                    disableBoard();
                    Platform.runLater(() -> statusText.setText("Opponent's turn"));
                }
                else {
                    Platform.runLater(() -> statusText.setText("Your turn"));
                }
            }

            if (key.equals("YOURTURN")){
                updateBoard();
                Platform.runLater(() -> statusText.setText("Your turn"));
                //AI mode;
                //Set 0 zero for manual
                if (Main.getPlayerMode() == 1){
                    int[] xy = ai.doEasyMove();
                    //@TODO REFACTORTING!!!!!!!!!!!
                    int pos = xy[0] * 8 + xy[1];

                    conn.sendCommand("move " + pos);
                }
            }

            if(key.equals("WIN")) {
                activeGame = false;
                Platform.runLater(() -> {
                    statusText.setText("You're the winner! Congratulations!");
                    statusText.setFill(Color.GREEN);
                });
                disableBoard();
                stopTimer();
            }

            if(key.equals("LOSS")) {
                activeGame = false;
                Platform.runLater(() -> {
                    statusText.setText("You lost the game :(");
                    statusText.setFill(Color.RED);
                });
                disableBoard();
                stopTimer();
            }
        }
    }

    private void stopTimer() {
        if(timerRunning) {
            timer.cancel();
            timer.purge();
            timerRunning = false;
        }
        Platform.runLater(() -> timerText.setText("-"));
    }

    private void startTimer() {
        stopTimer();
        if(activeGame) {
            Platform.runLater(() -> timerText.setText(Integer.toString(RESPONSETIME)));
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int interval = RESPONSETIME;

                @Override
                public void run() {
                    timerRunning = true;
                    if (interval > 0 && activeGame) {
                        Platform.runLater(() -> timerText.setText(Integer.toString(interval)));
                        interval--;
                    } else {
                        stopTimer();
                    }
                }
            }, (RESPONSETIME*100), (RESPONSETIME*100));
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


                int state = board.getBoard()[x][y].getState();
                Image image = this.setCellImage(state);
                ImageView imageV = new ImageView(image);
                imageV.setFitWidth(50);
                imageV.setFitHeight(50);
                Button button = new Button("", imageV);

                //call to ConcreteController
                //Puts the right characters on the screen
                //TTT: X or O
                //Rev: Black or White

                //needs to be image



                //button.setText(image);
                button.setOnAction((event)->{
                    int position = grid.getChildren().indexOf(event.getSource());
                    this.setMove(position);
                });

                button.setMinSize(50,50);
                grid.add(button, y, x);
            }
        }

        //possible moves
        ArrayList<int[]> possibleMoves =  this.getPossibleMoves();

        for(int[] move: possibleMoves) {
            //get cell; cell xy omzetten naar positie
            //de button daarvan ophalen en dan veradneren
            Button possibleButton = (Button) grid.getChildren().get((move[1]* board.getSize())+move[0]);

            possibleButton.setBackground(new Background(new BackgroundFill(Color.rgb(0,128,0), null, null)));
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
