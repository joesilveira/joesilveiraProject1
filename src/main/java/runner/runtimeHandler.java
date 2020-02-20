package runner;//joe silveira

import ResponseTypes.RSSFeed;
import connections.GithubJSONRequest;
import connections.StackOverflowRSSRequest;
import dbHandler.DatabaseHandler;
import fileIO.FileResource;
import screens.mainScreen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class runtimeHandler {

    //Class Variables
    int pageNum = 1;
    int callRequest = 1;
    int numJobs = 0;
    int openFile = 0;
    File userFile;
    File programFile;
    String api = "https://jobs.github.com/positions.json?page=";
    String rssUrl = "https://stackoverflow.com/jobs/feed";
    String fileName = "jobsAPI.txt";

    //Java class variables
    JProgressBar progressBar = new JProgressBar();
    JFrame frame = new JFrame();

    //Program class varialbes
    GithubJSONRequest http = new GithubJSONRequest();
    StackOverflowRSSRequest rssRequest = new StackOverflowRSSRequest();
    mainScreen ms = new mainScreen();
    FileResource fileIO = new FileResource();
    mainScreen screens = new mainScreen();
    DatabaseHandler dbHandler = new DatabaseHandler();


    //Joe silveira
    //Method to run the program with all necessary method calls
    public void startProgram() throws IOException, SQLException {
        //rssRequest.makeRequest(rssUrl);

//        //Print message
//        JOptionPane.showMessageDialog(null, "Welcome to the git hub jobs fetcher");
//
//        //Load Progress Bar
//        screens.initProgressBar(progressBar, frame);
//
//        //initaliaze github jobs table
//        dbHandler.initGitHubJobsTable();

        //initialize stack overflow jobs table
        dbHandler.initStackOverFLowJobsTable();

        //rssRequest.addJobsToDB();

        rssRequest.printJobs();

//        //make api request
//        pingAPI(progressBar, frame);
//
//        //initalize file in user path
//        fileIO.createFile(fileName);
//
//        //get the user file and set the local file
//        userFile = fileIO.getUserFilePath();
//
//        //Write to user file
//        fileIO.writeJobsToFile(http.getJobsList(), userFile);
//
//        //ask to open file
//        openFile = ms.askToOpenFile();
//        if (openFile == 0) {
//            //Open the file if supported
//            if (Desktop.isDesktopSupported()) {
//                Desktop.getDesktop().open(fileIO.getUserFilePath());
//            }
//        }
//
//        //Message telling user jobs being written
//        ms.makeMessage("Press okay to write the job to the database");
//
//        //write to db
//        http.addJobsToGitHubDB();
//
//        //show jobs being written to db
//        ms.showJobsWrittenToDB();
//
//        /*
//        Methods calls for testing purposes
//         */
//
//        //*********File Stuff*******
//        //Create local file for test and write the exact same contents of the user file to that file
//
//        //Create file in program
//        fileIO.createProgramFile(fileName);
//
//        //Set the program file path
//        programFile = fileIO.getProgramFile();
//
//        //Write to the program file
//        fileIO.writeJobsToFile(http.getJobsList(), programFile);
    }

    //Joe Silveira
    //Method to ping api with progress bar
    public void pingAPI(JProgressBar progressBar, JFrame frame) {

        //Loop to loop through each page until there are less than 50 jobs on page
        while (callRequest == 1) {

            //Initialize url
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

}
