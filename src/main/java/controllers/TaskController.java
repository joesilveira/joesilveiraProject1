package controllers;

import dbHandler.DBFunctions;
import dbHandler.Jobfunctions;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import runner.runtimeHandler;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    @FXML
    private AnchorPane taskViewScene;

    @FXML
    private Button runAllTasksButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private AnchorPane progressPane;

    @FXML
    private Text progressPaneLabel;


    private DBFunctions dbFunc = new DBFunctions();
    private HomeController homeController = new HomeController();

    startViewController svc = new startViewController();
    Jobfunctions jobfunctions = new Jobfunctions();
    runtimeHandler handler = new runtimeHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //run in new thread so it doesnt crash
    @FXML
    void runAllTasks(ActionEvent event) throws IOException, XMLStreamException, SQLException {
        Thread thread;
        progressPane.setVisible(true);

        Task task = new Task<Void>() {
            @Override
            public Void call() throws IOException, XMLStreamException, SQLException {
                progressPaneLabel.setText("Fetching jobs from API...");
                jobfunctions.fetchAllJobs(handler.getApi(), handler.getRssUrl());
                progressPaneLabel.setText("Adding all jobs to database...");
                jobfunctions.loadAllJobsIntoDB();
                progressPaneLabel.setText("Adding Geo Locations to Database");
                jobfunctions.addJobsToArray();
                jobfunctions.loadGeoCodesIntoDB();
                progressPaneLabel.setText("Please Press Home to continnue ");

                return null;
            }

        };

        task.setOnSucceeded(event1 -> {
            System.out.println("Done");
            progressIndicator.progressProperty().unbind();
            progressIndicator.setProgress(100);

        });
        progressIndicator.progressProperty().bind(task.progressProperty());
        thread = new Thread(task);
        thread.start();
    }
}
