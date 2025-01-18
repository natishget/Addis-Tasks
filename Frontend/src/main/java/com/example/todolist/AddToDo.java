package com.example.todolist;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class AddToDo extends Application {
    String username = null;
    int cat;
    public AddToDo(){

    }
    public AddToDo( String username){
        this.username = username;
    }
    @Override
    public void start(Stage stage) {
        if(username == null){
            HelloApplication hello = new HelloApplication();
                try {
                    hello.start(new Stage());
                } catch (IOException e) {
                    System.out.println("error occured in AddToDo class");
                }
            stage.close();
        }
        Text t = new Text("Add new To-do list");
        t.getStyleClass().add("text1");
        t.setStyle("-fx-font-size: 40px;");

        Label lb = new Label("To-do Name");
        lb.getStyleClass().add("text1");
        lb.setStyle("-fx-font-size: 20px;");
        TextField tf = new TextField();
        tf.getStyleClass().add("text");

        Label lb1 = new Label("Detail");
        lb.getStyleClass().add("text1");
        lb.setStyle("-fx-font-size: 20px;");
        TextArea tf1 = new TextArea();
        tf1.getStyleClass().add("text");

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButton = new RadioButton("Work");
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setOnAction(event -> {
            if (radioButton.isSelected()) {
                cat = 1;
            }
        });

        RadioButton radioButton1 = new RadioButton("Person");
        radioButton1.setToggleGroup(toggleGroup);
        radioButton1.setOnAction(event -> {
            if (radioButton1.isSelected()) {
                cat = 2;
            }
        });


        HBox hb = new HBox();
        hb.getChildren().addAll(radioButton,radioButton1);

        Button bt = new Button("Add");
        bt.getStyleClass().add("bt2");
        ToastNotification toastNotification = new ToastNotification();
        bt.setOnAction(event ->{
            menu men = new menu();
            System.out.println(cat);
            if (tf.getText() != "" && tf1.getText() != "" && cat <= 3) {
                int x = men.AddList(username, tf.getText(), tf1.getText(), cat);
                if (x < 1) {
                    ToastNotification.showNotification(stage, "Something Wrong Please Try Again");
                } else {
                    Home home = new Home(username);
                    home.start(new Stage());
                    stage.close();
                }
            }
            else
                ToastNotification.showNotification(stage, "Something Wrong Please Try Again");
        });
        Button bt2 = new Button("Back to Home");
        bt2.getStyleClass().add("bt2");
        Home home = new Home(username);
        bt2.setOnAction(event ->{
            home.start(new Stage());
            stage.close();
        });



        VBox vb1 = new VBox();
        vb1.getChildren().addAll(lb, tf, lb1, tf1, hb, bt, bt2);
        vb1.getStyleClass().add("vertical12");

        VBox vb = new VBox();
        vb.getChildren().addAll(t, vb1);
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
