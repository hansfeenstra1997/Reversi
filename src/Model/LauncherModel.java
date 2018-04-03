package Model;

import View.LauncherView;

public class LauncherModel {

    // GAME PANE
    public static void reversiEnable() {
        View.LauncherView.ticTacToeButton.setDisable(true);
        expandToMode();
    }

    public static void bkeEnable() {
        View.LauncherView.reversiButton.setDisable(true);
        expandToMode();
    }
    //
    public static void expandToName(int mode) {
        LauncherView.addModeTweak(mode);
        LauncherView.addStartButton();
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



    // Mode Pane
    public static void aiButtonEnable() {
        expandToName(1);
    }

    public static void playerButtonEnable() {
        expandToName(0);
    }

    public static void resetSettings() {
        View.LauncherView.removePanes();
        View.LauncherView.ticTacToeButton.setDisable(false);
        View.LauncherView.reversiButton.setDisable(false);
        View.LauncherView.clearModeTweak();
    }
}
