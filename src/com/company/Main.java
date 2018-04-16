package com.company;

import com.company.connection.Connection;
import com.company.controller.Controller;
import com.company.view.BoardView;
import com.company.view.ChoiceScreen;
import com.company.view.LauncherView;
import com.company.view.StartView;
import javafx.application.Application;

public class Main {

    private static Controller headController;
    private static String playerName;
    private static int playerMode = 0;

    /**
     * main in Main
     * @param args
     * This functions will start the application
     */
    public static void main(String[] args) {
        Application.launch(LauncherView.class, args);
    }

    /**
     * sendCommand in Main
     * @param command
     * This function will send the command to the server via the writer
     */
    private static void sendCommand(String command) {
        Connection.getInstance().sendCommand(command);
    }

    /**
     * login in Main
     * @param username
     * This function will send the login command to the server.
     */
    public static void login(String username) {
        playerName = username.replaceAll("[^a-zA-Z0-9 ]", "");
        playerName = playerName.trim();
        sendCommand("login " + playerName);
    }

    /**
     * startGame in Main
     * @param gameName
     * @param gameMode
     * This function will create a controller and that controller will make an concrete gamecontroller.
     * Also this function will start the queueReader thread.
     */
    public static void startGame(String gameName, String gameMode) {

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


