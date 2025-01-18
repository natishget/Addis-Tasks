package com.example.todolist;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Home extends Application {
    String username = null;
    public Home(){ }
    public Home(String username){
        this.username = username;
    }

    @Override
    public void start(Stage stage) {
        {
            if (username == null){
                HelloApplication hello = new HelloApplication();
                    try {
                        hello.start(new Stage());
                    } catch (IOException e) {
                        System.out.println("Error is occured in Home class");
                    }
                stage.close();
            }


            Button bt = new Button("+");
            bt.setOnAction(event ->{
                AddToDo add = new AddToDo(username);
                add.start(new Stage());
                stage.close();
            });

            Button bt1 = new Button("Logout");
            bt1.setOnAction(event ->{
                HelloApplication hello = new HelloApplication();
                try {
                    hello.start(new Stage());
                    stage.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
            });

            VBox vb = new VBox();

            menu men = new menu(username);
            String value = men.ListAsker();
            System.out.println(value);

            List<TodoTask> todoTasks = new ArrayList<>();

            String[] lines = value.split("\n");
            for (String line : lines) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String title = fields[0];
                    String description = fields[1];
                    String status = fields[2];
                    TodoTask task = new TodoTask(title, description, status);
                    todoTasks.add(task);
                }
            }


            TableView<TodoTask> tableView = new TableView<>();
            TableColumn<TodoTask, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            TableColumn<TodoTask, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            TableColumn<TodoTask, String> statusColumn = new TableColumn<>("DoneUndone");
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("DoneUndone"));
            TableColumn<TodoTask, Void> editColumn = new TableColumn<>("Edit");
            TableColumn<TodoTask, Void> deleteColumn = new TableColumn<>("Delete");


            // Custom cell factory for the DoneUndone column
            statusColumn.setCellFactory(column -> new TableCell<TodoTask, String>() {
                private final CheckBox checkBox = new CheckBox();

                {
                    setAlignment(Pos.CENTER);
                    itemProperty().addListener((obs, oldValue, newValue) -> {
                        if (newValue != null) {
                            checkBox.setSelected(newValue.equals("D"));
                        }
                    });
                    checkBox.setOnAction(event -> {
                        TodoTask task = getTableView().getItems().get(getIndex());
                        task.setDoneUndone(checkBox.isSelected() ? men.Status(username, task.getTitle(), task.getDescription(), "D") : men.Status(username, task.getTitle(), task.getDescription(), "U"));
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(checkBox);
                    }
                }
            });

            editColumn.setCellFactory(column -> new TableCell<TodoTask, Void>() {
                private final Button editButton = new Button("Edit");

                {
                    setAlignment(Pos.CENTER);
                    editButton.setOnAction(event -> {
                        TodoTask task = getTableView().getItems().get(getIndex());
                        EditToDo edit = new EditToDo(username, task.getTitle(), task.getDescription());
                        edit.start(new Stage());
                        stage.close();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            });

            deleteColumn.setCellFactory(column -> new TableCell<TodoTask, Void>() {
                private final Button deleteButton = new Button("Delete");
                {
                    setAlignment(Pos.CENTER);
                    deleteButton.setOnAction(event -> {
                        TodoTask task = getTableView().getItems().get(getIndex());
                        men.DeleteList(username, task.getTitle(), task.getDescription());
                        stage.close();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            });

            tableView.prefWidthProperty().bind(vb.widthProperty().multiply(0.8));

            tableView.getColumns().addAll(titleColumn, descriptionColumn, statusColumn, editColumn, deleteColumn);
            tableView.setItems(FXCollections.observableArrayList(todoTasks));

            DisplayWorks displayWork = new DisplayWorks(username);
            Button bt4 = new Button("Work");
            bt4.setOnAction(event ->{
                displayWork.start(new Stage());
                stage.close();
            });
            DisplayPersonal displayPersonal = new DisplayPersonal(username);
            Button bt5 = new Button("Personal");
            bt5.setOnAction(event ->{
                displayPersonal.start(new Stage());
                stage.close();
            });

            HBox hb = new HBox();
            hb.getChildren().addAll(bt, bt4, bt5, bt1);

            vb.getChildren().addAll(tableView, hb);
            vb.getStyleClass().add("vertical3");
            VBox vb1 = new VBox();
            vb1.getChildren().addAll(vb);
            vb1.getStyleClass().add("vertical");
            Scene scene = new Scene(vb1);
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

    public class TodoTask {
        private String title;
        private String description;
        private String DoneUndone;

        public TodoTask(String title, String description, String DoneUndone) {
            this.title = title;
            this.description = description;
            this.DoneUndone = DoneUndone;
        }

        // Getters and setters for the properties

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDoneUndone() {
            return DoneUndone;
        }

        public void setDoneUndone(String DoneUndone) {
            this.DoneUndone = DoneUndone;
        }
    }

}
