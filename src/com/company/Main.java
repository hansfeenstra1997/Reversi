package com.company;

import com.company.connection.Connection;
import com.company.controller.Controller;
import com.company.view.BoardView;
import com.company.view.ChoiceScreen;
import com.company.view.StartView;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    private Controller headController;
    private static String playerName;
    private static int playerMode = 0;

    /**
     * main in Main
     * @param args
     * This functions will start the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start in Main
     * @param primaryStage
     * @throws Exception
     * This function is the start of the JavaFX application. This is the function that wel be invoked when starting.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        StartView startView = new StartView(primaryStage);

        startView.getStartBtn().setOnAction((event) -> startGame("Tic-tac-toe", "ai-easy"));
        startView.getStartReversiBtn().setOnAction((event) -> startGame("Reversi", "ai-hard"));

        startView.getLoginBtn().setOnAction((event) -> login(startView.getUsernameFieldText()));
        startView.getCommandButton().setOnAction((event) -> {sendCommand(startView.getCommandFieldText()); startView.setCommandFieldText("");});
    }

    /**
     * sendCommand in Main
     * @param command
     * This function will send the command to the server via the writer
     */
    private void sendCommand(String command) {
        Connection.getInstance().sendCommand(command);
    }

    /**
     * login in Main
     * @param username
     * This function will send the login command to the server.
     */
    private void login(String username) {
        playerName = username;
        sendCommand("login " + username);
    }

    /**
     * startGame in Main
     * @param gameName
     * @param gameMode
     * This function will create a controller and that controller will make an concrete gamecontroller.
     * Also this function will start the queueReader thread.
     */
    private void startGame(String gameName, String gameMode) {

        headController = new Controller(new ChoiceScreen(gameName), new BoardView());
        headController.makeGameController(gameName);
        headController.makePlayer(gameMode);

        headController.setupFX();

        Thread thread = new Thread(headController);
        thread.start();
    }

    /**
     * getPlayerName in Main
     * @return
     * This function will return the playerName field
     */
    public static String getPlayerName() {
        return playerName;
    }

    /**
     * getPlayerMode in Main
     * @return
     * This function will return the playerMode field
     */
    public static int getPlayerMode() {
        return playerMode;
    }
}


