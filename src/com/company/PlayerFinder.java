package com.company;

import javafx.application.Application;
import javafx.application.Platform;
import Controller.LauncherController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

public class PlayerFinder{

    public PlayerFinder() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Specify a player name:");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 70, 10, 10));

        TextField from = new TextField();
        from.setPromptText("Specify player name");

        gridPane.add(from, 0, 0);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(() -> from.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (from.getText().length() >= 5) {
                LauncherController.setSpecificName(from.getText());
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
    }
}
