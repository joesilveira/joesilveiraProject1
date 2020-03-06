package controllers;

import dataTypes.AllJobsModel;
import dataTypes.GithubModel;
import dataTypes.StackOverFlowModel;
import dbHandler.DBFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class JobDisplayController implements Initializable {

    @FXML
    private AnchorPane readView;

    @FXML
    private TextArea grid_JobType;

    @FXML
    private TextArea grid_JobLocation;

    @FXML
    private TextArea grid_Description;

    @FXML
    private TextArea grid_jobTitle;

    @FXML
    private TextArea gird_howToApply;

    @FXML
    private Button previous_Button;

    @FXML
    private TextArea grid_JobCompany;

    @FXML
    private TextArea grid_CompanyURL;

    @FXML
    private Hyperlink urlLink;

    @FXML
    private Button next_Button;

    private int i = 0;
    ArrayList<AllJobsModel> allJobs = new ArrayList<AllJobsModel>();
    ArrayList<GithubModel> gJobs = new ArrayList<>();
    ArrayList<StackOverFlowModel> stackJobs = new ArrayList<>();

    DBFunctions dbFunc = new DBFunctions();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            previous_Button.setDisable(true);
            loadJobs();
            initFields();
        } catch (SQLException | MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(allJobs.size());

    }

    @FXML
    void nextButtonPressed(ActionEvent event) throws MalformedURLException {


        if (i == allJobs.size()) {
            next_Button.setDisable(true);
        } else {

            i++;

            previous_Button.setDisable(false);
            grid_jobTitle.setText(allJobs.get(i).getJobTitle());
            urlLink.setText(allJobs.get(i).getJobURL());
            grid_Description.setText(allJobs.get(i).getJobDescription());
            grid_JobType.setText(allJobs.get(i).getJobType());
            grid_JobCompany.setText(allJobs.get(i).getJobCompany());
            grid_JobLocation.setText(allJobs.get(i).getJobLocation());
            gird_howToApply.setText(allJobs.get(i).getHowToApply());
        }
    }

    @FXML
    void previousJobPressed(ActionEvent event) throws MalformedURLException {
        if (i == 0) {
            previous_Button.setDisable(true);
        } else {
            i--;
            grid_jobTitle.setText(allJobs.get(i).getJobTitle());
            urlLink.setText(allJobs.get(i).getJobURL());

            grid_Description.setText(allJobs.get(i).getJobDescription());
            grid_JobType.setText(allJobs.get(i).getJobType());
            grid_JobCompany.setText(allJobs.get(i).getJobCompany());
            grid_JobLocation.setText(allJobs.get(i).getJobLocation());
            gird_howToApply.setText(allJobs.get(i).getHowToApply());
        }
    }

    @FXML
    void openURL(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URL(urlLink.getText()).toURI());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void loadGitHubJobs() throws SQLException {

        gJobs = dbFunc.getGithubDBjobs();

        for (GithubModel gJob : gJobs) {
            AllJobsModel job = new AllJobsModel(gJob.getTitle(), gJob.getType(), gJob.getCompany(),
                    gJob.getLocation(), gJob.getCompany_url(), gJob.getDescription(),
                    gJob.getHow_to_apply());
            allJobs.add(job);
        }
    }

    private void loadStackJobs() {
        stackJobs = dbFunc.getStackJobs();

//        for (int i = 0; i < stackJobs.size(); i++) {
//            System.out.println(stackJobs.get(i).getTitle());
//
//        }

        for (StackOverFlowModel stackJob : stackJobs) {
            AllJobsModel job = new AllJobsModel(stackJob.getTitle(), stackJob.getCategory(), stackJob.getName(), stackJob.getLocation(),
                    stackJob.getLink(), stackJob.getDescription(), "Apply Online");
            allJobs.add(job);

        }
    }

    public void loadJobs() throws SQLException {
        loadStackJobs();
        loadGitHubJobs();
        Collections.sort(allJobs);

//        for(int i = 0; i < allJobs.size(); i++){
//            System.out.println(allJobs.get(i).getJobTitle());
//        }
    }

    private void initFields() throws MalformedURLException {
        grid_jobTitle.setText(allJobs.get(i).getJobTitle());
        urlLink.setText(allJobs.get(i).getJobURL());
        grid_Description.setText(allJobs.get(i).getJobDescription());
        grid_JobType.setText(allJobs.get(i).getJobType());
        grid_JobCompany.setText(allJobs.get(i).getJobCompany());
        grid_JobLocation.setText(allJobs.get(i).getJobLocation());
        gird_howToApply.setText(allJobs.get(i).getHowToApply());
    }


}
