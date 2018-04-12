package com.company;

import com.company.connection.Connection;
import com.company.controller.Controller;
import com.company.controller.GameFactory;
import com.company.view.BoardView;
import com.company.view.ChoiceScreen;
import com.company.view.StartView;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private GameFactory gameFactory = new GameFactory();

    private Controller headController;
    private static String playerName;
    private static int playerMode = 0;
    private VBox players;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartView startView = new StartView(primaryStage);

        startView.getStartBtn().setOnAction((event) -> startGame("Tic-tac-toe", 0));
        startView.getStartReversiBtn().setOnAction((event) -> startGame("Reversi", 0));
        startView.getLoginBtn().setOnAction((event) -> login(startView.getUsernameFieldText()));
        startView.getCommandButton().setOnAction((event) -> {sendCommand(startView.getCommandFieldText()); startView.setCommandFieldText("");});
    }

    private void sendCommand(String command) {
        Connection.getInstance().sendCommand(command);
    }

    private void login(String username) {
        playerName = username;
        sendCommand("login " + username);
    }

    private void startGame(String gameName, int gameMode) {
        //kiezen tussen subscriben en match aangeboden krijgen
        //sendCommand("subscribe " + gameName);

        Stage gameStage = new Stage();
        players = new VBox();

        headController = new Controller(players, gameStage);
        headController.makeGameController(gameName);


        makeChoiceScreen(gameStage, gameName);

        Thread thread = new Thread(headController);
        thread.start();
    }

    private void makeChoiceScreen(Stage gameStage, String gameName){
        new ChoiceScreen(gameStage, players, gameName);
        makeScene(gameStage);
    }

    private void makeScene(Stage gameStage){
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


