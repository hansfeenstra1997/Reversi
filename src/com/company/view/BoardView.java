package com.company.view;

import com.company.connection.Connection;
import com.company.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Comparator;

public class BoardView extends View {
    private Stage stage;

    public BoardView(Stage gameStage) {
        this.stage = gameStage;
        BorderPane gameBorderPane = new BorderPane();
        VBox gameVBox = new VBox();

        String fontColor ;
        String backgroundColor;
        String sideBarColor;

        if (Controller.getNightModeBackground() == true) {
            fontColor = "labelText-White";
            backgroundColor = "paneStyle-Dark";
            sideBarColor = "sideBar-Dark";
        }  else {
            fontColor = "labelText-Dark";
            backgroundColor = "paneStyle-Light";
            sideBarColor = "sideBar-Light";
        }

        gameBorderPane.setMinSize(500, 500);
        gameBorderPane.getStylesheets().add(getClass().getResource("mainWindow.css").toExternalForm());
        gameBorderPane.getStyleClass().add(backgroundColor);

        VBox left = new VBox(50);
        HBox top = new HBox(500);
        VBox topInfo = new VBox();
        VBox right = new VBox();
        HBox playerInfo = new HBox();
        HBox playColor = new HBox();
        HBox forfeitBox = new HBox();
        BorderPane statusBox = new BorderPane();

        Button forfeitBtn = new Button("Forfeit");
        forfeitBtn.setOnAction((e) -> Connection.getInstance().sendCommand("forfeit"));
        forfeitBox.getChildren().add(forfeitBtn);
        forfeitBox.setPadding(new Insets(0, 0, 0, 40));

        Label player = new Label("Player: ");
        player.getStyleClass().add(fontColor);
        Label playerName = new Label();
        playerName.getStyleClass().add(fontColor);

        Label opponent = new Label("Opponent: ");
        opponent.getStyleClass().add(fontColor);
        Label opponentName = new Label();
        opponentName.getStyleClass().add(fontColor);

        playerInfo.getChildren().addAll(player, playerName, opponent, opponentName);

        Label white = new Label("White: ");
        white.getStyleClass().add(fontColor);
        Label whitePlayer = new Label();
        whitePlayer.getStyleClass().add(fontColor);

        Label black = new Label("Black: ");
        black.getStyleClass().add(fontColor);
        Label blackPlayer = new Label();
        blackPlayer.getStyleClass().add(fontColor);

        Label timerText = new Label();
        timerText.setFont(Font.font(40));

        playColor.getChildren().addAll(black, blackPlayer, white, whitePlayer);

        topInfo.getChildren().addAll(playerInfo, playColor);
        top.getChildren().addAll(topInfo);
        top.getStyleClass().add(sideBarColor);
        top.setPrefSize(400, 50);
        left.getStyleClass().add(sideBarColor);
        left.getChildren().addAll(forfeitBox);
        left.setPrefSize(100, 500);
        right.getChildren().addAll(timerText);

        gameBorderPane.setRight(right);
        gameBorderPane.setTop(top);
        gameBorderPane.setLeft(left);
        gameBorderPane.setCenter(gameVBox);

        Text turn = new Text("");
        turn.setId("statusText");
        statusBox.setLeft(turn);

        Label lastMove = new Label();
        statusBox.setCenter(lastMove);

        gameBorderPane.setBottom(statusBox);

        Scene scene = new Scene(gameBorderPane);
        gameStage.setScene(scene);

    }
}
