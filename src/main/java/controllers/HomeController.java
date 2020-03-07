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
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button viewTable_button;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Button search_button;

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
        if (svc.jobsExist) {
            search_button.setDisable(true);
            viewTable_button.setDisable(true);
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
        fxmlLoader.setLocation(getClass().getResource("/searchView.fxml"));
        AnchorPane mapScene = fxmlLoader.load();
        scenePane.getChildren().setAll(mapScene);
        Stage stage = (Stage) search_button.getScene().getWindow();
        stage.sizeToScene();
        stage.centerOnScreen();
    }

}
