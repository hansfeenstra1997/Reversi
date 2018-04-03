package Controller;
import Model.LauncherModel;

public class LauncherController {

    static boolean optionsExpanded = false;
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

    static boolean gameModeSelected = false; // Prevents the GUI from adding the same pane twice.
    public static void reversiButton() {
        if (gameModeSelected == false) {
            Model.LauncherModel.reversiEnable();
            gameModeSelected = true;
        }
    }

    public static void bkeButton() {
        if (gameModeSelected == false) {
            Model.LauncherModel.bkeEnable();
            gameModeSelected = true;
        }
    }

    public static void resetButtonPressed() {
        Model.LauncherModel.resetSettings();
        gameModeSelected = false;
        optionsExpanded = false;
    }

    public static void startGamePressed() {

    }

    public static void vsPlayerButton() {
        Model.LauncherModel.playerButtonEnable();
    }

    public static void vsAiButton() {
        Model.LauncherModel.aiButtonEnable();
    }
}
