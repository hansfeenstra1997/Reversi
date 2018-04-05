package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    private GameFactory gameFactory = new GameFactory();
    Controller gameController;

    static String playerName;
    static int playerMode = 0;
    
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

        Button startReversiBtn = new Button("Start Reversi");
        startReversiBtn.setOnAction((event) -> startGame("Reversi", 0));
        controlPane.add(startReversiBtn, 0, 1);

        TextField usernameField = new TextField();
        controlPane.add(usernameField, 0, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction((event) -> login(usernameField.getText()));
        controlPane.add(loginBtn, 1, 2);

        TextField commandField = new TextField();
        controlPane.add(commandField,0, 3);
        Button commandButton = new Button("Send");
        commandButton.setOnAction((event) -> {sendCommand(commandField.getText()); commandField.setText("");});
        controlPane.add(commandButton,1,3);

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

        Stage gameStage = new Stage();
        gameController = gameFactory.makeGame(gameName, gameStage);
        gameController.makePlayer(playerMode);

        makeScene(gameStage);

        Thread thread = new Thread(gameController);
        thread.start();
    }

    public void makeScene(Stage gameStage){

        BorderPane gameBorderPane = new BorderPane();
        VBox gameVBox = new VBox();

        gameBorderPane.setMinSize(500, 500);

        HBox top = new HBox();

        Label player = new Label("Player:");
        Label playerName = new Label();

        Label opponent = new Label("Opponent:");
        Label opponentName = new Label();

        top.getChildren().addAll(player, playerName, opponent, opponentName);

        gameBorderPane.setTop(top);
        gameBorderPane.setCenter(gameVBox);

        Text turn = new Text("test");
        turn.setId("turnText");
        gameBorderPane.setBottom(turn);

        Scene scene = new Scene(gameBorderPane);
        gameStage.setScene(scene);
        gameStage.show();

        gameController.setupFX();

    }


}


