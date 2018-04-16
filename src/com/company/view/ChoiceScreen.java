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

    private VBox pl;


    public ChoiceScreen(String gameName) {
        String fontColor;
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

        stage = new Stage();

        borderPane = new BorderPane();

        borderPane.getStylesheets().add(getClass().getResource("mainWindow.css").toExternalForm());
        borderPane.getStyleClass().add(backgroundColor);
        borderPane.setMinSize(400, 400);

        pl = new VBox();

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

        playerList.getChildren().addAll(label, pl);

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

    public VBox getPlayerlist(){
        return pl;
    }
}
