package com.company.view;

import com.company.connection.Connection;
import com.company.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChoiceScreen extends View {
    public ChoiceScreen(Stage gameStage, VBox players, String gameName) {
        String fontColor = "";
        String backgroundColor = "";
        if (Controller.getNightModeBackground() == true) {
            fontColor = "labelText-White";
            backgroundColor = "paneStyle-Dark";
        }  else {
            fontColor = "labelText-Dark";
            backgroundColor = "paneStyle-Light";
        }

        Stage choiceStage = new Stage();

        BorderPane gameBorderPane = new BorderPane();
        gameBorderPane.getStylesheets().add(getClass().getResource("mainWindow.css").toExternalForm());
        gameBorderPane.getStyleClass().add(backgroundColor);

        gameBorderPane.setMinSize(400, 400);

        VBox top = new VBox();
        HBox topBox = new HBox();

        Label topLabel = new Label("Choice menu:");
        topLabel.getStyleClass().add(fontColor);
        topBox.getChildren().add(top);

        //left Playernames, right subscribe button
        HBox content = new HBox();

        HBox playerList = new HBox();
        playerList.setMinWidth(200);
        Label label = new Label("Playernames:");
        label.getStyleClass().add(fontColor);

        Connection.getInstance().sendCommand("get playerlist");

        playerList.getChildren().addAll(label, players);

        HBox refresh = new HBox();
        refresh.setMinWidth(200);
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> Connection.getInstance().sendCommand("get playerlist"));

        HBox subscribe = new HBox();
        subscribe.setMinWidth(200);
        Button subscribeButton = new Button("Subscribe");
        subscribeButton.setOnAction((event)->{
            Connection.getInstance().sendCommand("subscribe " + gameName);
        });

        refresh.getChildren().add(refreshButton);
        subscribe.getChildren().add(subscribeButton);

        content.getChildren().addAll(playerList, refresh, subscribe);

        gameBorderPane.setTop(top);
        gameBorderPane.setCenter(content);

        Text turn = new Text();
        turn.setId("turnText");
        gameBorderPane.setBottom(turn);

        Scene scene = new Scene(gameBorderPane);
        choiceStage.setScene(scene);
        choiceStage.show();
    }
}
