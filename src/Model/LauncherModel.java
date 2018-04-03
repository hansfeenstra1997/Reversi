package Model;

import View.LauncherView;

public class LauncherModel {

    public static void expandToName(int mode) {
        LauncherView.addName(mode);
    }

    public static void expandToMode() {
        View.LauncherView.addMode();
    }

    public static void optionsClicked() {
        View.LauncherView.addMenu();
    }

    public static void gameClicked() {
            View.LauncherView.removeMenu();
    }

    public static void reversiEnable() {
        View.LauncherView.ticTacToeButton.setDisable(true);
        expandToMode();
    }

    public static void bkeEnable() {
        View.LauncherView.reversiButton.setDisable(true);
        expandToMode();
    }

    public static void playerButtonEnable() {
        System.out.println("Player mode enabled");
        expandToName(0);
    }

    public static void aiButtonEnable() {
        System.out.println("AI mode enabled");
        expandToName(1);
    }
}
