package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerFinder extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane rootPane = new Pane();
        Scene scene = new Scene(rootPane, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
