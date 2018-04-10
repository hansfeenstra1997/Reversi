package com.company.view;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static javafx.animation.Animation.INDEFINITE;

public class LoadIcon extends ImageView {
    RotateTransition rotate;

    public LoadIcon() {
        rotate = new RotateTransition(Duration.millis(3000), this);
        rotate.setByAngle(360);
        rotate.setCycleCount(INDEFINITE);
        //rotate.setDuration(Duration.INDEFINITE);
        rotate.play();
    }
}
