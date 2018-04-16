package com.company.controller;

import com.company.connection.Reader;
import com.company.connection.Writer;
import com.company.view.ErrorWindow;
import com.company.Main;
import com.company.model.LauncherModel;
import com.company.view.LauncherView;
import com.sun.org.apache.bcel.internal.classfile.Unknown;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LauncherController {

    //SETTINGS:
    private static String game = ""; // BKE or Reversi?
    private static String playerName;
    private static String opponent = ""; // AI or Player?
    private static String specificPlayerName = ""; // If the Specific Player box is checked, this variable specifies his name.
    private static String mode = ""; // What AI difficulty?
    private static String aiDiff; // 0 = Reversi, 1 = BKE
    int maxNameCharacters = 3;
    private static boolean modeIsSet = false; // Prevents the GUI from adding the same pane twice.
    private static boolean specificPlayer = false; // Has a specific player been selected?
    private static boolean optionsExpanded = false; // Prevents the GUI from adding the same pane twice.
    private static boolean gameModeSelected = false; // Prevents the GUI from adding the same pane twice.
    private static boolean opponentSelected = false; // Prevents the GUI from adding the same pane twice.

    public static void setNight() {
        LauncherView.setNightmode(true);
    }

    public static void setLight() {
        LauncherView.setNightmode(false);
    }

    public static void optionsButton() {
        if (optionsExpanded == false) {
            LauncherModel.optionsClicked();
            optionsExpanded = true;
        }
    }

    public static void gameButton() {
        if (optionsExpanded == true) {
            LauncherModel.gameClicked();
            optionsExpanded = false;
        }
    }

    public static void reversiButton() {
        if (gameModeSelected == false) {
            LauncherModel.reversiEnable();
            gameModeSelected = true;
            game = "Reversi";
        }
    }

    public static void bkeButton() {
        if (gameModeSelected == false) {
            LauncherModel.bkeEnable();
            gameModeSelected = true;
            game = "Tic-tac-toe";
        }
    }

    public static void aiSelection(String givenMode) {
        modeIsSet = true;
        if (givenMode == "Smart Ai") {
            mode = "ai-hard";
        } else if (givenMode == "Randomized") {
            mode = "ai-easy";
        }

    }


    public static void resetButtonPressed() {
        LauncherModel.resetSettings();
        LauncherView.setError("");
        gameModeSelected = false;
        optionsExpanded = false;
        opponentSelected = false;
    }

    public static void startGamePressed() {
        if (opponent == "ai" && mode == "") {
            ErrorWindow error = new ErrorWindow("Warning",
                    "You want to add an AI, but no mode has been selected!",
                    "Please click on the dropdown box and select a mode.");

        }
        // If player name contains a space....
        else if (LauncherView.getNameField().contains(" ")) {
            ErrorWindow error = new ErrorWindow("Warning",
                    "Spaces are not allowed in names.",
                    "Please remove the spaces and try again.");
        } else {
            LauncherView.setError("Starting the game!");
            LauncherView.disableAllButtons();
            Main.startGame(game, mode);
            if (mode != "manual") {
                playerName = "[AI] " + getPlayerName();
                Main.login(playerName);

            } else {
                Main.login(LauncherView.getNameField());
            }
            System.out.println("== GAME SETTINGS == " +
                    "\nUsername:                    " + LauncherView.getNameField() +
                    "\nGame:                        " + game +
                    "\nOpponent:                    " + opponent);
            if (opponent == "ai") {
                System.out.println(
                        "AI Mode:                    " + mode);
            }
            System.out.println(
                      "\n\n=== OPTIONS == " +
                            "\nAI Reaction Time:            " + LauncherView.getReactionTime() + " Seconds" +
                            "\nSelected IP:                 " + getIP()    + ":" + getPort() +
                            "\nNightmode:                   " + LauncherView.nightModeChecked() +
                            "\nSound Effects:               " + LauncherView.soundChecked());

        }
    }

    public static void vsPlayerButton() {
        if (opponentSelected == false) {
            opponent = "player";
            LauncherModel.playerButtonEnable();
            mode = "manual";
        }
        opponentSelected = true;
    }

    public static void vsAiButton() {
        if (opponentSelected == false) {
            opponent = "ai";
            LauncherModel.aiButtonEnable();
        }
        opponentSelected = true;
    }


    // GETTERS
    public static String getGame() { return game; }
    public static String getOpponent() {return opponent;}
    public static String getPlayerName() { return LauncherView.getNameField();}
    public static String getMode() {return mode;}
    public static int getResponseTime() {return LauncherView.getReactionTime();}
    public static String getIP() {
        if (LauncherView.getLocalHost() == true) {
            return "localhost";
        } else {
            return LauncherView.getIP();
        }
    }
    private static boolean succes;
    public static void connectionTest() {
        try {
            LauncherView.setConnectionMessage("Attempting to connect...");
            Socket clientSocket = new Socket(LauncherController.getIP(), LauncherController.getPort());
            succes = true;
        } catch (IOException e) {
            e.printStackTrace();
            LauncherController.connectionError(true);
            succes = false;
        }
        if (succes == true) {
            LauncherController.connectionError(false);
        }
    }

    public static void connectionError(boolean error) {
        if (error == true) {
            System.out.println("Printing error message");
            LauncherView.setConnectionMessage("Connection failed.");
        } else {
            System.out.println("Printing succesfull message");
            LauncherView.setConnectionMessage("Connection passed!");
        }
    }

    public static int getPort() {
        if (LauncherView.getLocalHost() == true) {
            return 7789;
        } else {
            return Integer.parseInt(LauncherView.getPort());
        }
    }
    public static boolean getNightModeValue() {
        if (LauncherView.nightModeChecked() == true) {
            return true;
        } else {
            return false;
        }
    }

//    public static boolean checkConnection() {
//
//    }

    public static String getSpecificPlayerName() {  return specificPlayerName;}

    // SETTERS
    public static void setSpecificName(String name) {specificPlayerName = name; }
}
