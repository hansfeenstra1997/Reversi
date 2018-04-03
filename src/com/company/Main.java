package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {

    private GameFactory gameFactory = new GameFactory();

    Controller gameController;
    //static Board board;

    //feur game is shitty
    Stage gameStage = new Stage();
    BorderPane gamePane = new BorderPane();
    VBox gameMain = new VBox();
    GridPane gameControlPane = new GridPane();

    static String playerName;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(200, 200);
        VBox main = new VBox();
        GridPane controlPane = new GridPane();

        Button startBtn = new Button("Start Tic-Tac-Toe");
        startBtn.setOnAction((event) -> startGame("Tic-tac-toe", 0));
        controlPane.add(startBtn, 0, 0);

        TextField usernameField = new TextField();
        controlPane.add(usernameField, 0, 1);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction((event) -> login(usernameField.getText()));
        controlPane.add(loginBtn, 1, 1);

        TextField commandField = new TextField();
        controlPane.add(commandField,0, 2);
        Button commandButton = new Button("Send");
        commandButton.setOnAction((event) -> {sendCommand(commandField.getText()); commandField.setText("");});
        controlPane.add(commandButton,1,2);

        main.getChildren().add(controlPane);

        mainPane.setCenter(main);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendCommand(String command) {
        Connection.getInstance().sendCommand(command);
    }

    private void login(String username) {
        playerName = username;
        Connection.getInstance().sendCommand("login " + username);
    }

    private void startGame(String gameName, int gameMode) {
        Connection.getInstance().sendCommand("subscribe " + gameName);
        Runnable game = gameFactory.makeGame(gameName, gameStage, gamePane, gameMain, gameControlPane);

        //TicTacToeController tic = (TicTacToeController) game;
        gameController = (TicTacToeController) game;
        makeScene(gameController.board);

        Thread thread = new Thread(game);
        thread.start();

    }

    public void makeScene(Board board){
        gamePane.setMinSize(200, 200);
        updateScene(board);
    }

    public void updateScene(Board board){

        gameControlPane.getChildren().clear();

        for(int y = 0; y < board.getSize(); y++){
            for(int x = 0; x < board.getSize(); x++){
                Button button = new Button();
                int state = board.getBoard()[x][y].getState();
                button.setText(Integer.toString(state));
                button.setOnAction((event) -> {
                    int position = gameControlPane.getChildren().indexOf(event.getSource());
                    gameController.setMove(position);
                    System.out.println(position);
                });

                button.setMinSize(50,50);
                gameControlPane.add(button, x, y);
            }
        }

        gameMain.getChildren().add(gameControlPane);
        gamePane.setCenter(gameMain);

        Scene scene = new Scene(gamePane);
        gameStage.setScene(scene);
        gameStage.show();
    }

}


