package View;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.event.EventHandler;

public class LauncherView extends Application {
    static int launcherWidth = 450;
    static int launcherHeight = 350;


    static Pane rootPane = new Pane();

    Scene scene = new Scene(rootPane, launcherWidth, launcherHeight);

    Pane headerPane = new Pane();
        int headerPanePosY = 0;
        static int headerPaneHeight = 50;
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
        static Pane menuPane = new Pane();


    Label labelLauncherHeader = new Label();
    Label gameLabel = new Label("Select a game to play");
    Label modeLabel = new Label("Select an opponent");
    Label nameLabel = new Label("Specifiy a username");
    Label reactionTimeLabel = new Label("A.I Reaction Time (in seconds)");
    Label languageLabel = new Label("Change language");

    TextField nameInput = new TextField();
    static TextField reactionInput = new TextField();
    static CheckBox specificPlayer = new CheckBox();
    CheckBox nightMode = new CheckBox("Nightmode");
    CheckBox soundOption = new CheckBox("Enable sound");
    CheckBox placeholderOption = new CheckBox("Placeholder");
    public static Button reversiButton = new Button("Play Reversi");
    public static Button ticTacToeButton = new Button("Play BKE");
    Button start = new Button("Start a game");
    Button reset = new Button("Reset options");
    static Button vsAiButton = new Button("Versus AI");
    static Button vsPlayer = new Button("Versus Player");
    Button switchEnglish = new Button("English");
    Button switchDutch = new Button("Dutch");

    Image reversiIcon = new Image("img/reversiIcon.png");
    Image tictactoeIcon = new Image("img/tictactoe.png");
    Image playerPicture = new Image("img/playerIcon.png");
    Image aiPicture = new Image("img/aiIcon.png");
    Image settingsPicture = new Image("img/settings.png");
    Image settingsPicturePressed = new Image("img/settings-pressed.png");
    Image gamePicture = new Image("img/gameIcon.png");
    Image gamePicturePressed = new Image("img/gameIcon-pressed.png");

    static ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Normal",
                    "Hard"
            );
    static final ComboBox comboBox = new ComboBox(options);

    ////////////// - END INITIALISATION - //////////////

    public void createHeaderPane() {
        headerPane.setStyle("-fx-background-color:#274982");
        headerPane.setPrefSize(launcherWidth, headerPaneHeight);

        labelLauncherHeader.setText("Boardgame Launcher");
        labelLauncherHeader.setTextFill(Color.rgb(255, 255, 255));
        labelLauncherHeader.setLayoutX(launcherWidth / 2 - 80);
        labelLauncherHeader.setLayoutY(12);
        labelLauncherHeader.setAlignment(Pos.CENTER_LEFT);
        labelLauncherHeader.getStyleClass().add("LabelClass");

        ImageView settingIcon = new ImageView();
        settingIcon.setImage(settingsPicture);
        settingIcon.setFitWidth(50);
        settingIcon.setPreserveRatio(true);
        settingIcon.setLayoutX(launcherWidth - 50);

        ImageView gameIcon = new ImageView();
        gameIcon.setImage(gamePicturePressed);
        gameIcon.setFitWidth(50);
        gameIcon.setPreserveRatio(true);
        gameIcon.setLayoutX(launcherWidth - 100);

        settingIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Controller.LauncherController.optionsButton();
            settingIcon.setImage(settingsPicturePressed);
            gameIcon.setImage(gamePicture);
        });

        gameIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Controller.LauncherController.gameButton();
            settingIcon.setImage(settingsPicture);
            gameIcon.setImage(gamePicturePressed);
        });

        headerPane.getChildren().addAll(labelLauncherHeader, settingIcon, gameIcon);
    }

    public void createGamePane() {
        int reversiButtonX = 140;
        gamePane.setLayoutY(gamePaneYPos);
        modePane.setStyle("-fx-background-color: #707070");
        gamePane.setStyle("-fx-border-color: #FFFFFF");
        gamePane.setPrefSize(launcherWidth, 100);
        gamePane.getStyleClass().add("Panes");

        gameLabel.setLayoutY(5);
        gameLabel.layoutXProperty().bind(gamePane.widthProperty().subtract(gameLabel.widthProperty()).divide(2));
        gameLabel.setTextFill(Color.rgb(255, 255, 255));
        gameLabel.getStyleClass().add("LabelPanes");

        reversiButton.setLayoutY(30);
        reversiButton.setLayoutX(reversiButtonX);
        reversiButton.setOnAction((event) -> {
            Controller.LauncherController.reversiButton();
        });

        ticTacToeButton.setLayoutY(30);
        ticTacToeButton.setLayoutX(reversiButtonX + 100);
        ticTacToeButton.setOnAction((event) -> {
            Controller.LauncherController.bkeButton();
        });

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
        gamePane.getChildren().addAll(reversiButton, ticTacToeButton, gameLabel, revIcon, ticIcon);

    }

    public void createModePane() {
        int vsPlayerButtonX = 140;
        modePane.setStyle("-fx-background-color: #707070");
        modePane.setLayoutY(gamePaneYPos + 100);
        modePane.setPrefSize(launcherWidth, 100);
        modePane.getStyleClass().add("Panes");

        modeLabel.layoutXProperty().bind(modePane.widthProperty().subtract(modeLabel.widthProperty()).divide(2));
        modeLabel.setLayoutY(5);
        modeLabel.setTextFill(Color.rgb(255, 255, 255));
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
        specificPlayer.setTextFill(Color.rgb(255, 255, 255));
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
        namePane.setPrefSize(launcherWidth, namePaneHeight);
        namePane.getStyleClass().add("PanesLast");

        nameLabel.setLayoutY(15);
        nameLabel.setLayoutX(nameLabelX);
        nameLabel.setTextFill(Color.rgb(255, 255, 255));

        nameInput.setLayoutX(nameLabelX + 140);
        nameInput.setLayoutY(15);

        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameInput.getText().length() >= 5) {
                start.setDisable(false);
            }
        });

        namePane.getChildren().addAll(nameInput, nameLabel);
    }

    public void createStartPane() {
        startPane.setLayoutY(startPaneYPos);
        startPane.setPrefSize(launcherWidth, 50);

        start.layoutXProperty().bind(startPane.widthProperty().subtract(start.widthProperty()).divide(1.5));
        start.setLayoutY(10);
        start.setOnAction((event) -> {
            Controller.LauncherController.startGamePressed();
        });
        start.setDisable(true);

        reset.layoutXProperty().bind(startPane.widthProperty().subtract(start.widthProperty()).divide(3));
        reset.setLayoutY(10);
        reset.setOnAction((event) -> {
            Controller.LauncherController.resetButtonPressed();
        });

        startPane.getChildren().addAll(start, reset);
    }

    public void createMenuPane() {
        System.out.println("eXECUTING");
        menuPane.setPrefSize(launcherWidth, launcherHeight - headerPaneHeight);
        menuPane.setLayoutY(headerPaneHeight);
        menuPane.setStyle("-fx-background-color: #707070");

        // AI REACTION TIME
        reactionTimeLabel.setTextFill(Color.rgb(255, 255, 255));
        reactionTimeLabel.setLayoutY(10);
        reactionTimeLabel.setLayoutX(10);
        reactionInput.setLayoutY(30);
        reactionInput.setLayoutX(10);

        // LANGUAGE SETTING
        languageLabel.setLayoutY(90);
        languageLabel.setTextFill(Color.rgb(255, 255, 255));
        languageLabel.setLayoutX(10);
        switchEnglish.setLayoutY(110);
        switchEnglish.setLayoutX(10);
        switchDutch.setLayoutY(110);
        switchDutch.setLayoutX(70);

        // NIGHTMODE CHECKBOX
        nightMode.setLayoutY(170);
        nightMode.setLayoutX(10);
        nightMode.setTextFill(Color.rgb(255, 255, 255));

        // SOUND
        soundOption.setLayoutY(200);
        soundOption.setLayoutX(10);
        soundOption.setTextFill(Color.rgb(255, 255, 255));

        // PLACEHOLDER
        placeholderOption.setLayoutY(230);
        placeholderOption.setLayoutX(10);
        placeholderOption.setTextFill(Color.rgb(255, 255, 255));

        menuPane.getChildren().addAll(reactionTimeLabel, reactionInput, languageLabel, switchEnglish, switchDutch, nightMode, soundOption, placeholderOption);
    }

    public static void removeMenu() {rootPane.getChildren().remove(menuPane); }
    public static void removeMode() {rootPane.getChildren().remove(modePane); }
    public static void removeName() {rootPane.getChildren().remove(namePane); }
    public static void removeStart() {rootPane.getChildren().remove(startPane); }

    public static void addMenu() {
        rootPane.getChildren().add(menuPane);
    }
    public static void addMode() {
        rootPane.getChildren().add(modePane);
    }
    public static void addName() { rootPane.getChildren().add(namePane); }

    // Player = 0
    // AI = 1
    public static void addModeTweak(int mode) {
        if (mode == 0) {
            specificPlayer.setDisable(false);
            vsAiButton.setDisable(true);
        }
        if (mode == 1) {
            comboBox.setDisable(false);
            vsPlayer.setDisable(true);
        }
        addName();
    }

    public static void clearModeTweak() {
        specificPlayer.setDisable(true);
        vsAiButton.setDisable(false);
        comboBox.setDisable(true);
        vsPlayer.setDisable(false);
    }

    public static void addStartButton() {
        rootPane.getChildren().add(startPane);
    }

    public void start(Stage primaryStage) throws Exception {
        createHeaderPane();
        createModePane();
        createGamePane();
        createNamePane();
        createMenuPane();
        createStartPane();
        scene.getStylesheets().add("mainWindow.css");
        rootPane.getChildren().addAll(headerPane, gamePane);

        primaryStage.setTitle("Boardgame Launcher");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}


