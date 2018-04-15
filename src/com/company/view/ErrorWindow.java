package com.company.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorWindow {

    public ErrorWindow(String title, String message, String solution) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(solution);
        Optional<ButtonType> result = alert.showAndWait();
    }
}
