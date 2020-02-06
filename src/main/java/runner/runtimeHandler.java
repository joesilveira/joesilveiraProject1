package runner;//joe silveira

import connections.HTTPRequest;
import fileIO.FileResource;
import screens.mainScreen;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class runtimeHandler {

    //Class Variables
    int pageNum = 1;
    int callRequest = 1;
    int numJobs = 0;
    File userFile;
    File programFile;
    String api = "https://jobs.github.com/positions.json?page=";
    public String fileName = "jobsAPI.txt";

    //Java class variables
    JProgressBar progressBar = new JProgressBar();
    JFrame frame = new JFrame();

    //Program class varialbes
    HTTPRequest http = new HTTPRequest();
    FileResource fileIO = new FileResource();
    mainScreen screens = new mainScreen();

    //Joe silveira
    //Method to run the program with all necessary method calls
    public void startProgram() throws IOException {
        //Print message
        JOptionPane.showMessageDialog(null, "Welcome to the git hub jobs fetcher");

        //Load Progress Bar
        screens.initProgressBar(progressBar, frame);

        //make api request
        pingAPI(progressBar, frame);

        //create file in user path
        //fileIO.createFile(fileName);

        //get the user file adn set the local file
        //userFile = fileIO.getUserFilePath();

        //Write to user file
        //fileIO.writeToFile(http.getJobsTitleList(),userFile);


        /*
        Methods calls for testing purposes
         */


        //*********File Stuff*******
        //Create local file for test and write the exact same contents of the user file to that file

        //Create file in program
        fileIO.createProgramFile(fileName);

        //Set the program file path
        programFile = fileIO.getProgramFile();

        //Write to the program file
        fileIO.writeToFile(http.getJobsTitleList(), programFile);
    }

    //Joe Silveira
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

    //Joe Silveira
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

}
