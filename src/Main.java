import View.GUI;
import javafx.application.Application;
        import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) throws InterruptedException {
        Application.launch(GUI.class, args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Boardgame Launcher");
        System.out.println("Ik werk");
    }

}
