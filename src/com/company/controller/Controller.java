package com.company.controller;

import com.company.*;
import com.company.connection.Connection;
import com.company.model.Board;
import com.company.view.LoadIcon;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
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

public class Controller implements Runnable{

    private static final int RESPONSETIME = 9;
    Connection conn;
    private ArrayList<Map.Entry<String, ArrayList<String>>> readerQueue;

    //Board board;

    //Game Section
    GameController gameController;

    //view parts
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

    public void makePlayer(String gameMode){
        gameController.makePlayer(gameMode);
    }

    public void makeGameController(String gameName){
        GameFactory gameFactory = new GameFactory();
        gameController = gameFactory.makeGame(gameName);
        //removed from main, currently no idea how to use it
        // or in controller of gamecontroller
        //gameController.makePlayer(playerMode);
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
                                conn.sendCommand("challenge \"" + value + "\" \"" + gameController.getGameName() + "\"");
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
                        //stage.show();
                        System.out.println("OK");
                    } else {
                        System.out.println("Cancel");
                    }
                });
            }

            if(key.equals("MATCH")){
                gameController.board.clearBoard();

                Platform.runLater(() -> {
                    stage.show();
                    statusText.setText("Opponent's turn");
                });
                activeGame = true;
                startTimer();
                String opponent = values.get(2);

                if(Main.getPlayerName().equals(values.get(0))){
                    gameController.setPlayers(Main.getPlayerName(), values.get(2), 1, 2);
                } else {
                    gameController.setPlayers(values.get(2), Main.getPlayerName(), 2, 1);
                }

                gameController.initBoard();

                //Show playernames on screen
                Platform.runLater(() -> {
                    HBox playerInfo = (HBox) top.getChildren().get(0);
                    HBox playColor = (HBox) top.getChildren().get(1);

                    Label playerName = (Label) playerInfo.getChildren().get(1);

                    playerName.setText(Main.getPlayerName() + " - ");
                    Label opponentName = (Label) playerInfo.getChildren().get(3);
                    opponentName.setText(opponent);

                    Label blackPlayer = (Label) playColor.getChildren().get(1);
                    blackPlayer.setText(gameController.getFirstPlayer() + " - ");
                    Label whitePlayer = (Label) playColor.getChildren().get(3);
                    whitePlayer.setText(gameController.getSecondPlayer());
                });

                //playertomove is beginner
                //dus moet kruisje zijn
                //dit is het statement als er een nieuwe match is

                //makeGridpane
                updateBoard();
                if(values.get(0).equals(gameController.getFirstPlayer()) && gameController.getFirstPlayerID()==2) {
                    disableBoard();
                }
            }


            if (key.equals("MOVE")) {
                Platform.runLater(() -> statusText.setFill(Color.BLACK));
                startTimer();
                int player = 1;
                if(!values.get(0).equals(Main.getPlayerName())){
                    player = 2;
                }
                int move = Integer.parseInt(values.get(1));

                if(move>=0 && move<=((gameController.board.getSize()* gameController.board.getSize())-1) && !values.get(2).equals("Illegal move")) {
                    int[] pos = Board.convertPos(move);
                    String playerName;
                    if(gameController.getFirstPlayerID() == player) {
                        playerName = gameController.getFirstPlayer();
                    }
                    else {
                        playerName = gameController.getSecondPlayer();
                    }
                    gameController.flipBoard(pos[0], pos[1], player);
                    gameController.board.setPosition(player, move);

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

                if(this.gameController.gameMode.contains("ai")) {
                    // calculate move AI
                    //@TODO steeds gamcontroller. aanroepen miss even anders
                    gameController.player.doMove(-1);
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

            if(key.equals("DRAW")) {
                activeGame = false;
                Platform.runLater(() -> {
                    statusText.setText("It's a draw!");
                    statusText.setFill(Color.BLUE);
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

        for(int x = 0; x < gameController.board.getSize(); x++){
            for(int y = 0; y < gameController.board.getSize(); y++){


                int state = gameController.board.getBoard()[x][y].getState();
                Image image = gameController.setCellImage(state);
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
                    gameController.setMove(position);
                });

                button.setMinSize(50,50);
                grid.add(button, y, x);
            }
        }

        //possible moves
        ArrayList<int[]> possibleMoves =  gameController.getPossibleMoves();

        for(int[] move: possibleMoves) {
            //get cell; cell xy omzetten naar positie
            //de button daarvan ophalen en dan veradneren
            Button possibleButton = (Button) grid.getChildren().get((move[1]* gameController.board.getSize())+move[0]);

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
