package com.company.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BoardView extends View {
    private Stage stage;

    public BoardView(Stage gameStage) {
        this.stage = gameStage;
        BorderPane gameBorderPane = new BorderPane();
        VBox gameVBox = new VBox();

        gameBorderPane.setMinSize(500, 500);

        VBox top = new VBox();
        VBox right = new VBox();
        HBox playerInfo = new HBox();
        HBox playColor = new HBox();
        BorderPane statusBox = new BorderPane();

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

        top.getChildren().addAll(playerInfo, playColor);
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
