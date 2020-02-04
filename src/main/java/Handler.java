//joe silveira

import Connections.HTTPRequest;
import FileIO.FileResource;

import javax.swing.*;
import java.io.IOException;

public class Handler {

    //Class Variables
    int pageNum = 1;
    int callRequest = 1;
    int numJobs = 0;
    String api = "https://jobs.github.com/positions.json?page=";
    String fileName = "jobsAPI.txt";
    HTTPRequest http = new HTTPRequest();
    ;
    FileResource fileIO = new FileResource();

    //Method to ping api with progress bar
    public void pingAPI(JProgressBar progressBar, JFrame frame) {
        while (callRequest == 1) {

            String url = api + pageNum;

            //Make Request
            http.makeGetRequest(url);

            //Progress bar updates
            numJobs += http.getNumJobsOnPage();
            progressBar.setMinimum(0);
            progressBar.setMaximum(numJobs);
            for (int i = 0; i < numJobs; i++) {
                progressBar.setValue(i);
            }
            pageNum++;

            //Close frame after completion
            if (http.getNumJobsOnPage() < 50) {
                callRequest = 0;
                frame.dispose();
            }

        }
    }

    //Method to ping api and return num of jobs
    //For test
    public int pingAPI() {

        int totalNumJobs = 0;
        while (callRequest == 1) {

            String url = api + pageNum;

            //Make Request
            http.makeGetRequest(url);

            pageNum++;

            if (http.getNumJobsOnPage() < 50) {
                callRequest = 0;
            }
        }

        totalNumJobs = http.getJobsTitleList().size();

        return totalNumJobs;
    }

    //Simple method to show save dialog
    public void showSaveDialog() throws IOException {
        //Show save dialog
        fileIO.createFile(fileName);
        fileIO.writeToFile(http.getJobsTitleList());

    }
}
