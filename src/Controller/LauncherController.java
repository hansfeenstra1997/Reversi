package Controller;
import Model.LauncherModel;

public class LauncherController {

    public static void reversiButton() {
        Model.LauncherModel.reversiEnable();
    }

    public static void bkeButton() {
        Model.LauncherModel.bkeEnable();
    }

    public static void vsPlayerButton() {
        Model.LauncherModel.playerButtonEnable();
    }

    public static void vsAiButton() {
        Model.LauncherModel.aiButtonEnable();
    }
}
