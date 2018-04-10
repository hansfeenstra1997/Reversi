package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application{
    int launcherWidth = 450;
    int launcherHeight = 350;


    static Pane rootPane = new Pane();

    Scene scene = new Scene(rootPane, launcherWidth, launcherHeight);
    Pane headerPane = new Pane();
        int headerPanePosY = 0;
        int headerPaneHeight = 50;
    static Pane gamePane = new Pane();
        int gamePaneHeight = 100;
        int gamePaneYPos = headerPaneHeight;
    static Pane modePane = new Pane();
        int modePaneHeight = 100;
        int modePaneYPos = gamePaneYPos + modePaneHeight;
    static Pane namePane = new Pane();
        int namePaneHeight = 50;
        int namePaneYPos = modePaneYPos + namePaneHeight;
    static Pane startPane = new Pane();
        int startPaneHeight = 50;
        int startPaneYPos = namePaneYPos + startPaneHeight + 50;

    Label labelLauncherHeader = new Label();
    Label gameLabel = new Label("Select a game to play");
    Label modeLabel = new Label("Select an opponent");
    Label nameLabel = new Label("Specifiy a username");

    TextField nameInput = new TextField();

    static CheckBox specificPlayer = new CheckBox();

    Button reversiButton = new Button("Play Reversi");
    Button ticTacToeButton = new Button("Play BKE");
    Button start = new Button("Start a game");
    static Button vsAiButton = new Button("Versus AI");
    static Button vsPlayer = new Button("Versus Player");

    Image reversiIcon = new Image("img/reversiIcon.png");
    Image tictactoeIcon = new Image("img/tictactoe.png");
    Image playerPicture = new Image("img/playerIcon.png");
    Image aiPicture = new Image("img/aiIcon.png");

    static ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Normal",
                    "Hard"
            );
    static final ComboBox comboBox = new ComboBox(options);

    public void createHeaderPane() {
        headerPane.setStyle("-fx-background-color: #16a2ff");
        headerPane.setPrefSize(launcherWidth,headerPaneHeight);

        labelLauncherHeader.setText("Boardgame Launcher");
        labelLauncherHeader.setTextFill(Color.rgb(255,255,255));
        labelLauncherHeader.setLayoutX(launcherWidth / 2 - 80);
        labelLauncherHeader.setLayoutY(12);
        labelLauncherHeader.setAlignment(Pos.CENTER_LEFT);
        labelLauncherHeader.getStyleClass().add("LabelClass");
    }

    public void createGamePane() {
        int reversiButtonX = 140;
        gamePane.setLayoutY(gamePaneYPos);
        modePane.setStyle("-fx-background-color: #707070");
        gamePane.setStyle("-fx-border-color: #FFFFFF");
        gamePane.setPrefSize(launcherWidth,100);
        gamePane.getStyleClass().add("Panes");

        gameLabel.setLayoutY(5);
        gameLabel.layoutXProperty().bind(gamePane.widthProperty().subtract(gameLabel.widthProperty()).divide(2));
        gameLabel.setTextFill(Color.rgb(255,255,255));
        gameLabel.getStyleClass().add("LabelPanes");

        reversiButton.setLayoutY(30);
        reversiButton.setLayoutX(reversiButtonX);
        reversiButton.setOnAction((event) -> {
            Controller.LauncherController.reversiButton();
        });

        ticTacToeButton.setLayoutY(30);
        ticTacToeButton.setLayoutX(reversiButtonX + 100);

        ImageView revIcon = new ImageView();
        revIcon.setImage(reversiIcon);
        revIcon.setFitWidth(100);
        revIcon.setPreserveRatio(true);
        revIcon.setLayoutX(0);
        revIcon.setLayoutY(0);

        ImageView ticIcon = new ImageView();
        ticIcon.setImage(tictactoeIcon);
        ticIcon.setFitWidth(100);
        ticIcon.setPreserveRatio(true);
        ticIcon.setLayoutX(launcherWidth - 100);
        ticIcon.setLayoutY(0);
        gamePane.getChildren().addAll(reversiButton, ticTacToeButton, gameLabel, revIcon, ticIcon);

    }

    public void createModePane() {
        int vsPlayerButtonX = 140;
        modePane.setStyle("-fx-background-color: #707070");
        modePane.setLayoutY(gamePaneYPos + 100);
        modePane.setPrefSize(launcherWidth,100);
        modePane.getStyleClass().add("Panes");

        modeLabel.layoutXProperty().bind(modePane.widthProperty().subtract(modeLabel.widthProperty()).divide(2));
        modeLabel.setLayoutY(5);
        modeLabel.setTextFill(Color.rgb(255,255,255));
        modeLabel.getStyleClass().add("LabelPanes");

        vsAiButton.setLayoutY(30);
        vsAiButton.setLayoutX(vsPlayerButtonX + 100);

        vsPlayer.setLayoutY(30);
        vsPlayer.setLayoutX(vsPlayerButtonX);

        vsPlayer.setOnAction((event) -> {
            Controller.LauncherController.vsPlayerButton();
        });

        vsAiButton.setOnAction((event) -> {
            Controller.LauncherController.vsAiButton();
        });

        specificPlayer.setText("Search for \n specific player?");
        specificPlayer.setTextFill(Color.rgb(255,255,255));
        specificPlayer.setLayoutY(60);
        specificPlayer.setLayoutX(vsPlayerButtonX - 20);
        specificPlayer.setDisable(true);

        comboBox.setLayoutX(vsPlayerButtonX + 100);
        comboBox.setPromptText("Difficulty");
        comboBox.setLayoutY(60);
        comboBox.setDisable(true);

        ImageView aiIcon = new ImageView();
        aiIcon.setImage(aiPicture);
        aiIcon.setFitWidth(100);
        aiIcon.setPreserveRatio(true);
        aiIcon.setLayoutX(launcherWidth - 100);
        aiIcon.setLayoutY(10);

        ImageView playerIcon = new ImageView();
        playerIcon.setImage(playerPicture);
        playerIcon.setFitWidth(100);
        playerIcon.setPreserveRatio(true);
        playerIcon.setLayoutX(0);
        playerIcon.setLayoutY(10);

        modePane.getChildren().addAll(vsAiButton, vsPlayer, modeLabel, playerIcon, aiIcon, specificPlayer, comboBox);

    }

    public void createNamePane() {
        int nameLabelX = 50;
        namePane.setStyle("-fx-background-color: #707070");
        namePane.setLayoutY(modePaneYPos + 100);
        namePane.setPrefSize(launcherWidth,namePaneHeight);
        namePane.getStyleClass().add("PanesLast");

        nameLabel.setLayoutY(15);
        nameLabel.setLayoutX(nameLabelX);
        nameLabel.setTextFill(Color.rgb(255,255,255));

        nameInput.setLayoutX(nameLabelX + 140);
        nameInput.setLayoutY(15);
        namePane.getChildren().addAll(nameInput, nameLabel);
    }

    public void createStartPane() {
        startPane.setLayoutY(startPaneYPos);
        startPane.setPrefSize(launcherWidth,50);
        start.layoutXProperty().bind(startPane.widthProperty().subtract(start.widthProperty()).divide(2));
        start.setLayoutY(10);
        startPane.getChildren().add(start);
    }

    public static void addMode() {
        rootPane.getChildren().add(modePane);
    }

    // AI = 1
    // Player = 0
    public static void addName(int mode) {
        if (mode == 1) {
            comboBox.setDisable(false);
            vsPlayer.setDisable(true);
        } else {
            specificPlayer.setDisable(false);
            vsAiButton.setDisable(true);
        }
        rootPane.getChildren().addAll(namePane, startPane);
        addStartButton();
    }

    public static void addStartButton() {

    }

    public void start(Stage primaryStage) throws Exception {
        createHeaderPane();
        createModePane();
        createGamePane();
        createNamePane();
        createStartPane();

        scene.getStylesheets().add("com/company/mainWindow.css");

        headerPane.getChildren().addAll(labelLauncherHeader);
        rootPane.getChildren().addAll(headerPane, gamePane);


        primaryStage.setTitle("Boardgame Launcher");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}


