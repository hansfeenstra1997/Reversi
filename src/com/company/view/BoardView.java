package com.company.view;

import com.company.connection.Connection;
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

        gameBorderPane.setMinSize(500, 500);

        HBox top = new HBox();
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
        Label playerName = new Label();

        Label opponent = new Label("Opponent: ");
        Label opponentName = new Label();

        playerInfo.getChildren().addAll(player, playerName, opponent, opponentName);

        Label white = new Label("White: ");
        Label whitePlayer = new Label();

        Label black = new Label("Black: ");
        Label blackPlayer = new Label();

        Label timerText = new Label();
        timerText.setFont(Font.font(40));

        playColor.getChildren().addAll(black, blackPlayer, white, whitePlayer);

        topInfo.getChildren().addAll(playerInfo, playColor);
        top.getChildren().addAll(topInfo, forfeitBox);
        right.getChildren().add(timerText);

        gameBorderPane.setRight(right);
        gameBorderPane.setTop(top);
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
