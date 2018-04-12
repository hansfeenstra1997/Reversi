package com.company.model;

import com.company.view.LauncherView;

public class LauncherModel {

    // GAME PANE
    public static void reversiEnable() {
        LauncherView.ticTacToeButton.setDisable(true);
        expandToMode();
    }

    public static void bkeEnable() {
        LauncherView.reversiButton.setDisable(true);
        expandToMode();
    }
    //
    public static void expandToName(int mode) {
        LauncherView.addModeTweak(mode);
        LauncherView.addStartButton();
        LauncherView.addName();
    }

    public static void expandToMode() {
        LauncherView.addMode();
    }

    public static void optionsClicked() {
        LauncherView.addMenu();
    }

    public static void gameClicked() {
            LauncherView.removeMenu();
    }



    // Mode Pane
    public static void aiButtonEnable() {
        expandToName(1);
    }

    public static void playerButtonEnable() {
        expandToName(0);
    }

    public static void resetSettings() {
        LauncherView.removePanes();
        LauncherView.ticTacToeButton.setDisable(false);
        LauncherView.reversiButton.setDisable(false);
        LauncherView.clearModeTweak();
    }
}
