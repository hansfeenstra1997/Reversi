package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {

    private GameFactory gameFactory = new GameFactory();

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
        Connection.getInstance().sendCommand("login " + username);
    }

    private void startGame(String gameName, int gameMode) {
        Connection.getInstance().sendCommand("subscribe " + gameName);
        Runnable game = gameFactory.makeGame(gameName);

        Thread thread = new Thread(game);
        thread.start();

    }
}
