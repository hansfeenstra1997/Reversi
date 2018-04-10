package com.company.view;

import com.company.connection.Connection;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChoiceScreen extends View {
    public ChoiceScreen(Stage gameStage, VBox players) {
        Stage choiceStage = new Stage();

        BorderPane gameBorderPane = new BorderPane();

        gameBorderPane.setMinSize(400, 400);

        VBox top = new VBox();
        HBox topBox = new HBox();

        Label topLabel = new Label("Choice menu:");
        topBox.getChildren().add(top);

        //left Playernames, right subscribe button
        HBox content = new HBox();

        HBox playerList = new HBox();
        playerList.setMinWidth(200);
        Label label = new Label("Playernames:");
        Connection.getInstance().sendCommand("get playerlist");

        playerList.getChildren().addAll(label, players);

        HBox subscribe = new HBox();
        subscribe.setMinWidth(200);
        Button subscribeButton = new Button("Subscribe");
        //action
        //makeScene(gameStage);

        subscribe.getChildren().add(subscribeButton);

        content.getChildren().addAll(playerList, subscribe);

        gameBorderPane.setTop(top);
        gameBorderPane.setCenter(content);

        Text turn = new Text("test");
        turn.setId("turnText");
        gameBorderPane.setBottom(turn);

        Scene scene = new Scene(gameBorderPane);
        choiceStage.setScene(scene);
        choiceStage.show();


    }
}
