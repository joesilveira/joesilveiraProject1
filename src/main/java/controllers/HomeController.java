package controllers;

import dbHandler.DBFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button viewTable_button;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Button search_button;

    public Button getViewTable_button() {
        return viewTable_button;
    }

    public void setViewTable_button(Button viewTable_button) {
        this.viewTable_button = viewTable_button;
    }

    public Button getSearch_button() {
        return search_button;
    }

    public void setSearch_button(Button search_button) {
        this.search_button = search_button;
    }

    public Button getTasks_button() {
        return tasks_button;
    }

    public void setTasks_button(Button tasks_button) {
        this.tasks_button = tasks_button;
    }

    public Button getHome_button() {
        return home_button;
    }

    public void setHome_button(Button home_button) {
        this.home_button = home_button;
    }

    @FXML
    private Button tasks_button;

    @FXML
    private Button home_button;

    @FXML
    private AnchorPane sceneViewPane;


    DBFunctions dbFunc = new DBFunctions();
    boolean buttonsDisabled = true;
    private startViewController svc = new startViewController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (dbFunc.checkEmptyTable("Jobs") || dbFunc.checkEmptyTable("StackOverFlowJobs")) {
                search_button.setDisable(true);
                viewTable_button.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homeButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        Stage stage = (Stage) home_button.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void tasksButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/tasksView.fxml"));
        AnchorPane tasksScene = fxmlLoader.load();
        scenePane.getChildren().setAll(tasksScene);
        Stage stage = (Stage) tasks_button.getScene().getWindow();
        stage.centerOnScreen();
    }

    @FXML
    void viewTablePressed(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/jobDisplay.fxml"));
        AnchorPane readView = fxmlLoader.load();
        scenePane.getChildren().setAll(readView);
        Stage stage = (Stage) viewTable_button.getScene().getWindow();
        stage.sizeToScene();
        stage.centerOnScreen();
    }


    @FXML
    void searchPressed(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/searchView2.fxml"));
        AnchorPane mapScene = fxmlLoader.load();
        scenePane.getChildren().setAll(mapScene);
        Stage stage = (Stage) search_button.getScene().getWindow();
        stage.sizeToScene();
        stage.centerOnScreen();
    }

}
