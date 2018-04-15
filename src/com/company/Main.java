package com.company;

import com.company.connection.Connection;
import com.company.controller.Controller;
import com.company.controller.GameFactory;
import com.company.view.BoardView;
import com.company.view.ChoiceScreen;
import com.company.view.LauncherView;
import com.company.view.StartView;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main {

    private GameFactory gameFactory = new GameFactory();

    private static Controller headController;
    private static String playerName;
    private static int playerMode = 0;
    private static VBox players;
    
    public static void main(String[] args) {
        Application.launch(LauncherView.class, args);
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


