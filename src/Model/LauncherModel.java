package Model;

public class LauncherModel {

    public static void expandToName(int mode) {
        View.GUI.addName(mode);
    }

    public static void expandToMode() {
        View.GUI.addMode();
    }

    public static void reversiEnable() {
        System.out.println("Reversi is enabled");
        expandToMode();
    }

    public static void bkeEnable() {
        System.out.println("TicTacToe is enabled");
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
