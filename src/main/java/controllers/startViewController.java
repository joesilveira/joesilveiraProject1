package controllers;

import dbHandler.DBFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class startViewController implements Initializable {

    @FXML
    private Button launch_button;

    @FXML
    private Button close_button;

    private DBFunctions dbFunc = new DBFunctions();

    public boolean isJobsExist() {
        return jobsExist;
    }

    boolean jobsExist = true;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void launchStartView(ActionEvent event) throws IOException, SQLException {
        if (dbFunc.checkEmptyTable("Jobs") || dbFunc.checkEmptyTable("StackOverFlowJobs")) {
            jobsExist = false;
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("No Jobs Exist in the Database. Please Run Tasks");
            a.show();
        }
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