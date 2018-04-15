package com.company;

import com.company.connection.Connection;
import com.company.controller.Controller;
import com.company.controller.GameFactory;
import com.company.view.*;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private GameFactory gameFactory = new GameFactory();

    private static Controller headController;
    private static String playerName;
    private static int playerMode = 0;
    private static VBox players;
    
    public static void main(String[] args) {
        Application.launch(LauncherView.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartView startView = new StartView(primaryStage);

        startView.getStartBtn().setOnAction((event) -> startGame("Tic-tac-toe", "manual"));
        startView.getStartReversiBtn().setOnAction((event) -> startGame("Reversi", "ai-hard"));

        startView.getLoginBtn().setOnAction((event) -> login(startView.getUsernameFieldText()));
        startView.getCommandButton().setOnAction((event) -> {sendCommand(startView.getCommandFieldText()); startView.setCommandFieldText("");});
    }

    private static void sendCommand(String command) {
        Connection.getInstance().sendCommand(command);
    }

    public static void login(String username) {
        playerName = username;
        sendCommand("login " + username);
    }

    public static void startGame(String gameName, String gameMode) {
        Stage gameStage = new Stage();
        players = new VBox();

        headController = new Controller(players, gameStage);
        headController.makeGameController(gameName);
        headController.makePlayer(gameMode);

        makeChoiceScreen(gameStage, gameName);

        Thread thread = new Thread(headController);
        thread.start();
    }

    private static void makeChoiceScreen(Stage gameStage, String gameName){
        new ChoiceScreen(gameStage, players, gameName);
        makeScene(gameStage);
    }

    private static void makeScene(Stage gameStage){
        new BoardView(gameStage);
        headController.setupFX();
    }

    public static String getPlayerName() {
        return playerName;
    }
    public static int getPlayerMode() {
        return playerMode;
    }
}


