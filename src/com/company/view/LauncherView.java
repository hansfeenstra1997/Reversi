package com.company.view;

import com.company.controller.LauncherController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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


    private Label labelLauncherHeader = new Label();
    private Label gameLabel = new Label("Select a game to play");
    private Label ipLabel = new Label ("Specify the IP you would like to connect to");
    private Label portLabel = new Label(":");
    private Label modeLabel = new Label("Manual play or AI Play?");
    private Label nameLabel = new Label("Specifiy a username");
    private Label reactionTimeLabel = new Label("A.I Reaction Time (in seconds)");
    private Label languageLabel = new Label("Change language");
    private static Label errorMessage = new Label("");

    private static TextField nameInput = new TextField();
    private static TextField reactionInput = new TextField("5");
    private static TextField ipInput = new TextField("145.33.225.170");
    private static TextField portInput = new TextField("6789");

    private static CheckBox nightMode = new CheckBox("Nightmode");
    private static CheckBox soundOption = new CheckBox("Enable sound");
    private static CheckBox placeholderOption = new CheckBox("Placeholder");
    public static Button reversiButton = new Button("Play Reversi");  // !NIET OP PUBLIC HOUDEN!
    public static Button ticTacToeButton = new Button("Play BKE");      // !NIET OP PUBLIC HOUDEN!
    private static Button start = new Button("Start a game");
    private Button reset = new Button("Reset options");
    static Button vsAiButton = new Button("AI Mode");
    static Button vsPlayer = new Button("Manual Mode");
    private Button switchEnglish = new Button("English");
    private Button switchDutch = new Button("Dutch");

    private Image reversiIcon = new Image("reversiIcon.png");
    private Image tictactoeIcon = new Image("tictactoe.png");
    private Image playerPicture = new Image("playerIcon.png");
    private Image aiPicture = new Image("aiIcon.png");
    private Image settingsPicture = new Image("settings.png");
    private Image settingsPicturePressed = new Image("settings-pressed.png");
    private Image gamePicture = new Image("gameIcon.png");
    private Image gamePicturePressed = new Image("gameIcon-pressed.png");

    public static String stylePane = "paneStyle-Light";
    public static String styleLabel = "labelPanes";

    static ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Randomized",
                    "Smart Ai"
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
            LauncherController.optionsButton();
            settingIcon.setImage(settingsPicturePressed);
            gameIcon.setImage(gamePicture);
        });

        gameIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            LauncherController.gameButton();
            settingIcon.setImage(settingsPicture);
            gameIcon.setImage(gamePicturePressed);
        });

        headerPane.getChildren().addAll(labelLauncherHeader, settingIcon, gameIcon);
    }

    public void createGamePane() {
        int reversiButtonX = 140;
        gamePane.setLayoutY(gamePaneYPos);
        gamePane.getStyleClass().add(stylePane);
        gamePane.setPrefSize(launcherWidth, 100);
        gamePane.getStyleClass().add("Panes");

        gameLabel.setLayoutY(5);
        gameLabel.layoutXProperty().bind(gamePane.widthProperty().subtract(gameLabel.widthProperty()).divide(2));
        gameLabel.setTextFill(Color.rgb(255, 255, 255));
        gameLabel.getStyleClass().add(styleLabel);

        reversiButton.setLayoutY(30);
        reversiButton.setLayoutX(reversiButtonX);
        reversiButton.setOnAction((event) -> {
            LauncherController.reversiButton();
            vsPlayer.setDisable(false);
            vsAiButton.setDisable(false);
        });

        ticTacToeButton.setLayoutY(30);
        ticTacToeButton.setLayoutX(reversiButtonX + 100);
        ticTacToeButton.setOnAction((event) -> {
            LauncherController.bkeButton();
            vsPlayer.setDisable(false);
            vsAiButton.setDisable(false);
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
        modePane.getStyleClass().add(stylePane);
        modePane.setLayoutY(gamePaneYPos + 100);
        modePane.setPrefSize(launcherWidth, 100);
        modePane.getStyleClass().add(stylePane);

        modeLabel.layoutXProperty().bind(modePane.widthProperty().subtract(modeLabel.widthProperty()).divide(2));
        modeLabel.setLayoutY(5);
        modeLabel.setTextFill(Color.rgb(255, 255, 255));
        modeLabel.getStyleClass().add(styleLabel);

        vsAiButton.setLayoutY(30);
        vsAiButton.setLayoutX(vsPlayerButtonX + 100);
        vsAiButton.setDisable(true);
        vsAiButton.setTooltip(
                new Tooltip("Add yourself to a game as an AI. Requires no manual play")
        );

        vsPlayer.setLayoutY(30);
        vsPlayer.setLayoutX(vsPlayerButtonX);
        vsPlayer.setDisable(true);
        vsPlayer.setTooltip(
                new Tooltip("Play as a human player. No AI involved.")
        );

        vsPlayer.setOnAction((event) -> {
            LauncherController.vsPlayerButton();
        });

        vsAiButton.setOnAction((event) -> {
            LauncherController.vsAiButton();
        });

        comboBox.setLayoutX(vsPlayerButtonX + 100);
        comboBox.setPromptText("AI Mode");
        comboBox.setTooltip(new Tooltip(
                "How do you want the AI to act?" +
                        "\n RANDOMIZED: AI will place randomly around the field." +
                        "\n SMART AI: AI is more intelligent using a more advanced algorithm"
        ));
        comboBox.setLayoutY(60);
        comboBox.setDisable(true);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                LauncherController.aiSelection(t1);
            }
        });

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

        modePane.getChildren().addAll(vsAiButton, vsPlayer, modeLabel, playerIcon, aiIcon, comboBox);

    }

    public void createNamePane() {
        int nameLabelX = 50;
        namePane.getStyleClass().add(stylePane);
        namePane.setLayoutY(modePaneYPos + 100);
        namePane.setPrefSize(launcherWidth, namePaneHeight);

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
        start.setOnAction((event) -> { LauncherController.startGamePressed(); });
        start.setDisable(true);

        reset.layoutXProperty().bind(startPane.widthProperty().subtract(start.widthProperty()).divide(3));
        reset.setLayoutY(10);
        reset.setOnAction((event) -> { LauncherController.resetButtonPressed(); });

        errorMessage.setLayoutY(40);
        errorMessage.layoutXProperty().bind(startPane.widthProperty().subtract(errorMessage.widthProperty()).divide(2));

        startPane.getChildren().addAll(start, reset, errorMessage);
    }

    public void createMenuPane() {
        menuPane.setPrefSize(launcherWidth, launcherHeight - headerPaneHeight);
        menuPane.setLayoutY(headerPaneHeight);
        menuPane.getStyleClass().add(stylePane);

        // AI REACTION TIME
        reactionTimeLabel.setTextFill(Color.rgb(255, 255, 255));
        reactionTimeLabel.setLayoutY(10);
        reactionTimeLabel.setLayoutX(10);
        reactionInput.setLayoutY(30);
        reactionInput.setLayoutX(10);

        // IP SETTINGS
        ipLabel.setTextFill(Color.rgb(255, 255, 255));
        ipLabel.setLayoutY(10);
        ipLabel.setLayoutX(launcherWidth - 250);

        ipInput.setLayoutY(30);
        ipInput.setLayoutX(launcherWidth - 225);
        ipInput.setPrefWidth(100);

        portLabel.setTextFill(Color.rgb(255, 255, 255));
        portLabel.setLayoutY(35);
        portLabel.setLayoutX(launcherWidth - 120);

        portInput.setLayoutY(30);
        portInput.setLayoutX(launcherWidth - 110);
        portInput.setPrefWidth(50);
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
        nightMode.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (nightMode.isSelected() == true) {
                    LauncherController.setNight();
                } else {
                    System.out.println("Executing");
                    LauncherController.setLight();
                }
            }
        });

        // SOUND
        soundOption.setLayoutY(200);
        soundOption.setLayoutX(10);
        soundOption.setSelected(true);
        soundOption.setTextFill(Color.rgb(255, 255, 255));

        // PLACEHOLDER
        placeholderOption.setLayoutY(230);
        placeholderOption.setLayoutX(10);
        placeholderOption.setTextFill(Color.rgb(255, 255, 255));

        menuPane.getChildren().addAll(reactionTimeLabel, reactionInput, languageLabel, switchEnglish,
                switchDutch, nightMode, soundOption, placeholderOption, ipLabel, ipInput, portInput,
                portLabel);
    }

    public static void removePanes() {
        rootPane.getChildren().removeAll(menuPane, modePane, namePane, startPane);
    }
    public static void removeMenu() {
        rootPane.getChildren().remove(menuPane);
    }

    public static void addMode() {}
    public static void addName() {}

    public static void addPanes() {
        rootPane.getChildren().addAll(modePane, namePane, startPane);
    }
    public static void addMenu() {
        rootPane.getChildren().add(menuPane); }

    //  Player = 0 // AI = 1
    public static void addModeTweak(int mode) {
        if (mode == 0) {
            vsAiButton.setDisable(true);
        }
        if (mode == 1) {
            comboBox.setDisable(false);
            vsPlayer.setDisable(true);
        }
    }

    public static void clearModeTweak() {
        vsAiButton.setDisable(true);
        comboBox.setDisable(true);
        vsPlayer.setDisable(true);
        ticTacToeButton.setDisable(false);
        reversiButton.setDisable(false);
        nameInput.setDisable(false);
        start.setDisable(false);
    }

    public static void disableAll() {
        vsAiButton.setDisable(true);
        comboBox.setDisable(true);
        vsPlayer.setDisable(true);
        ticTacToeButton.setDisable(true);
        reversiButton.setDisable(true);
        nameInput.setDisable(true);
        start.setDisable(true);
    }

    public static String getNameField() {
        return nameInput.getText();
    }
    public static boolean nightModeChecked() {
        return nightMode.isSelected();
    }
    public static boolean soundChecked() {
        return soundOption.isSelected();
    }

    public static int getReactionTime() {
        return Integer.parseInt(reactionInput.getText());
    }

    public static void setError(String error) {
        errorMessage.setText(error);
    }
    public static void setNightmode(boolean activated) {
        if (activated == true) {
            stylePane = "paneStyle-Dark";
        }
        if (activated == false) {
            stylePane = "paneStyle-notDark";
            gamePane.getStyleClass().clear();
            System.out.println(gamePane.getStyleClass());
        }

        gamePane.getStyleClass().clear();
        modePane.getStyleClass().clear();
        startPane.getStyleClass().clear();
        namePane.getStyleClass().clear();
        menuPane.getStyleClass().clear();

        gamePane.getStyleClass().add(stylePane);
        modePane.getStyleClass().add(stylePane);
        startPane.getStyleClass().add(stylePane);
        namePane.getStyleClass().add(stylePane);
        menuPane.getStyleClass().add(stylePane);
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
        scene.getStylesheets().add("com/company/mainWindow.css");
        rootPane.getChildren().addAll(headerPane, gamePane, modePane, namePane, startPane);

        primaryStage.setTitle("Boardgame Launcher");
        primaryStage.setScene(scene);
        primaryStage.setWidth(457);
        primaryStage.setHeight(381);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setResizable(false);

    }
}


