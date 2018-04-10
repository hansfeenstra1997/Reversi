package Controller;

import com.company.Main;

public class LauncherController {

    //SETTINGS:
    private static String game = ""; // BKE or Reversi?
    private static String opponent = ""; // AI or Player?
    private static String aiMode = ""; // What AI difficulty?
    private static int gameID = 999; // 0 = Reversi, 1 = BKE

    private static boolean modeIsSet = false; // Prevents the GUI from adding the same pane twice.
    private static boolean specificPlayer = false; // Has a specific player been selected?
    private static boolean optionsExpanded = false; // Prevents the GUI from adding the same pane twice.
    private static boolean gameModeSelected = false; // Prevents the GUI from adding the same pane twice.
    private static boolean opponentSelected = false; // Prevents the GUI from adding the same pane twice.

    public static void optionsButton() {
        if (optionsExpanded == false) {
            Model.LauncherModel.optionsClicked();
            optionsExpanded = true;
            System.out.println("Options button clicked");
        }
    }

    public static void gameButton() {
        if (optionsExpanded == true) {
            Model.LauncherModel.gameClicked();
            optionsExpanded = false;
        }
    }

    public static void reversiButton() {
        if (gameModeSelected == false) {
            Model.LauncherModel.reversiEnable();
            gameModeSelected = true;
            game = "Reversi";
            gameID = 0;
        }
    }

    public static void bkeButton() {
        if (gameModeSelected == false) {
            Model.LauncherModel.bkeEnable();
            gameModeSelected = true;
            game = "Tic-tac-toe";
            gameID = 1;
        }
    }

    public static void aiSelection(String mode) {
        modeIsSet = true;
        aiMode = mode;

    }


    public static void resetButtonPressed() {
        Model.LauncherModel.resetSettings();
        View.LauncherView.setError("");
        gameModeSelected = false;
        optionsExpanded = false;
        opponentSelected = false;
    }

    public static void startGamePressed() {
        if (opponent == "ai" && aiMode == "") {
            View.LauncherView.setError("No AI Mode has been selected!");
        }
        else if (View.LauncherView.getNameField().contains(" ")) {
            View.LauncherView.setError(("Spaces are not allowed in a username!"));
        } else {
            Main.startGame(game, gameID);
            Main.login(View.LauncherView.getNameField());
            View.LauncherView.setError("Starting a game...");
            View.LauncherView.disableAll();
            System.out.println("== GAME SETTINGS == " +
                    "\nUsername:                    " + View.LauncherView.getNameField() +
                    "\nGame:                        " + game +
                    "\nOpponent:                    " + opponent);
            if (opponent == "ai") {
                System.out.println(
                        "AI Mode:                    " + aiMode);
            }
            System.out.println(
                    "Specific player selected:    " + View.LauncherView.specificPlayer() +
                      "\n\n=== OPTIONS == " +
                            "\nAI Reaction Time:            " + View.LauncherView.getReactionTime() + " Seconds" +
                            "\nNightmode:                   " + View.LauncherView.nightModeChecked() +
                            "\nSound Effects:               " + View.LauncherView.soundChecked());

        }
    }

    public static void vsPlayerButton() {
        if (opponentSelected == false) {
            opponent = "player";
            Model.LauncherModel.playerButtonEnable();
        }
        opponentSelected = true;
    }

    public static void vsAiButton() {
        if (opponentSelected == false) {
            opponent = "ai";
            Model.LauncherModel.aiButtonEnable();
        }
        opponentSelected = true;
    }


    // GETTERS
    public static String getGame() {
        return game;
    }
    public static String getOpponent() {return opponent;}
    public static String getPlayerName() { return View.LauncherView.getNameField();}
    public static String getAiMode() {return aiMode;}
    public static int getResponseTime() {return View.LauncherView.getReactionTime();}
}
