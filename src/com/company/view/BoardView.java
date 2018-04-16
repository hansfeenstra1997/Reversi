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

public class BoardView extends View {
    Text turnText;
    Label lastMove;

    Label timerText;

    public BoardView() {
        stage = new Stage();
        borderPane = new BorderPane();

        center = new VBox();

        borderPane.setMinSize(500, 500);

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

        playerInfo.getChildren().addAll(new Label("Player: "), new Label(), new Label("Opponent: "), new Label());

        timerText = new Label();
        timerText.setFont(Font.font(40));

        playColor.getChildren().addAll(new Label("Black: "), new Label(), new Label("White: "), new Label());

        topInfo.getChildren().addAll(playerInfo, playColor);
        top.getChildren().addAll(topInfo, forfeitBox);
        right.getChildren().add(timerText);

        borderPane.setRight(right);
        borderPane.setTop(top);
        borderPane.setCenter(center);

        turnText = new Text("");
        turnText.setId("statusText");
        statusBox.setLeft(turnText);

        lastMove = new Label();
        statusBox.setCenter(lastMove);

        borderPane.setBottom(statusBox);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
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
