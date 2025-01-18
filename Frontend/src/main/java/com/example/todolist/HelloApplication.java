package com.example.todolist;

import javafx.application.Application;
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

public class HelloApplication extends Application {
    boolean logedin;
    @Override
    public void start(Stage stage) throws IOException {
        VBox vb2 = new VBox();
        Text text = new Text("Welcome");
        text.getStyleClass().add("text1");
        text.setStyle("-fx-font-size: 40px;");

        Text t3 = new Text("Login");
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



        Button bt = new Button("Login");
        bt.getStyleClass().add("bt");
        bt.setOnAction(event->{
            LoginHandler lh = new LoginHandler();
            if (tf.getText() != null && tf1.getText() != null)
                 logedin = lh.login(tf.getText(), tf1.getText());
            System.out.println(logedin);
            if (logedin) {
                System.out.println("come to this to call home");
                Home home = new Home(tf.getText());
                home.start(new Stage());
                stage.close();
            }
            else {
                ToastNotification toastNotification = new ToastNotification();
                toastNotification.showNotification(stage, "Something Wrong Please Try Again");
            }
        });

        Button bt1 = new Button("Create Account");
        bt1.getStyleClass().add("bt1");
        Register register = new Register();
        bt1.setOnAction(event ->{
            register.start(new Stage());
            stage.close();
        });

        vb2.getChildren().addAll(t1,tf, t2,tf1);
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(vb2, bt, bt1);
        vb1.getStyleClass().add("vertical1");

        VBox vb = new VBox();
        vb.getChildren().addAll(text,t3, vb1);
        vb.getStyleClass().add("vertical");
        Scene scene = new Scene(vb);
        scene.getStylesheets().add("style.css");
        stage.setTitle("To-Do List");

        stage.initStyle(StageStyle.DECORATED);
        // Get the screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Set the stage dimensions to match the screen size
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();

    }

}
