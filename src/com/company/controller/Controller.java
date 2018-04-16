package com.company.controller;

import com.company.*;
import com.company.connection.Connection;
import com.company.model.Board;
import com.company.view.BoardView;
import com.company.view.LoadIcon;
import com.company.view.View;
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
    private Connection conn;
    private ArrayList<Map.Entry<String, ArrayList<String>>> readerQueue;

    //Controller
    GameController gameController;

    //View
    BoardView boardView;


    private GridPane grid;

    //private Label timerText;

    private VBox players;


    private Timer timer;
    private boolean timerRunning = false;
    private boolean activeGame = false;

    private Parser parser = new Parser();

    /**
     * Constructor of Controller
     * @param playerList
     * @param view
     * Initializes the variables of the controller.
     */
    public Controller(VBox playerList, BoardView view) {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().getQueue();

        boardView = view;

        players = playerList;
    }

    /**
     * makePlayer in Controller
     * @param gameMode
     * This function calls the makePlayer function in the concrete gameController
     */
    public void makePlayer(String gameMode){
        gameController.makePlayer(gameMode);
    }

    /**
     * makeGameController in Controller
     * @param gameName
     * This function creates a gameFactory which will create a concrete gameController
     */
    public void makeGameController(String gameName){
        GameFactory gameFactory = new GameFactory();
        gameController = gameFactory.makeGame(gameName);
    }

    /**
     * setupFX in Controller
     * This function fills all the JavaFX variables for updateing the board status on the view
     */
    public void setupFX(){

        boardView.getCenter().setAlignment(Pos.CENTER);
        Label waitText = new Label("Waiting for match...");
        boardView.getCenter().getChildren().add(waitText);

        boardView.getStage().setTitle("Game");

        LoadIcon loadIconView = new LoadIcon();
        try {
            Image loadIcon = new Image(new FileInputStream("src/com/company/load.png"));
            loadIconView.setImage(loadIcon);
        }
        catch(FileNotFoundException e) {
            System.out.println("Load icon not found");
        }

        Platform.runLater(() -> boardView.getCenter().getChildren().add(loadIconView));
    }

    /**
     * readQueue in Controller
     * This function reads the queue from the reader, this function will execute the queueParser
     * This function will be called every 500 milliseconds.
     */
    private void readQueue() {
        Map.Entry<String, ArrayList<String>> command = readerQueue.get(0);
        System.out.println(command);
        readerQueue.remove(command);
        queueParser(command);
    }

    /**
     * queueParser in Controller
     * @param command
     * This function parses the command from the server. Calls the right function in private Parser class.
     */
    private void queueParser(Map.Entry<String, ArrayList<String>> command){
        if(command != null){
            String key = command.getKey();

            ArrayList<String> values = command.getValue();

            switch(key) {
                case "PLAYERS":
                    parser.parsePlayers(values);
                    break;
                case "CHALLENGE":
                    parser.parseChallenge(values);
                    break;
                case "MATCH":
                    parser.parseMatch(values);
                    break;
                case "MOVE":
                    parser.parseMove(values);
                    break;
                case "YOURTURN":
                    parser.parseYourTurn(values);
                    break;
                case "LOSS":
                    parser.parseLoss(values);
                    break;
                case "BRAW":
                    parser.parseDraw(values);
                    break;
                case "WIN":
                    parser.parseWin(values);
                    break;
            }
        }
    }

    /**
     * stopTimer in Controller
     * This function stops the timer that will show in screen.
     */
    private void stopTimer() {
        if(timerRunning) {
            timer.cancel();
            timer.purge();
            timerRunning = false;
        }
        Platform.runLater(() -> boardView.getTimerText().setText("-"));
    }

    /**
     * startTimer in Controller
     * This function start the timer that will show in screen.
     */
    private void startTimer() {
        stopTimer();
        if(activeGame) {
            Platform.runLater(() -> boardView.getTimerText().setText(Integer.toString(RESPONSETIME)));
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int interval = RESPONSETIME;

                @Override
                public void run() {
                    timerRunning = true;
                    if (interval > 0 && activeGame) {
                        Platform.runLater(() -> boardView.getTimerText().setText(Integer.toString(interval)));
                        interval--;
                    } else {
                        stopTimer();
                    }
                }
            }, (RESPONSETIME*100), (RESPONSETIME*100));
        }
    }

    /**
     * disableBoard in Controller
     * This function disables the buttons when the it's the opoonent's turn
     */
    private void disableBoard() {
        Platform.runLater(() -> {
            for(Node node: grid.getChildren()) {
                node.setDisable(true);
            }
        });
    }

    /**
     * updateBoard in Controller
     * This function updates the view after every "MOVE" from the server
     */
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

                button.setOnAction((event)->{
                    int position = grid.getChildren().indexOf(event.getSource());
                    gameController.setMove(position);
                });

                button.setMinSize(50,50);
                grid.add(button, y, x);
            }
        }

        //possible moves
        ArrayList<int[]> possibleMoves =  gameController.player.getPossibleMoves(1, gameController.board, gameController.board.getSize());

        for(int[] move: possibleMoves) {
            //get cell; cell xy omzetten naar positie
            //de button daarvan ophalen en dan veradneren
            Button possibleButton = (Button) grid.getChildren().get((move[1]* gameController.board.getSize())+move[0]);

            possibleButton.setBackground(new Background(new BackgroundFill(Color.rgb(0,128,0), null, null)));
        }

        Platform.runLater(()->{
            boardView.getCenter().getChildren().clear();
            boardView.getCenter().getChildren().add(grid);

            boardView.getStage().sizeToScene();
            boardView.getStage().show();
        });
    }

    /**
     * run in Controller
     * This function executes every 500 milliseconds the readQueue function
     */
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


    // ------------ PARSING ---------------- //


    private class Parser {
        private void parsePlayers(ArrayList<String> values) {
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
        private void parseChallenge(ArrayList<String> values) {
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
        private void parseMatch(ArrayList<String> values) {
            gameController.board.clearBoard();

            Platform.runLater(() -> {
                boardView.getStage().show();
                boardView.getTurnText().setText("Opponent's turn");
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
                VBox top1 = (VBox) boardView.getTop().getChildren().get(0);
                HBox playerInfo = (HBox) top1.getChildren().get(0);
                HBox playColor = (HBox) top1.getChildren().get(1);

                Label playerName = (Label) playerInfo.getChildren().get(1);

                playerName.setText(Main.getPlayerName() + " - ");
                Label opponentName = (Label) playerInfo.getChildren().get(3);
                opponentName.setText(opponent);

                Label blackPlayer = (Label) playColor.getChildren().get(1);
                blackPlayer.setText(gameController.getFirstPlayer() + " - ");
                Label whitePlayer = (Label) playColor.getChildren().get(3);
                whitePlayer.setText(gameController.getSecondPlayer());
            });

            //makeGridpane
            updateBoard();
            if(values.get(0).equals(gameController.getFirstPlayer()) && gameController.getFirstPlayerID()==2) {
                disableBoard();
            }
        }
        private void parseMove(ArrayList<String> values) {
            Platform.runLater(() -> boardView.getTurnText().setFill(Color.BLACK));
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
                gameController.player.flipBoard(pos[0], pos[1], player);
                gameController.board.setPosition(player, move);

                Platform.runLater(() -> boardView.getLastMove().setText("Last move: " + playerName + " at " + (pos[0]+1) + ", "+ (pos[1]+1)));
            }

            updateBoard();
            if(player==1) {
                disableBoard();
                Platform.runLater(() -> boardView.getTurnText().setText("Opponent's turn"));
            }
            else {
                Platform.runLater(() -> boardView.getTurnText().setText("Your turn"));
            }
        }
        private void parseYourTurn(ArrayList<String> values) {
            updateBoard();
            Platform.runLater(() -> boardView.getTurnText().setText("Your turn"));
            //AI mode;
            //Set 0 zero for manual

            if(gameController.gameMode.contains("ai")) {
                // calculate move AI
                //@TODO steeds gamcontroller. aanroepen miss even anders
                gameController.player.doMove(-1);
            }
        }
        private void parseDraw(ArrayList<String> values) {
            activeGame = false;
            Platform.runLater(() -> {
                boardView.getTurnText().setText("It's a draw!");
                boardView.getTurnText().setFill(Color.BLUE);
            });
            disableBoard();
            stopTimer();
        }
        private void parseWin(ArrayList<String> values) {
            activeGame = false;
            Platform.runLater(() -> {
                boardView.getTurnText().setText("You're the winner! Congratulations!");
                boardView.getTurnText().setFill(Color.GREEN);
            });
            disableBoard();
            stopTimer();
        }
        private void parseLoss(ArrayList<String> values) {
            activeGame = false;
            Platform.runLater(() -> {
                boardView.getTurnText().setText("You lost the game :(");
                boardView.getTurnText().setFill(Color.RED);
            });
            disableBoard();
            stopTimer();
        }
    }

}
