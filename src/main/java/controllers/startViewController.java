package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class startViewController implements Initializable {

    @FXML
    private Button launch_button;

    @FXML
    private Button close_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void launchStartView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        Stage stage = (Stage) launch_button.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(true);
    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }


}