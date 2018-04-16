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
    public ChoiceScreen(Stage gameStage, VBox players, String gameName) {
        stage = new Stage();

        borderPane = new BorderPane();

        borderPane.setMinSize(400, 400);

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

        borderPane.setTop(top);
        borderPane.setCenter(content);

        Text turn = new Text();
        turn.setId("turnText");
        borderPane.setBottom(turn);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}
