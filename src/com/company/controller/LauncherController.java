package com.company.controller;

import com.company.ErrorWindow;
import com.company.Main;
import com.company.model.LauncherModel;
import com.company.view.LauncherView;

public class LauncherController {

    //SETTINGS:
    private static String game = ""; // BKE or Reversi?
    private static String opponent = ""; // AI or Player?
    private static String specificPlayerName = ""; // If the Specific Player box is checked, this variable specifies his name.
    private static String mode; // What AI difficulty?
    private static String aiDiff; // 0 = Reversi, 1 = BKE
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
            System.out.println("Options button clicked");
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

    public static void aiSelection(String mode) {
        System.out.println(mode);
        modeIsSet = true;
        mode = LauncherController.mode;
        if (mode == "Smart Ai") {
            LauncherController.mode = "ai-hard";
        } else if (mode == "Random Ai") {
            LauncherController.mode = "ai-easy";
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
                    "You want to play against AI, but no AI mode has been selected",
                    "Please click on the dropdown box and select a mode.");

        }
        // If player name contains a space....
        else if (LauncherView.getNameField().contains(" ")) {
            ErrorWindow error = new ErrorWindow("Warning",
                    "Spaces are not allowed in names.",
                    "Please remove the spaces and try again.");
        } else {
            Main.startGame(game, mode);
            Main.login(LauncherView.getNameField());
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
            System.out.println("Opponent set to " + opponent);
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
    public static String getSpecificPlayerName() {  return specificPlayerName;}

    // SETTERS
    public static void setSpecificName(String name) {specificPlayerName = name; System.out.println(getSpecificPlayerName()); }
}
