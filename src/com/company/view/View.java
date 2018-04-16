package com.company.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class View {

    Stage stage;
    BorderPane borderPane;

    VBox center;

    public Stage getStage(){
        return stage;
    }

    public HBox getTop() {
        return (HBox) borderPane.getTop();
    }

    public VBox getCenter(){
        return (VBox) borderPane.getCenter();
    }

}
