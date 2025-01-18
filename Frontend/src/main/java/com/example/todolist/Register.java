package com.example.todolist;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Register extends Application {
    boolean registedin;
        @Override
        public void start(Stage stage){
            HelloApplication ha = new HelloApplication();

        Text text = new Text("Welcome");
        text.getStyleClass().add("text1");
        text.setStyle("-fx-font-size: 40px;");

        Text t3 = new Text("Register");
        t3.getStyleClass().add("text1");
        t3.setStyle("-fx-font-size: 15px;");

        Text t1 = new Text("Username");
        t1.getStyleClass().add("text1");
        t1.setStyle("-fx-font-size: 17px;");
        TextField tf = new TextField();
        tf.getStyleClass().add("text");

        Text t2 = new Text("Password");
        t2.getStyleClass().add("text1");
        t2.setStyle("-fx-font-size: 17px;");
        TextField tf1 = new PasswordField();
        tf1.getStyleClass().add("text");

        Text t4 = new Text("First Name");
        t4.getStyleClass().add("text1");
        t4.setStyle("-fx-font-size: 17px;");
        TextField tf2 = new TextField();
        tf2.getStyleClass().add("text");

        Text t5 = new Text("Last Name");
        t5.getStyleClass().add("text1");
        t5.setStyle("-fx-font-size: 17px;");
        TextField tf3 = new TextField();
        tf3.getStyleClass().add("text");
        String username = tf.getText();
        String password = tf1.getText();
        String first = tf2.getText();
        String last = tf3.getText();

        Button bt = new Button("Register");
        bt.getStyleClass().add("bt");
        bt.setOnAction(event->{
            LoginHandler lh = new LoginHandler();
            ToastNotification toastNotification = new ToastNotification();
            if (tf.getText() != "" && tf1.getText() != "" && tf2.getText() != "" && tf3.getText() != "") {
                registedin = lh.register(tf2.getText(), tf3.getText(), tf.getText(), tf1.getText());
                if (registedin) {
                    try {
                        ha.start(new Stage());
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle the IOException appropriately
                    }
                }
                else
                    ToastNotification.showNotification(stage, "Something Wrong please register again");
            }
            else {
                ToastNotification.showNotification(stage, "Please Fill All the information");
            }

        });
        Button bt1 = new Button("Already have account?");
        bt1.getStyleClass().add("bt1");
        bt1.setOnAction(event ->{
            try {
                ha.start(new Stage());
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the IOException appropriately
            }

        });

        VBox vb2 = new VBox();
        vb2.getChildren().addAll(t4,tf2, t5, tf3, t1,tf, t2,tf1);

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(vb2, bt, bt1);
        vb1.getStyleClass().add("vertical12");

        VBox vb = new VBox();
        vb.getChildren().addAll(text,t3, vb1);
        vb.getStyleClass().add("vertical");
        Scene scene = new Scene(vb);
        scene.getStylesheets().add("style.css");

        stage.initStyle(StageStyle.DECORATED);

        // Get the screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Set the stage dimensions to match the screen size
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.setTitle("To-Do List");
        stage.setScene(scene);
        stage.show();
    }
}
