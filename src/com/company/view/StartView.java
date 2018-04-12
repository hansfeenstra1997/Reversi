package com.company.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartView extends View {
    private Button startBtn;
    private Button startReversiBtn;
    private Button commandButton;
    private Button loginBtn;
    private TextField usernameField;
    private TextField commandField;

    public StartView(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(200, 200);
        VBox main = new VBox();
        GridPane controlPane = new GridPane();

        startBtn = new Button("Start Tic-Tac-Toe");
        controlPane.add(startBtn, 0, 0);

        startReversiBtn = new Button("Start Reversi");
        controlPane.add(startReversiBtn, 0, 1);

        usernameField = new TextField();
        controlPane.add(usernameField, 0, 2);

        loginBtn = new Button("Login");
        controlPane.add(loginBtn, 1, 2);

        commandField = new TextField();
        controlPane.add(commandField,0, 3);
        commandButton = new Button("Send");
        controlPane.add(commandButton,1,3);

        main.getChildren().add(controlPane);

        mainPane.setCenter(main);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Games4You");
        primaryStage.show();
    }

    public Button getStartBtn() {
        return startBtn;
    }
    public Button getStartReversiBtn() {
        return startReversiBtn;
    }
    public Button getCommandButton() {
        return commandButton;
    }
    public Button getLoginBtn() {
        return loginBtn;
    }
    public String getUsernameFieldText() {
        return usernameField.getText();
    }
    public String getCommandFieldText() {
        return commandField.getText();
    }
    public void setCommandFieldText(String text) {
        commandField.setText(text);
    }
}
