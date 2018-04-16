package com.company.view;

import com.company.connection.Connection;
import com.company.controller.Controller;
import com.company.controller.LauncherController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;

public class BoardView extends View {
    //private Stage stage;
    private static int matchTimeVar;
    private static Label matchTimeDisplay = new Label();

    Text turnText;
    Label lastMove;

    Label timerText;

    public BoardView() {

        stage = new Stage();
        borderPane = new BorderPane();

        center = new VBox();

        borderPane.setMinSize(500, 500);


        String fontColor ;
        String backgroundColor;
        String sideBarColor;
        String headerLabelColor;

        if (Controller.getNightModeBackground() == true) {
            fontColor = "labelText-White";
            backgroundColor = "paneStyle-Dark";
            sideBarColor = "sideBar-Dark";
            headerLabelColor = "labelHeader-White";
        }  else {
            fontColor = "labelText-Dark";
            backgroundColor = "paneStyle-Light";
            sideBarColor = "sideBar-Light";
            headerLabelColor = "labelHeader-Dark";
        }

        borderPane.getStylesheets().add(getClass().getResource("mainWindow.css").toExternalForm());
        borderPane.getStyleClass().add(backgroundColor);

        StackPane left = new StackPane();
        HBox top = new HBox(500);
        VBox topInfo = new VBox();
        VBox right = new VBox();
        VBox botLeft = new VBox();
        VBox topLeft = new VBox();

        HBox playerInfo = new HBox();
        HBox playColor = new HBox();
        HBox forfeitBox = new HBox();
        BorderPane statusBox = new BorderPane();

        CheckBox nightModeBox = new CheckBox("Enable NightMode");
        CheckBox soundEffectBox = new CheckBox("Enable Sound");
        nightModeBox.getStyleClass().add(fontColor);
        soundEffectBox.getStyleClass().add(fontColor);

        Button forfeitBtn = new Button("Forfeit");
        Timer timer = new Timer();
        forfeitBtn.setOnAction((e) -> Connection.getInstance().sendCommand("forfeit"));
        forfeitBox.setPadding(new Insets(0, 0, 0, 40));

        Label player = new Label("Player: ");

        Label matchTimeLabel = new Label("Match Time:");
        matchTimeLabel.getStyleClass().add(headerLabelColor);

        Label timeLeft = new Label("Time Left:");
        timeLeft.getStyleClass().add(headerLabelColor);

        matchTimeDisplay.getStyleClass().add(fontColor);

        player.getStyleClass().add(fontColor);
        Label playerName = new Label();
        playerName.getStyleClass().add(fontColor);

        Label opponent = new Label("Opponent: ");
        opponent.getStyleClass().add(fontColor);
        Label opponentName = new Label();
        opponentName.getStyleClass().add(fontColor);

        playerInfo.setAlignment(Pos.TOP_CENTER);
        playerInfo.getChildren().addAll(player, playerName, opponent, opponentName);

        Label white = new Label("White: ");
        white.getStyleClass().add(fontColor);
        Label whitePlayer = new Label();
        whitePlayer.getStyleClass().add(fontColor);

        Label black = new Label("Black: ");
        black.getStyleClass().add(fontColor);
        Label blackPlayer = new Label();
        blackPlayer.getStyleClass().add(fontColor);

        timerText = new Label();
        timerText.setFont(Font.font(40));

        playColor.getChildren().addAll(black, blackPlayer, white, whitePlayer);

        topInfo.getChildren().addAll(playerInfo, playColor);
        top.getChildren().addAll(topInfo);
        top.getStyleClass().add(sideBarColor);

        ////////////// SIDE PANEL
        topLeft.getChildren().addAll(matchTimeLabel, matchTimeDisplay, timerText, timeLeft);
        topLeft.setAlignment(Pos.TOP_CENTER);
        matchTimeLabel.setAlignment(Pos.CENTER);
        timerText.setAlignment(Pos.CENTER);

        botLeft.getChildren().addAll(nightModeBox, soundEffectBox, forfeitBtn);
        botLeft.setAlignment(Pos.BOTTOM_LEFT);
        top.setPrefSize(400, 50);
        left.getStyleClass().add(sideBarColor);
        left.setAlignment(botLeft, Pos.BOTTOM_LEFT);
        left.setAlignment(topLeft, Pos.TOP_LEFT);

        left.setPrefSize(150, 500);
        left.getChildren().addAll(botLeft, topLeft);

        // TIMER UPDATES EVERY SECOND

        right.getChildren().addAll(timerText);

        borderPane.setRight(right);
        borderPane.setTop(top);
        borderPane.setCenter(center);
        borderPane.setLeft(left);

        turnText = new Text("");
        turnText.setId("statusText");
        statusBox.setLeft(turnText);

        lastMove = new Label();
        statusBox.setCenter(lastMove);

        borderPane.setBottom(statusBox);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        LauncherController.resetButtonPressed();
                    }
                });
            }
        });
    }

    public Label getLastMove(){
        return lastMove;
    }

    public Text getTurnText(){
        return turnText;
    }

    public Label getTimerText(){
        return timerText;
    }
}
