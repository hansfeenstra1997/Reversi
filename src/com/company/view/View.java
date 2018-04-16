package com.company.view;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class View {

    Stage stage;
    BorderPane borderPane;

    HBox top;
    VBox center;
    VBox right;
    BorderPane bottom;

    public Stage getStage(){
        return stage;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public HBox getTop() {
        return (HBox) borderPane.getTop();
    }

    public VBox getCenter(){
        return (VBox) borderPane.getCenter();
    }

    public VBox getRight(){
        return (VBox) borderPane.getRight();
    }

    public BorderPane getBottom() {
        return (BorderPane) borderPane.getBottom();
    }


}
