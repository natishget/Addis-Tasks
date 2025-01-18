package com.example.todolist;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ToastNotification {
    private static final Duration FADE_DURATION = Duration.millis(5000);
    private static final double OPACITY_START = 1.0;
    private static final double OPACITY_END = 0.0;

    public static void showNotification(Stage owner, String message) {
        Label label = new Label(message);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");

        StackPane root = new StackPane(label);
        root.setAlignment(Pos.BOTTOM_CENTER);

        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.setAutoHide(true);

        FadeTransition fadeTransition = new FadeTransition(FADE_DURATION, root);
        fadeTransition.setFromValue(OPACITY_START);
        fadeTransition.setToValue(OPACITY_END);
        fadeTransition.setOnFinished(event -> popup.hide());
        fadeTransition.play();
        popup.show(owner);
    }
}

